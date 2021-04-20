package sk.westland.core.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WLVotePartyDeath extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private List<Player> attackedPlayersList;

    public WLVotePartyDeath(List<Player> attackedPlayersList) {
        this.attackedPlayersList = attackedPlayersList;
    }

    public List<Player> getAttackedPlayersList() {
        return attackedPlayersList;
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
