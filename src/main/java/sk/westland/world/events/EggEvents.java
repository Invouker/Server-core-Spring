package sk.westland.world.events;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class EggEvents implements Listener {

    @EventHandler
    private void onProjectileHitEvent(ProjectileHitEvent event) {
        if(!(event.getEntity().getShooter() instanceof  Player))
            return;

        Player player = (Player) event.getEntity().getShooter();

        if(!player.hasPermission("westland.catch_mob.egg"))
            return;

        if(event.getEntityType() != EntityType.EGG)
            return;

        EnumSet<EntityType> blockedEntityTypes = EnumSet.of(EntityType.GHAST,EntityType.GUARDIAN,EntityType.HOGLIN,EntityType.PIGLIN_BRUTE,EntityType.PILLAGER,
                EntityType.RAVAGER, EntityType.SHULKER, EntityType.STRAY, EntityType.VEX, EntityType.WITHER_SKELETON, EntityType.ZOGLIN,
                EntityType.ENDER_DRAGON, EntityType.WITHER, EntityType.WITHER_SKULL, EntityType.PIGLIN, EntityType.STRIDER, EntityType.ENDERMAN,
                EntityType.ZOMBIFIED_PIGLIN, EntityType.HORSE, EntityType.SKELETON_HORSE, EntityType.ZOMBIE_HORSE, EntityType.PLAYER);

        if(event.getHitEntity() == null)
            return;

        Entity entity = event.getHitEntity();
        if(blockedEntityTypes.contains(entity.getType()))
            return;

        if(entity instanceof Tameable) {
            Tameable tameable = (Tameable) entity;
            if(!tameable.getOwner().getName().equals(player.getName()))
                return;
        }

        Location loc = entity.getLocation();
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(loc);

        if(res != null) {
            ResidencePermissions perms = res.getPermissions();
            boolean hasPermission = perms.playerHas(player, "mob-catch", false);
            if(!hasPermission)
                return;
        }

        Material mat = Material.valueOf(entity.getType().toString().toUpperCase() + "_SPAWN_EGG");

        loc.getWorld().dropItem(loc, new ItemStack(mat));
        entity.remove();

    }


}
