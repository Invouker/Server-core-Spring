package sk.westland.core.event.eventmanager;

import sk.westland.core.eventmanager.EEventState;
import sk.westland.core.eventmanager.EventManager;

public class EventChangeState extends EventOrganizer {

    private static EEventState oldeEventStateStatic = EEventState.NONE;
    private final EEventState oldeEventState;
    private final EEventState neweEventState;

    public EventChangeState(EventManager eventManager, EEventState eEventState) {
        super(eventManager);
        this.oldeEventState = oldeEventStateStatic;
        oldeEventStateStatic = eEventState;
        this.neweEventState = eEventState;
    }


    public EEventState getOldEEventState() {
        return oldeEventState;
    }


    public EEventState getNewEEventState() {
        return neweEventState;
    }

}
