package sk.westland.core.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.WestLand;


public class ServerDisableEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final WestLand westLand;

    public ServerDisableEvent(WestLand westLand) {
        this.westLand = westLand;
        System.out.println("Disabling plugin: " + westLand.getName());
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
