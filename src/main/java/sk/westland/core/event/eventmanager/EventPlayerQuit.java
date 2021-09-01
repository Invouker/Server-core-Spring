package sk.westland.core.event.eventmanager;

import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.EPlayerCauseEventLeave;
import sk.westland.core.eventmanager.EventManager;

public class EventPlayerQuit extends EventOrganizer {

    private WLPlayer wlPlayer;
    private EPlayerCauseEventLeave ePlayerCauseEventLeave;

    public EventPlayerQuit(EventManager eventManager, WLPlayer wlPlayer, EPlayerCauseEventLeave ePlayerCauseEventLeave) {
        super(eventManager);
        this.wlPlayer = wlPlayer;
        this.ePlayerCauseEventLeave = ePlayerCauseEventLeave;
    }

    public WLPlayer getWlPlayer() {
        return wlPlayer;
    }

    public void setWlPlayer(WLPlayer wlPlayer) {
        this.wlPlayer = wlPlayer;
    }

    public EPlayerCauseEventLeave getePlayerCauseEventLeave() {
        return ePlayerCauseEventLeave;
    }

    public void setePlayerCauseEventLeave(EPlayerCauseEventLeave ePlayerCauseEventLeave) {
        this.ePlayerCauseEventLeave = ePlayerCauseEventLeave;
    }
}
