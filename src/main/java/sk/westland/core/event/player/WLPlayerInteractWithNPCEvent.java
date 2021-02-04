package sk.westland.core.event.player;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.event.NPCEvent;
import sk.westland.core.entity.player.WLPlayer;

public class WLPlayerInteractWithNPCEvent extends NPCEvent {

    public WLPlayerInteractWithNPCEvent(@NotNull WLPlayer player, Player npc) {
        super(player, npc);
    }



}
