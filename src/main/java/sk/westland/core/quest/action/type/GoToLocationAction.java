package sk.westland.core.quest.action.type;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.event.player.WLPlayerMoveEvent;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.quest.Quest;
import sk.westland.core.quest.QuestTask;
import sk.westland.core.quest.action.ActionDataStorage;
import sk.westland.core.quest.action.ITaskAction;
import sk.westland.core.quest.action.TaskRequireEvent;

@TaskRequireEvent({WLPlayerMoveEvent.class})
public class GoToLocationAction extends ITaskAction {

    private Location location;
    private double range;

    public GoToLocationAction(Location location, double range) {
        this.location = location;
        this.range = range;
    }

    @Override
    public boolean evaluate(@NotNull Quest quest, @NotNull QuestTask task, @NotNull WLPlayer player, @NotNull ActionDataStorage dataStorage, @Nullable Event bukkitEvent) {
        if(bukkitEvent == null) {
            return false;
        }

        if(!(bukkitEvent instanceof WLPlayerMoveEvent)) {
            return false;
        }

        WLPlayerMoveEvent playerMoveEvent = (WLPlayerMoveEvent) bukkitEvent;
        Location moveTo = playerMoveEvent.getLocation();

        if(moveTo == null) {
            return false;
        }

        return moveTo.distance(this.location) <= this.range;
    }
}
