package sk.westland.core.services;

import org.bukkit.event.Listener;
import sk.westland.core.eventmanager.EEventState;
import sk.westland.core.eventmanager.EventManager;

public class EventManagerService implements Listener, BeanWire {

    private EventManager eventManager;

    public void createEvent(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void startEvent() {
        eventManager.setCanStart(true);
        eventManager.changeState(EEventState.WARMUP);
    }

    public void endEvent() {
        if(!isEventCreated())
            return;

        eventManager.changeState(EEventState.END_TIME);
        eventManager = null;
    }

    public void forceServiceEndEvent() {
        eventManager = null;
    }


    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public boolean isEventCreated() {
        return eventManager != null;
    }

    public boolean isEventRunning() {
         return (eventManager != null && eventManager.isRunning());
    }
}
