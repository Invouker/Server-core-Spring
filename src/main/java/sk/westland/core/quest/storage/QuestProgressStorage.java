package sk.westland.core.quest.storage;

import org.bukkit.entity.Player;
import sk.westland.core.quest.Quest;
import sk.westland.core.quest.QuestState;
import sk.westland.core.quest.QuestTask;
import sk.westland.core.quest.action.ActionDataStorage;

import java.util.List;
import java.util.Objects;

public abstract class QuestProgressStorage {

    protected Player player;
    protected Quest quest;
    protected QuestState questState;

    public QuestProgressStorage(Player player, Quest quest) {
        this.player = player;
        this.quest = quest;
        this.questState = QuestState.InProgress;
    }

    public String getActiveActionNpcName() {
        return getActiveTasks().get(0).getTaskAction().getNameOfNpcForInteraction();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Quest getQuest() {
        return quest;
    }

    public QuestState getQuestState() {
        return questState;
    }

    public void setQuestState(QuestState questState) {
        this.questState = questState;
    }

    public abstract List<QuestTask> getActiveTasks();

    public abstract ActionDataStorage getTaskStorage(QuestTask task);

    public abstract boolean isNeedEvent(Class<?> eventClass);

    public abstract boolean isTaskIdActive(int taskId);

    public abstract boolean isTaskIdCompleted(int taskId);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestProgressStorage that = (QuestProgressStorage) o;
        return player.equals(that.player) && quest.getId() == that.quest.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, quest);
    }

    @Override
    public String toString() {
        return "QuestProgressStorage{" +
                "player=" + player.getName() +
                ", quest=" + quest.toString() +
                ", questState=" + questState.toString() +
                '}';
    }
}
