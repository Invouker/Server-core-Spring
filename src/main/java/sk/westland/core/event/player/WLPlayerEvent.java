package sk.westland.core.event.player;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.entity.player.WLPlayer;

public class WLPlayerEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private WLPlayer wlPlayer;

    public WLPlayerEvent(WLPlayer wlPlayer) {
        this.wlPlayer = wlPlayer;
    }

    public WLPlayer getWlPlayer() {
        return wlPlayer;
    }

    public Player getPlayer() {
        return wlPlayer.getPlayer();
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
