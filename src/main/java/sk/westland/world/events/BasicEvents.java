package sk.westland.world.events;

import com.Zrips.CMI.Modules.tp.Teleportations;
import com.Zrips.CMI.events.CMIAsyncPlayerTeleportEvent;
import de.themoep.resourcepacksplugin.bukkit.events.ResourcePackStatusEvent;
import de.themoep.resourcepacksplugin.core.ResourcePackStatus;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.EPlayerOptions;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.services.APIServices;
import sk.westland.core.services.DiscordService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.RunnableHelper;
import sk.westland.world.commands.player.NightVisionCommand;
import sk.westland.world.items.Materials;

import java.util.EnumSet;

@Component
public class BasicEvents implements Listener {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private DiscordService discordService;

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onEntityExplode(EntityExplodeEvent event) {
        if(event.getEntityType() == EntityType.CREEPER || event.getEntityType() == EntityType.PRIMED_TNT)
            event.getEntity().remove();
            event.setCancelled(true);
    }

    @EventHandler
    private void noUproot(PlayerInteractEvent event) {
        if(event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.FARMLAND)
            event.setCancelled(true);
    }

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

        if(entity.getName().startsWith(ChatColor.GOLD + "") || entity.getName().startsWith(ChatColor.YELLOW + "")) {
            Bukkit.getPluginManager().callEvent(new WLPlayerInteractWithNPCEvent(wlPlayer, (Player)entity));
        }
    }


    @EventHandler
    private void onNPC(WLPlayerInteractWithNPCEvent event) {
        event.getPlayer().sendMessage("Interacting with: " + event.getNPC().getName());
    }

    @EventHandler
    private void onPluginInit(PluginEnableEvent event) throws NoSuchFieldException, IllegalAccessException {



        Bukkit.getScheduler().runTaskTimer(event.getWestLand(), () -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                //Bukkit.getPluginManager().callEvent(new WLPlayerMoveEvent(playerService.getWLPlayer(onlinePlayer), onlinePlayer.getLocation()));
            }
        }, 0l, 2l);
    }

    @EventHandler(ignoreCancelled = true)
    private void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();
        for (Materials.Items items : Materials.Items.values()) {
            if(anvilInventory.getItem(0) == null)
                return;

            if(anvilInventory.getItem(0).isSimilar(items.getItem())) {
                event.getInventory().setRepairCost(999);
                event.setResult(null);
            }
        }
    }


    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(!NightVisionCommand.getPlayers().contains(event.getPlayer()))
            return;

        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        NightVisionCommand.getPlayers().remove(player);
    }


    //@Autowired
    private APIServices apiServices;


    //@EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        apiServices.getTitleManagerAPI().removeScoreboard(event.getPlayer());
        //Bukkit.dispatchCommand(event.getPlayer(), "tm scoreboard toggle");

        //Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), () -> { }, 20*5L);
    }

    //@EventHandler
    private void onResourcePackEventLo(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        if(event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)
            displayScoreboard(player, EPlayerOptions.SHOW_SCOREBOARD.getPlayerOptions(wlPlayer));
    }

    private void displayScoreboard(Player player, boolean show) {
        Bukkit.dispatchCommand(player, "tm scoreboard toggle");

        if(show != apiServices.getTitleManagerAPI().hasScoreboard(player)) {
            Bukkit.dispatchCommand(player, "tm scoreboard toggle");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCMIAsyncPlayerTeleport(CMIAsyncPlayerTeleportEvent event) {
        EnumSet<Teleportations.TeleportType> teleportTypes =
                EnumSet.of(Teleportations.TeleportType.Tp, Teleportations.TeleportType.randomTp,
                        Teleportations.TeleportType.TpHere, Teleportations.TeleportType.TpPos,
                        Teleportations.TeleportType.Back, Teleportations.TeleportType.Spawn);

        if(teleportTypes.contains(event.getType())) {

        }
    }
}
