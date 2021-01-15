package sk.westland.core.event.player;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import sk.westland.core.player.WLPlayer;

public class WLPlayerDamageEvent extends WLPlayerEvent {

    private Entity entity;
    private EntityDamageByEntityEvent event;
    private EntityDamageEvent.DamageCause damageCause;
    private double damage;

    public WLPlayerDamageEvent(WLPlayer wlPlayer, Entity entity, EntityDamageByEntityEvent event) {
        super(wlPlayer);
        this.entity = entity;
        this.event = event;
        this.damageCause = event.getCause();
        this.damage = event.getDamage();
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public EntityDamageByEntityEvent getEvent() {
        return event;
    }

    public void setEvent(EntityDamageByEntityEvent event) {
        this.event = event;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public void setDamageCause(EntityDamageEvent.DamageCause damageCause) {
        this.damageCause = damageCause;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
