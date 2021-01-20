package sk.westland.core.services;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.quest.*;
import sk.westland.core.quest.action.event.TaskEnabledEvent;
import sk.westland.core.quest.storage.QuestProgressStorage;
import sk.westland.core.quest.storage.QuestProgressStorageOrderDependent;
import sk.westland.core.quest.storage.QuestProgressStorageOrderIndependent;
import sk.westland.core.utils.BookFactory;
import sk.westland.core.utils.BookPageFactory;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;

public class QuestService implements Listener {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private InteractionService interactionService;

    private Map<String, Quest> questMap = new HashMap<>();
    private Map<Player, Map<String, QuestProgressStorage>> progressStorageMap = new HashMap<>();
    private Map<Player, List<QuestProgressStorage>> activeQuestProgressMap = new HashMap<>();

    public Set<String> getQuestIds() {
        return questMap.keySet();
    }

    @NotNull
    public ESetQuestStateResult setQuestState(@NotNull WLPlayer wlPlayer, @NotNull String quest_id, @NotNull QuestState questState) {
        Map<String, QuestProgressStorage> playerQuestMap = progressStorageMap.get(wlPlayer.getPlayer());

        if(playerQuestMap == null || !playerQuestMap.containsKey(quest_id)) {
            return ESetQuestStateResult.QuestNotFound;
        }

        QuestProgressStorage progressStorage = playerQuestMap.get(quest_id);

        if(progressStorage.getQuestState() == questState) {
            return ESetQuestStateResult.SameState;
        }

        if(questState == QuestState.InProgress || questState == QuestState.NotStarted) {
            return ESetQuestStateResult.CannotChange;
        }

        if(questState == QuestState.Completed) {
            completeQuest(wlPlayer, progressStorage);
        } else {
            progressStorage.setQuestState(questState);
        }

        return ESetQuestStateResult.Changed;
    }

    public boolean removePlayerQuest(@NotNull String quest_id, @NotNull WLPlayer wlPlayer) {
        Map<String, QuestProgressStorage> playerQuestMap = progressStorageMap.get(wlPlayer.getPlayer());

        if(playerQuestMap == null || !playerQuestMap.containsKey(quest_id)) {
            return false;
        }

        QuestProgressStorage progressStorage = playerQuestMap.get(quest_id);

        if(progressStorage.getQuestState() == QuestState.InProgress) {
            deactivateQuestProgress(progressStorage);
        }

        playerQuestMap.remove(quest_id);

        return true;
    }

    /**
     *
     * @param firstReward is forcing to give some reward, not allowing empty reward.
     */
    public Quest registerQuest(String quest_id, String title, String description, List<QuestTask> taskList, @NotNull EnumSet<QuestFlag> questFlags, @NotNull IReward firstReward, IReward... otherRewards) {
        List<IReward> rewardList = new ArrayList<>(Arrays.asList(otherRewards));
        rewardList.add(firstReward);

        Quest quest = new Quest(quest_id, title, description, taskList, rewardList, questFlags);
        
        if(questMap.containsKey(quest_id))
            throw new KeyAlreadyExistsException("Quest " + quest_id + " with these ID, already exist!");

        this.questMap.put(quest_id, quest);

        return quest;
    }

    public Quest registerQuest(String quest_id, String title, String description, List<QuestTask> taskList, @NotNull IReward firstReward, IReward... otherRewards) {
        return registerQuest(quest_id, title, description, taskList, EnumSet.noneOf(QuestFlag.class), firstReward, otherRewards);
    }

    public Quest getQuestById(String id) {
        return this.questMap.getOrDefault(id, null);
    }

    public void offerQuest(String quest_id, @NotNull WLPlayer player) {
        offerQuest(getQuestById(quest_id), player);
    }

    public void offerQuest(Quest quest, @NotNull WLPlayer player) {
        BookFactory bookFactory = new BookFactory();
        bookFactory.title(quest.getTitle());
        bookFactory.author("MineBreak");

        bookFactory.addPage(BookPageFactory.create()
                .appendText(ChatColor.GREEN + quest.getTitle())
                .newLine()
                .newLineText(quest.getDescription())
        );

        bookFactory.open(player);


    }

    public boolean acceptQuest(String quest_id, @NotNull WLPlayer player) {
        return acceptQuest(getQuestById(quest_id), player);
    }

