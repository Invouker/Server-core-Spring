package sk.westland.world.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.event.player.WLPlayerMoveEvent;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.services.PlayerService;

@Component
public class BasicEvents implements Listener {

    @Autowired
    private PlayerService playerService;

    @EventHandler
    private void onNPCInteraction(PlayerInteractEntityEvent event) {
        if(event.getHand() != EquipmentSlot.HAND)
            return; // Only main-hand interaction

        Entity entity = event.getRightClicked();
        if(entity.getType() != EntityType.PLAYER)
            return; // Only Player-Type NPC

        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        if (wlPlayer == null)
            return; // Only players with type

        if(entity.getName().startsWith(ChatColor.GOLD + "")) {
            Bukkit.getPluginManager().callEvent(new WLPlayerInteractWithNPCEvent(wlPlayer, (Player)entity));
        }
    }


    @EventHandler
    private void onNPC(WLPlayerInteractWithNPCEvent event) {
        event.getPlayer().sendMessage("Interacting with: " + event.getNPC().getName());
    }

    @EventHandler
    private void onPluginInit(PluginEnableEvent event) {
        Bukkit.getScheduler().runTaskTimer(event.getWestLand(), () -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                Bukkit.getPluginManager().callEvent(new WLPlayerMoveEvent(playerService.getWLPlayer(onlinePlayer), onlinePlayer.getLocation()));
            }
        }, 0l, 2l);
    }

}
