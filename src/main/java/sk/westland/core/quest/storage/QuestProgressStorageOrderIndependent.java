package sk.westland.core.quest.storage;

import org.bukkit.entity.Player;
import sk.westland.core.quest.Quest;
import sk.westland.core.quest.QuestTask;
import sk.westland.core.quest.action.ActionDataStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class QuestProgressStorageOrderIndependent extends QuestProgressStorage {

    private Map<Integer, ActionDataStorage> actionDataStorageMap;

    private List<Integer> completedTasks = new ArrayList<>();

    public QuestProgressStorageOrderIndependent(Player player, Quest quest) {
        super(player, quest);
        this.actionDataStorageMap = new HashMap<>(quest.getTaskCount());

        for(QuestTask questTask : quest.getTaskList()) {
            this.actionDataStorageMap.put(questTask.getId(), new ActionDataStorage());
        }
    }

    public boolean canBeCompleted() {
        return this.completedTasks.size() == getQuest().getTaskCount();
    }

    public void setTaskCompleted(QuestTask task) {
        if(!this.completedTasks.contains(task.getId())) {
            this.completedTasks.add(task.getId());
        }
    }

    @Override
    public List<QuestTask> getActiveTasks() {
        return this.quest.getTaskList();
    }

    @Override
    public ActionDataStorage getTaskStorage(QuestTask task) {
        return this.actionDataStorageMap.get(task.getId());
    }

    @Override
    public boolean isNeedEvent(Class<?> eventClass) {
        return getQuest().getTaskList().stream().anyMatch(task -> Stream.of(task.getTaskRequireEvent().value()).anyMatch(aClass -> aClass == eventClass));
    }

    @Override
    public boolean isTaskIdActive(int taskId) {
        return !isTaskIdCompleted(taskId);
    }

    @Override
    public boolean isTaskIdCompleted(int taskId) {
        return this.completedTasks.contains(taskId);
    }
}