    public boolean acceptQuest(Quest quest, @NotNull WLPlayer player) {
        if(quest == null)
            return false;

        Map<String, QuestProgressStorage> questPlayerProgressStorageMap = progressStorageMap.getOrDefault(player.getPlayer(), null);

        if(questPlayerProgressStorageMap == null) {
            questPlayerProgressStorageMap = new LinkedHashMap<>();
            progressStorageMap.put(player.getPlayer(), questPlayerProgressStorageMap);
        }

        if(questPlayerProgressStorageMap.containsKey(quest.getId())) {
            //TODO: Think of some other way to enable repeating quest, forced for now.
            QuestState questState = questPlayerProgressStorageMap.get(quest.getId()).getQuestState();

            if(questState == QuestState.InProgress)
                return false;

            if(questState == QuestState.Completed && !quest.hasFlag(QuestFlag.Repeatable))
                return false;
        }

        QuestProgressStorage questProgressStorage;

        if(quest.hasFlag(QuestFlag.OrderIndependent)) {
            questProgressStorage = new QuestProgressStorageOrderIndependent(player.getPlayer(), quest);
        } else {
            questProgressStorage = new QuestProgressStorageOrderDependent(player.getPlayer(), quest);
        }

        questPlayerProgressStorageMap.put(quest.getId(), questProgressStorage);

        setQuestProgressActive(questProgressStorage);
        System.out.println("4");
        questProgressStorage.getActiveTasks().forEach((task -> task.getTaskAction().whenSwitched(quest, task, player, questProgressStorage.getTaskStorage(task))));

        ChatInfo.SUCCESS.send(player, ChatColor.GREEN + "Přijal jsi quest: " + ChatColor.YELLOW + quest.getTitle());

        updatePlayerProgressEvent(player, new TaskEnabledEvent());

        return true;
    }

    public QuestState getQuestStatus(@NotNull WLPlayer wlPlayer, @NotNull String quest_id) {
        return getQuestStatus(wlPlayer, getQuestById(quest_id));
    }

    public QuestState getQuestStatus(@NotNull WLPlayer wlPlayer, @NotNull Quest quest) {
        QuestProgressStorage progressStorage = getQuestProgress(wlPlayer, quest.getId());

        if(progressStorage == null) {
            return QuestState.NotStarted;
        }

        return progressStorage.getQuestState();
    }

    public QuestProgressStorage getQuestProgress(@NotNull WLPlayer wlPlayer, String quest_id) {
        Map<String, QuestProgressStorage> map = this.progressStorageMap.getOrDefault(wlPlayer.getPlayer(), null);

        if(map == null)
            return null;

        return map.getOrDefault(quest_id, null);
    }

    public List<QuestProgressStorage> getActiveProgressStorages() {
        List<QuestProgressStorage> storageList = new LinkedList<>();

        for(Map.Entry<Player, List<QuestProgressStorage>> entry : activeQuestProgressMap.entrySet()) {
            storageList.addAll(entry.getValue());
        }

        return storageList;
    }

    public List<QuestProgressStorage> getAllQuestProgressList(WLPlayer wlPlayer) {
        List<QuestProgressStorage> storageList = new ArrayList<>();

        if(!progressStorageMap.containsKey(wlPlayer.getPlayer())) {
            return storageList;
        }

        for(Map.Entry<String, QuestProgressStorage> entry : progressStorageMap.get(wlPlayer.getPlayer()).entrySet()) {
            storageList.add(entry.getValue());
        }

        return storageList;
    }

    public List<QuestProgressStorage> getActiveProgressStorage(WLPlayer player) {
        List<QuestProgressStorage> list = activeQuestProgressMap.getOrDefault(player.getPlayer(), null);

        if(list == null) {
            list = new ArrayList<>();
            activeQuestProgressMap.put(player.getPlayer(), list);
        }

        return list;
    }

    public void setQuestProgressActive(QuestProgressStorage questProgress) {
        getActiveProgressStorage(playerService.getWLPlayer(questProgress.getPlayer())).add(questProgress);
    }


    public void deactivateQuestProgress(QuestProgressStorage questProgress) {
        getActiveProgressStorage(playerService.getWLPlayer(questProgress.getPlayer())).remove(questProgress);
    }

    private void completeQuest(@NotNull WLPlayer player, QuestProgressStorage questStorage) {
        player.playXPSound();
        questStorage.setQuestState(QuestState.Completed);
        deactivateQuestProgress(questStorage);

        Quest quest = questStorage.getQuest();

        player.sendMessage(ChatColor.GREEN + "Dokončil jsi quest: " + ChatColor.YELLOW + quest.getTitle());

        for(IReward reward : quest.getRewardList()) {
            reward.apply(this, player);
        }
    }

