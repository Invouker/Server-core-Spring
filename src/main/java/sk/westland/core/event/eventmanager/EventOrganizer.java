package sk.westland.core.event.eventmanager;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.eventmanager.EventManager;

public class EventOrganizer extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private EventManager eventManager;

    public EventOrganizer(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
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
