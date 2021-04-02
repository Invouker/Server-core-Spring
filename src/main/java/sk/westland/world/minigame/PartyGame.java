package sk.westland.world.minigame;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.springframework.stereotype.Component;
import sk.westland.core.event.ServerDisableEvent;
import sk.westland.core.utils.Utils;
import sk.westland.world.items.Materials;

@Component
public class PartyGame implements Listener {

    private Location  location;
    private static final int VOTE_START = 200;
    private Zombie zombie;
    private static final String ZOMBIE_NAME = "§6§lVote Party - Zombie";

    public PartyGame() { }

    public PartyGame(Location location) {
        this.location = location;

        zombie = location.getWorld().spawn(location, Zombie.class, (entity) -> {
            entity.setCustomName(ZOMBIE_NAME);
            entity.setCustomNameVisible(true);
            entity.setCanBreakDoors(false);
            entity.setAI(false);
            entity.setShouldBurnInDay(false);
            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(600);
            entity.setHealth(600);
            entity.setAdult();
            entity.setRotation(Utils.BaseMath.getRandomInt(256), 0);
        });
        //
    }

    @EventHandler
    private void onServerDisable(ServerDisableEvent event) {
        if(zombie != null)
            zombie.remove();
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntityType() != EntityType.ZOMBIE)
            return;

        if(event.getEntity().getName().equals(ZOMBIE_NAME)) {

            event.getDrops().clear();
            event.getDrops().add(Materials.Items.BETTER_PICKAXE.getItem());

        }
    }
}
