package sk.westland.core.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.WestLand;


public class PluginDisableEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private WestLand westLand;

    public PluginDisableEvent(WestLand westLand) {
        this.westLand = westLand;
    }

    public WestLand getWestLand() {
        return westLand;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
