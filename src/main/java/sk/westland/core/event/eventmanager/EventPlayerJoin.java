package sk.westland.core.event.eventmanager;

import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.eventmanager.EventManager;

public class EventPlayerJoin extends EventOrganizer {

    private WLPlayer wlPlayer;

    public EventPlayerJoin(EventManager eventManager, WLPlayer wlPlayer) {
        super(eventManager);
        this.wlPlayer = wlPlayer;
    }

    public WLPlayer getWlPlayer() {
        return wlPlayer;
    }

    public void setWlPlayer(WLPlayer wlPlayer) {
        this.wlPlayer = wlPlayer;
    }

}
