package sk.westland.world.commands.player;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.utils.Utils;

import java.util.ArrayList;

@Component
@CommandLine.Command(name = "nv", aliases = {"nightvision", "nocnevidenie", "nocne"})
@HasPermission("westland.commands.night_vision")
public class NightVisionCommand implements Runnable, Listener {

    @Autowired
    private Context context;

    public static ArrayList<Player> players = new ArrayList<>();

    @Override
    public void run() {
        Player player = context.getPlayer();

        if(player == null)
            return;

        if(players.contains(player)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            Utils.playSound(player, Sound.BLOCK_BEACON_DEACTIVATE);
            player.sendMessage("§b§l[!] §fNočné videnie bolo deaktivované.");

            players.remove(player);
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,20*9999,  3,false, false, false));
            Utils.playSound(player, Sound.BLOCK_BEACON_ACTIVATE);
            player.sendMessage("§b§l[!] §fNočné videnie bolo aktivované.");

            players.add(player);
        }
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(!players.contains(player))
            return;

        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        players.remove(player);
    }
}
