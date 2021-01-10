package sk.westland.core.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.WestLand;

public class PluginEnableEvent extends Event {

    private WestLand westLand;

    public PluginEnableEvent(WestLand westLand) {
        this.westLand = westLand;
    }

    public WestLand getWestLand() {
        return westLand;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
