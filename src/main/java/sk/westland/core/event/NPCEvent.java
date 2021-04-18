package sk.westland.core.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.entity.player.WLPlayer;

public class NPCEvent extends EntityEvent {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final WLPlayer wlPlayer;
    private final Entity npc;
    private final String npcName;

    public NPCEvent(@NotNull WLPlayer player, @NotNull Entity npc) {
        super(npc);
        this.npc = npc;
        this.wlPlayer = player;
        this.npcName = ChatColor.stripColor(npc.getName());
    }

    public Entity getNPC() {
        return npc;
    }

    public String getNPCName() {
        return npcName;
    }

    public WLPlayer getWLPlayer() {
        return wlPlayer;
    }

    public Player getPlayer() { return wlPlayer.getPlayer(); }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
