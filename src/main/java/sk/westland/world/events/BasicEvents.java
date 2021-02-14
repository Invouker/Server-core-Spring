package sk.westland.world.events;

import com.gmail.chickenpowerrr.ranksync.api.RankSyncApi;
import com.gmail.chickenpowerrr.ranksync.api.bot.Bot;
import com.gmail.chickenpowerrr.ranksync.api.data.Properties;
import com.gmail.chickenpowerrr.ranksync.api.event.BotEnabledEvent;
import com.gmail.chickenpowerrr.ranksync.api.event.Event;
import com.gmail.chickenpowerrr.ranksync.discord.bot.BotFactory;
import com.gmail.chickenpowerrr.ranksync.discord.bot.DiscordBot;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.JDA;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.JDABuilder;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.entities.Guild;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.entities.GuildChannel;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.internal.JDAImpl;
import com.gmail.chickenpowerrr.ranksync.manager.RankSyncManager;
import com.gmail.chickenpowerrr.ranksync.spigot.RankSyncPlugin;
import net.minecraft.server.v1_16_R3.MinecraftServer;
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
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.event.player.WLPlayerMoveEvent;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.services.DiscordService;
import sk.westland.core.services.PlayerService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Map;

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

        if(entity.getName().startsWith(ChatColor.GOLD + "")) {
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

}
