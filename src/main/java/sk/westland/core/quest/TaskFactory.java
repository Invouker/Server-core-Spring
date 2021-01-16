package sk.westland.core.quest;


import sk.westland.core.quest.action.ITaskAction;
import sk.westland.core.quest.action.TaskRequireEvent;

import java.util.ArrayList;
import java.util.List;

public class TaskFactory {

    public static TaskFactory create() {
        return new TaskFactory();
    }

    private List<QuestTask> questTaskList = new ArrayList<>();

    public TaskFactory addTask(String title, String description, ITaskAction action, List<IReward> taskRewards) {
        System.out.println("ADDING TASK: " + title + ", " + description + ", ACTION:" + action);

        TaskRequireEvent taskRequireEvent = getTaskRequireEvent(action.getClass());

        assert taskRequireEvent != null;

        this.questTaskList.add(new QuestTask(questTaskList.size(), title, description, action, taskRequireEvent, taskRewards));
        return this;
    }

    public TaskFactory addTask(String title, String description, ITaskAction action) {
        return addTask(title, description, action, null);
    }

    public TaskFactory addTask(String title, ITaskAction action, List<IReward> taskRewards) {
        return addTask(title, null, action, taskRewards);
    }

    public TaskFactory addTask(String title, ITaskAction action) {
        return addTask(title, action, null);
    }

    public TaskFactory addTask(ITaskAction action, List<IReward> taskRewards) {
        return addTask(null, action, taskRewards);
    }

    public TaskFactory addTask(ITaskAction action) {
        return addTask(action, null);
    }

    private TaskRequireEvent getTaskRequireEvent(Class<? extends ITaskAction> actionClass) {
        if(!actionClass.isAnnotationPresent(TaskRequireEvent.class)) {
            return null;
        }

        return actionClass.getAnnotation(TaskRequireEvent.class);
    }

    public List<QuestTask> build() {
        return this.questTaskList;
    }

}
