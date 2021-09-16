package sk.westland.world.events;

import com.Zrips.CMI.Modules.tp.Teleportations;
import com.Zrips.CMI.events.CMIPlayerTeleportEvent;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.services.DiscordService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.Utils;

import java.util.EnumSet;

@Component
public class BasicEvents implements Listener {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private DiscordService discordService;

    //@EventHandler(priority = EventPriority.HIGHEST)
    private void onEntityExplode(EntityExplodeEvent event) {
        if(event.getEntityType() == EntityType.CREEPER || event.getEntityType() == EntityType.PRIMED_TNT)
            event.getEntity().remove();
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    private void onEntitySpawn(EntitySpawnEvent event) {
        if(event.getEntityType() == EntityType.PHANTOM) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void noUproot(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null)
            return;

        if(event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.FARMLAND)
            event.setCancelled(true);
    }

    @EventHandler
    private void onNPCInteraction(PlayerInteractEntityEvent event) {
        if(event.getHand() != EquipmentSlot.HAND)
            return; // Only main-hand interaction

        Entity entity = event.getRightClicked();
        //if(entity.getType() != EntityType.PLAYER)
            //return; // Only Player-Type NPC

        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        if (wlPlayer == null)
            return; // Only players with type
        //System.out.println("Interakcia s : " + entity.getName());

        if(entity.getName().startsWith(ChatColor.GOLD + "") || entity.getName().startsWith(ChatColor.YELLOW + "") || entity.getName().startsWith(ChatColor.AQUA + "")) {
            Bukkit.getPluginManager().callEvent(new WLPlayerInteractWithNPCEvent(wlPlayer, entity));
        }
    }


    @EventHandler
    private void onNPC(WLPlayerInteractWithNPCEvent event) {
        //event.getPlayer().sendMessage("Interacting with: " + event.getNPC().getName());
    }

    @EventHandler(ignoreCancelled = true)
    private void onCMIPlayerTeleport(CMIPlayerTeleportEvent event) {
        EnumSet<Teleportations.TeleportType> teleportTypes =
                EnumSet.of(Teleportations.TeleportType.Top, Teleportations.TeleportType.Tp, Teleportations.TeleportType.randomTp,
                        Teleportations.TeleportType.TpHere, Teleportations.TeleportType.TpPos,
                        Teleportations.TeleportType.Back, Teleportations.TeleportType.Spawn);

        if(teleportTypes.contains(event.getType())) {
            Player player = event.getPlayer();
            Utils.playSound(event.getTo(), Sound.ENTITY_ENDERMAN_TELEPORT);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 35, 20, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 2, false, false, false));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInitialSpawn(PlayerSpawnLocationEvent event) {
        Location playerLocation = event.getPlayer().getLocation();
        if(playerLocation.getBlock().getType() == Material.NETHER_PORTAL) {
            Bukkit.getScheduler().runTask(WestLand.getInstance(), () -> {
                playerLocation.getBlock().setType(Material.AIR);
                playerLocation.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
            });
        }
    }
}
