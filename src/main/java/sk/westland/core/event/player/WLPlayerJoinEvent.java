package sk.westland.core.event.player;

import dev.alangomes.springspigot.util.Synchronize;
import sk.westland.core.entity.player.WLPlayer;

@Synchronize
public class  WLPlayerJoinEvent extends WLPlayerEvent {

    public WLPlayerJoinEvent(WLPlayer wlPlayer) {
        super(wlPlayer);
    }

}
