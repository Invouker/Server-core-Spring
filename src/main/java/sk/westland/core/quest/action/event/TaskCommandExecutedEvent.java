package sk.westland.core.quest.action.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TaskCommandExecutedEvent extends Event {

    private String name;
    private String questId;
    private int taskId;
    private String arg;

    public TaskCommandExecutedEvent(String name, String questId, int taskId, String arg) {
        this.name = name;
        this.questId = questId;
        this.taskId = taskId;
        this.arg = arg;
    }

    public String getName() {
        return name;
    }

    public String getQuestId() {
        return questId;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getArg() {
        return arg;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
