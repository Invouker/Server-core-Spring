package sk.westland.core.event.player;

import org.bukkit.Location;
import sk.westland.core.entity.player.WLPlayer;

public class WLPlayerMoveEvent extends WLPlayerEvent {

    private Location location;

    public WLPlayerMoveEvent(WLPlayer wlPlayer, Location location) {
        super(wlPlayer);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
