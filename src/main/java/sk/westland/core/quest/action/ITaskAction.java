package sk.westland.core.quest.action;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.quest.Quest;
import sk.westland.core.quest.QuestTask;

public abstract class ITaskAction {

    private String nameOfNpcForInteraction;

    public abstract boolean evaluate(
            @NotNull Quest quest,
            @NotNull QuestTask task,
            @NotNull WLPlayer player,
            @NotNull ActionDataStorage dataStorage,
            @Nullable Event bukkitEvent);

    public void whenSwitched(@NotNull Quest quest, @NotNull QuestTask task, @NotNull WLPlayer player, @NotNull ActionDataStorage dataStorage) {

    }

    protected static boolean checkName(Entity entity, String name) {
        if(entity.getName().equals(name))
            return true;
        if(entity.getCustomName() != null && entity.getCustomName().equals(name))
            return true;
        return false;
    }

    public String getNameOfNpcForInteraction() {
        return nameOfNpcForInteraction;
    }

    public void setNameOfNpcForInteraction(String nameOfNpcForInteraction) {
        this.nameOfNpcForInteraction = nameOfNpcForInteraction;
    }

    @Override
    public String toString() {
        return "ITaskAction{" +
                "nameOfNpcForInteraction='" + nameOfNpcForInteraction + '\'' +
                '}';
    }
}
