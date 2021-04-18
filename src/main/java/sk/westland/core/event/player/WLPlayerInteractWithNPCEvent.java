package sk.westland.core.event.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.event.NPCEvent;

public class WLPlayerInteractWithNPCEvent extends NPCEvent {

    public WLPlayerInteractWithNPCEvent(@NotNull WLPlayer player, Entity npc) {
        super(player, npc);
    }

}
