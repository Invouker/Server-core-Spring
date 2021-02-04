package sk.westland.core.event.player;

import sk.westland.core.entity.player.WLPlayer;

public class WLPlayerQuitEvent extends WLPlayerEvent {

    public WLPlayerQuitEvent(WLPlayer wlPlayer) {
        super(wlPlayer);
    }

}
