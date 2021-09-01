package sk.westland.world.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.springframework.stereotype.Component;

@Component
public class FastDiggingFly implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && p.getGameMode() == GameMode.SURVIVAL) {
            if (p.hasPermission("westland.fastdigging.fly") && p.isFlying())
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1000, 6, false, false));
            if (p.hasPermission("westland.fastdigging.fly") && !p.isFlying())
                p.removePotionEffect(PotionEffectType.FAST_DIGGING);
        }
    }
}
