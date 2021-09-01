package sk.westland.world.minigame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.App;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.event.ServerDisableEvent;
import sk.westland.core.event.player.WLVotePartyDeath;
import sk.westland.core.services.MoneyService;
import sk.westland.core.services.UtilsService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PartyGame implements Listener {

    private Illusioner illusioner;
    private final String ILLUSIONER_NAME = "§6§lVote Party - Illusioner";
    private final List<Player> attacked = new ArrayList<>();

    @Autowired
    private MoneyService moneyService;

    @Deprecated
    public PartyGame() { }

    public PartyGame(Location location) {
        /*ChatInfo.sendTitle(ChatColor.of("#ffffb3") + "VoteParty",
                ChatColor.of("#ccff66") +"Bola spawnutá na spawne");
*/
        int playerCount = Bukkit.getOnlinePlayers().size();
        int health = Math.min(playerCount * 240, 1000);

        location.setYaw(90);
        illusioner =
                location.getWorld().spawn(location, Illusioner.class, (entity) -> {
            entity.setCustomName(ILLUSIONER_NAME);
            entity.setCustomNameVisible(true);
            entity.setAI(false);
            if(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null)
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);

            entity.setSpell(Spellcaster.Spell.FANGS);
            entity.setHealth(health);
        });


    }

    @EventHandler
    private void onServerDisable(ServerDisableEvent event) {
        if(illusioner != null)
            illusioner.remove();

        attacked.clear();
    }
//TODO: PlayerAttackEntityEvent repair
/*
    @EventHandler
    public void onPlayerAttackEntity(PlayerAttackEntityEvent event) {
        Entity entity = event.getAttackedEntity();
        if(entity.getType() != EntityType.ILLUSIONER)
            return;

        Player player = (Player) event.getAttacker();
        if(entity.getCustomName() != null && entity.getCustomName().equals(ILLUSIONER_NAME)) {
            if(attacked.contains(player))
                return;

            attacked.add(player);
        }
    }*/

    public void payRewardByAttacked() {
        attacked.forEach((player -> {
            moneyService.give(player, MoneyType.Money, App.getService(UtilsService.class).getRandomMinMaxInt(1900, 5000));
            moneyService.give(player, MoneyType.Gems, 325);
            player.giveExpLevels(ThreadLocalRandom.current().nextInt(5, 15));
            player.getInventory().addItem(new ItemStack(Material.DIAMOND, ThreadLocalRandom.current().nextInt(1, 15)));
            player.getInventory().addItem(new ItemStack(Material.EMERALD, ThreadLocalRandom.current().nextInt(1, 20)));
        }));
    }

    public void despawn() {
        if(illusioner != null && illusioner.getHealth() > 0)
            illusioner.remove();

        attacked.clear();
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntityType() != EntityType.ILLUSIONER)
            return;

        Entity entity = event.getEntity();
        if(entity.getLastDamageCause() == null)
            return;

        if(entity.getName().equals(ILLUSIONER_NAME)) {

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
