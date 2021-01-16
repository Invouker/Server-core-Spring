package sk.westland.core.quest;


import sk.westland.core.quest.action.ITaskAction;
import sk.westland.core.quest.action.TaskRequireEvent;

import java.util.List;

public class QuestTask {

    private Quest quest;
    private int id;
    private String title;
    private String description;
    private ITaskAction taskAction;
    private TaskRequireEvent taskRequireEvent;
    private List<IReward> taskRewards;

    public QuestTask(int id, String title, String description, ITaskAction taskAction, TaskRequireEvent taskRequireEvent, List<IReward> taskRewards) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskAction = taskAction;
        this.taskRequireEvent = taskRequireEvent;
        this.taskRewards = taskRewards;
    }

    public String createActionArugument(String arg) {
        return "/quest_task_action " + this.quest.getId() + " " + this.id + " " + arg;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ITaskAction getTaskAction() {
        return taskAction;
    }

    public TaskRequireEvent getTaskRequireEvent() {
        return taskRequireEvent;
    }

    public List<IReward> getTaskRewards() {
        return taskRewards;
    }

    public boolean hasRewards() {
        return taskRewards != null && taskRewards.size() > 0;
    }

    @Override
    public String toString() {
        return "QuestTask{" +
                "quest=" + quest +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskRequireEvent=" + taskRequireEvent.toString() +
                '}';
    }
}
