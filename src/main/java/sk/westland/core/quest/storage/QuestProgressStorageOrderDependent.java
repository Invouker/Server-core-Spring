package sk.westland.core.quest.storage;


import org.bukkit.entity.Player;
import sk.westland.core.quest.Quest;
import sk.westland.core.quest.QuestState;
import sk.westland.core.quest.QuestTask;
import sk.westland.core.quest.action.ActionDataStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestProgressStorageOrderDependent extends QuestProgressStorage {

    private int activeTaskId;
    private QuestTask activeTask;

    private ActionDataStorage actionDataStorage;

    private List<QuestTask> questTaskList = new ArrayList<>(1);

    public QuestProgressStorageOrderDependent(Player playerInstance, Quest quest) {
        super(playerInstance, quest);
        this.activeTaskId = 0;
        this.activeTask = quest.getTask(0);
        this.resetActionDataStorage();
    }

    public QuestProgressStorageOrderDependent(Player player, Quest quest, QuestState questState, int activeTaskId, ActionDataStorage actionDataStorage) {
        this(player, quest);
        this.questState = questState;
        this.activeTaskId = activeTaskId;
        this.actionDataStorage = actionDataStorage;
        this.activeTask = quest.getTask(activeTaskId);
    }

    @Override
    public List<QuestTask> getActiveTasks() {
        questTaskList.clear();
        questTaskList.add(getActiveTask());
        return questTaskList;
    }

    @Override
    public boolean isTaskIdActive(int taskId) {
        return getActiveTaskId() == taskId;
    }

    @Override
    public boolean isTaskIdCompleted(int taskId) {
        return taskId < getActiveTaskId();
    }

    @Override
    public ActionDataStorage getTaskStorage(QuestTask task) {
        return this.actionDataStorage;
    }

    public QuestTask getLastActiveTaskWithInfo() {
        QuestTask lastTask = getActiveTask();
        int nowIndex = getActiveTaskId();
        nowIndex--;

        if(lastTask.getTitle() != null) {
            return lastTask;
        }

        while(nowIndex >= 0) {
            QuestTask prevTask = quest.getTask(nowIndex);

            if(prevTask.getTitle() != null) {
                return prevTask;
            }

            lastTask = prevTask;
            nowIndex--;
        }

        return lastTask;
    }

    public void resetActionDataStorage() {
        this.actionDataStorage = new ActionDataStorage();
    }

    @Override
    public boolean isNeedEvent(Class<?> eventClass) {
        return Arrays.stream(getActiveTask().getTaskRequireEvent().value()).anyMatch(aClass -> aClass == eventClass);
    }

    public void setNextTaskId() {
        this.setActiveTaskId(this.activeTaskId + 1);
    }

    public void setActiveTaskId(int taskId) {
        this.activeTaskId = taskId;
        this.activeTask = this.quest.getTask(taskId);
    }

    public boolean isQuestCompleted() {
        return this.activeTaskId == this.quest.getLastTaskId();
    }

    public int getActiveTaskId() {
        return activeTaskId;
    }

    public QuestTask getActiveTask() {
        return activeTask;
    }

    public ActionDataStorage getActionDataStorage() {
        return actionDataStorage;
    }

    @Override
    public String toString() {
        return "QuestProgressStorageOrderDependent{" +
                "player=" + player.getName() +
                ", quest=" + quest.toString() +
                ", questState=" + questState.toString() +
                ", activeTaskId=" + activeTaskId +
                ", activeTask=" + activeTask.toString() +
                ", actionDataStorage=" + actionDataStorage.toString() +
                ", questTaskList=" + questTaskList.toString() +
                '}';
    }
}
