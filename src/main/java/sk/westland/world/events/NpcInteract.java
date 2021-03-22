package sk.westland.world.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.services.MoneyService;
import sk.westland.world.inventories.entities.HorseBuyInventory;

@Component
public class NpcInteract implements Listener {

    @Autowired
    private MoneyService moneyService;

    @EventHandler(ignoreCancelled = true)
    public void onWLPlayerInteractWithNPC(WLPlayerInteractWithNPCEvent event) {
        if(event.getNPCName().contains("Predajca koní")) {
            HorseBuyInventory horseBuyInventory = new HorseBuyInventory(moneyService);
            horseBuyInventory.open(event.getPlayer());
        }
    }
}
