package sk.westland.core.quest;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Quest {

    private String id;
    private String title;
    private String description;

    private List<QuestTask> taskList;
    private List<IReward> rewardList;

    private EnumSet<QuestFlag> questFlags;

    public Quest(String id, String title, String description, List<QuestTask> taskList, List<IReward> rewardList, EnumSet<QuestFlag> questFlags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskList = taskList;
        this.rewardList = rewardList;
        this.questFlags = questFlags;

        for(QuestTask questTask : taskList) {
            questTask.setQuest(this);
        }
    }

    public boolean hasAnyTaskTitle() {
        for(QuestTask task : taskList) {
            if(task.getTitle() != null) {
                return true;
            }
        }

        return false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getDescriptionFormatted() {
        return Stream.of(description.split("\n")).collect(Collectors.toList());
    }

    public List<QuestTask> getTaskList() {
        return Collections.unmodifiableList(taskList);
    }

    public int getLastTaskId() {
        return this.taskList.size() - 1;
    }

    public int getTaskCount() {
        return this.taskList.size();
    }

    public QuestTask getTask(int index) {
        return this.taskList.get(index);
    }

    public List<IReward> getRewardList() {
        return rewardList;
    }

    public EnumSet<QuestFlag> getQuestFlags() {
        return questFlags;
    }

    public boolean hasFlag(@NotNull QuestFlag flag) {
        return questFlags.contains(flag);
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskList=" + taskList.size() +
                ", questFlags=" + questFlags.toString() +
                '}';
    }
}
