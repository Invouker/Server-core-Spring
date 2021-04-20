package sk.westland.world.minigame;

import net.md_5.bungee.api.ChatColor;
import net.yatopia.api.event.PlayerAttackEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.event.ServerDisableEvent;
import sk.westland.core.event.player.WLVotePartyDeath;
import sk.westland.core.services.MoneyService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Component
public class PartyGame implements Listener {

    private Zombie zombie;
    private final String ZOMBIE_NAME = "§6§lVote Party - Zombie";
    private final List<Player> attacked = new ArrayList<>();

    @Autowired
    private MoneyService moneyService;

    @Deprecated
    public PartyGame() { }

    public PartyGame(Location location) {
        ChatInfo.sendTitle(ChatColor.of("#ffffb3") + "VoteParty",
                ChatColor.of("#ccff66") +"Bola spawnutá na spawne");

        int playerCount = Bukkit.getOnlinePlayers().size();
        int health = Math.min(playerCount * 240, 1000);
        zombie = location.getWorld().spawn(location, Zombie.class, (entity) -> {
            entity.setCustomName(ZOMBIE_NAME);
            entity.setCustomNameVisible(true);
            entity.setCanBreakDoors(false);
            entity.setAI(false);
            entity.setShouldBurnInDay(false);
            if(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null)
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);

            entity.setHealth(health);
            entity.setAdult();
            entity.setRotation(Utils.BaseMath.getRandomInt(256), 0);
        });
    }

    @EventHandler
    private void onServerDisable(ServerDisableEvent event) {
        if(zombie != null)
            zombie.remove();

        attacked.clear();
    }

    @EventHandler
    public void onPlayerAttackEntity(PlayerAttackEntityEvent event) {
        Entity entity = event.getAttackedEntity();
        if(entity.getType() != EntityType.ZOMBIE)
            return;

        Player player = (Player) event.getAttacker();
        if(entity.getCustomName() != null && entity.getCustomName().equals(ZOMBIE_NAME)) {
            if(attacked.contains(player))
                return;

            attacked.add(player);
        }
    }

    public void payRewardByAttacked() {
        attacked.forEach((player -> {
            moneyService.give(player, MoneyType.Money, 350);
            moneyService.give(player, MoneyType.Gems, 25);
        }));
    }

    public void despawn() {
        if(zombie != null && zombie.getHealth() > 0)
        zombie.remove();

        attacked.clear();
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntityType() != EntityType.ZOMBIE)
            return;

        Entity entity = event.getEntity();
        if(entity.getLastDamageCause() == null)
            return;

        if(entity.getName().equals(ZOMBIE_NAME)) {

            event.getDrops().clear();
            Bukkit.getPluginManager().callEvent(new WLVotePartyDeath(attacked));
            payRewardByAttacked();
            despawn();
        }
    }

    public List<Player> getAttacked() {
        return attacked;
    }
}
