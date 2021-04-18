package sk.westland.world.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.services.HorseService;
import sk.westland.core.services.MoneyService;
import sk.westland.world.inventories.DailyRewardInventory;
import sk.westland.world.inventories.entities.HorseUpgradeInventory;
import sk.westland.world.inventories.shops.HorseBuyInventory;

@Component
public class NpcInteract implements Listener {

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private HorseService horseService;

    @EventHandler(ignoreCancelled = true)
    public void onWLPlayerInteractWithNPC(WLPlayerInteractWithNPCEvent event) {
        WLPlayer wlPlayer = event.getWLPlayer();
        if(event.getNPCName().contains("Predajca koní")) {
            HorseBuyInventory horseBuyInventory = new HorseBuyInventory(horseService, moneyService, event.getPlayer());
            horseBuyInventory.open(wlPlayer);
        }

        if(event.getNPCName().contains("Vylepšenie kon")) {
            HorseUpgradeInventory horseUpgradeInventory = new HorseUpgradeInventory(horseService);
            horseUpgradeInventory.open(wlPlayer);
        }

        if(event.getNPCName().contains("Daily reward")) {
            DailyRewardInventory dailyRewardInventory = new DailyRewardInventory(wlPlayer, moneyService);
            dailyRewardInventory.open(wlPlayer);
        }
    }
}