    public void updateProgressEventOrderIndependent(@NotNull WLPlayer player, @NotNull QuestProgressStorageOrderIndependent questStorage, @Nullable Event bukkitEvent) {
        List<QuestTask> questTasks = questStorage.getActiveTasks();
        if(questStorage.getQuestState() == QuestState.Completed)
            return;

        for (int i = questTasks.size() - 1; i >= 0; i--) {
            QuestTask questTask = questTasks.get(i);

            if(questStorage.isTaskIdCompleted(i))
                continue;


            boolean isCompleted = questTask.getTaskAction().evaluate(questStorage.getQuest(), questTask, player, questStorage.getTaskStorage(questTask), bukkitEvent);
            if(isCompleted) {
                questStorage.setTaskCompleted(questTask);
                if(questTask.getTitle() != null)
                    player.sendMessage(ChatColor.GREEN + "Dokončil jsi část úkolu: " + ChatColor.YELLOW + questTask.getTitle());

                player.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);

                if(questTask.hasRewards()) {
                    for (IReward reward : questTask.getTaskRewards()) {
                        reward.apply(this, player);
                    }
                }
            }
        }

        if(questStorage.canBeCompleted()) {
            completeQuest(player, questStorage);
        }
    }

    public boolean updateProgressEventOrderDependent(@NotNull WLPlayer player, @NotNull QuestProgressStorageOrderDependent questStorage, @Nullable Event bukkitEvent) {
        QuestTask questTask = questStorage.getActiveTask();

        if(questStorage.getQuestState() != QuestState.InProgress) {
            //TODO: this cannot happen !
            Log.warn(" != inProgress");
            return false;
        }

        boolean canSwitchToNextTask = questTask.getTaskAction().evaluate(questStorage.getQuest(), questTask, player, questStorage.getActionDataStorage(), bukkitEvent);

        if(!canSwitchToNextTask)
            return false;

        if(questStorage.isQuestCompleted()) {
            if(questTask.hasRewards()) {
                for (IReward reward : questTask.getTaskRewards()) {
                    reward.apply(this, player);
                }
            }
            completeQuest(player, questStorage);
            return true;
        } else {
            if(questTask.hasRewards()) {
                for (IReward reward : questTask.getTaskRewards()) {
                    reward.apply(this, player);
                }
            }
            questStorage.setNextTaskId();
            questStorage.resetActionDataStorage();
            questStorage.getActiveTask().getTaskAction().whenSwitched(questStorage.getQuest(), questStorage.getActiveTask(), player, questStorage.getActionDataStorage());
            if(questStorage.getActiveTask().getTitle() != null)
                player.sendMessage(ChatColor.GREEN + "Nový postup: " + ChatColor.YELLOW + questStorage.getActiveTask().getTitle());

            if(questStorage.isNeedEvent(TaskEnabledEvent.class))
                updateProgressEventOrderDependent(player, questStorage, new TaskEnabledEvent());

            player.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);

            return true;
        }
    }

    public void updatePlayerProgressEvent(@Nullable WLPlayer player, @Nullable Event bukkitEvent) {
        if(player == null)
            return;

        List<QuestProgressStorage> storageList = getActiveProgressStorage(player);
        // Inverted for because of ConcurrentModificationException
        for (int i = storageList.size() - 1; i >= 0; i--) {
            QuestProgressStorage progressStorage = storageList.get(i);

            boolean isOrderIndependent = progressStorage.getQuest().hasFlag(QuestFlag.OrderIndependent);

            if(!isOrderIndependent && bukkitEvent != null && progressStorage.isNeedEvent(bukkitEvent.getClass())) {
                boolean callImmediately =
                        interactionService.requestInteractionHandler(
                        progressStorage.getActiveActionNpcName(),
                        progressStorage.getQuest().getTitle(),
                        progressStorage.getActiveTasks().get(0).getTitle(),
                        Event.class, bukkitEvent, receivedEvent ->
                                    updateProgressEventOrderDependent(player, (QuestProgressStorageOrderDependent) progressStorage, receivedEvent)
                                );
                if(callImmediately) break;
            }
        }

        for (int i = storageList.size() - 1; i >= 0; i--) {
            QuestProgressStorage progressStorage = storageList.get(i);

            boolean isOrderIndependent = progressStorage.getQuest().hasFlag(QuestFlag.OrderIndependent);

            if(isOrderIndependent && bukkitEvent != null && progressStorage.isNeedEvent(bukkitEvent.getClass())) {
                interactionService.requestInteractionHandler(progressStorage.getActiveActionNpcName(),
                        progressStorage.getQuest().getTitle(),
                        progressStorage.getActiveTasks().get(0).getTitle(),
                        Event.class, bukkitEvent, receivedEvent -> updateProgressEventOrderIndependent(player, (QuestProgressStorageOrderIndependent) progressStorage, bukkitEvent));

            }

        }
    }
}
