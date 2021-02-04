package sk.westland.core.event.player;

import sk.westland.core.entity.player.WLPlayer;

public class WLPlayerJoinEvent extends WLPlayerEvent {

    public WLPlayerJoinEvent(WLPlayer wlPlayer) {
        super(wlPlayer);
    }

}
