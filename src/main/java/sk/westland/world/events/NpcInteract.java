package sk.westland.world.events;

import dev.alangomes.springspigot.context.Context;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.EServerData;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.services.*;
import sk.westland.core.utils.ChatInfo;
import sk.westland.world.inventories.DailyRewardInventory;
import sk.westland.world.inventories.entities.HorseUpgradeInventory;
import sk.westland.world.inventories.shops.HorseBuyInventory;

@Component
@CommandLine.Command(name = "interact", hidden = true)
public class NpcInteract implements Listener {

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private HorseService horseService;

    @Autowired
    private RunnableService runnableService;

    @Autowired
    private ServerDataService serverDataService;

    @Autowired
    private PlayerService playerService;

    @EventHandler
    public void onNPCClickEvent(NPCRightClickEvent event) {
        WLPlayer wlPlayer = playerService.getWLPlayer(event.getClicker());

        if(serverDataService.getBooleanData(EServerData.DEBUG))
            ChatInfo.DEBUG.send(wlPlayer, "Interakcia s " + event.getNPC().getName() + ", " + event.getNPC().getId());


        switch (event.getNPC().getId()) {
            case 95: {
                HorseBuyInventory horseBuyInventory = new HorseBuyInventory(horseService, moneyService, event.getClicker());
                horseBuyInventory.open(wlPlayer);
                break;
            }

            case 94: {
                HorseUpgradeInventory horseUpgradeInventory = new HorseUpgradeInventory(horseService, moneyService,runnableService, event.getClicker());
                horseUpgradeInventory.open(wlPlayer);
                break;
            }

            default:
                break;
        }

    }

    public void onWLPlayerInteractWithNPC(WLPlayerInteractWithNPCEvent event) {
        WLPlayer wlPlayer = event.getWLPlayer();

        if(serverDataService.getBooleanData(EServerData.DEBUG))
            ChatInfo.DEBUG.send(wlPlayer, "Interakcia s " + event.getNPCName() + ", " + event.getNPC().getCustomName());


        if(event.getNPCName().contains("Predajca koní")) {
            HorseBuyInventory horseBuyInventory = new HorseBuyInventory(horseService, moneyService, event.getPlayer());
            horseBuyInventory.open(wlPlayer);
        }

        if(event.getNPCName().contains("Vylepšenie koň")) {
            HorseUpgradeInventory horseUpgradeInventory = new HorseUpgradeInventory(horseService, moneyService,runnableService, event.getPlayer());
            horseUpgradeInventory.open(wlPlayer);
        }

        if(event.getNPCName().contains("Daily reward")) {
            DailyRewardInventory dailyRewardInventory = new DailyRewardInventory(wlPlayer, moneyService);
            dailyRewardInventory.open(wlPlayer);
        }
    }

    @Component
    @CommandLine.Command(name = "dreward", hidden = true)
    static class DailyReward implements Runnable {

        @Autowired
        private PlayerService playerService;

        @Autowired
        private Context context;

        @Autowired
        private MoneyService moneyService;

        @Override
        public void run() {
            WLPlayer wlPlayer = playerService.getWLPlayer(context.getPlayer());

            DailyRewardInventory dailyRewardInventory = new DailyRewardInventory(wlPlayer, moneyService);
            dailyRewardInventory.open(wlPlayer);
        }
    }
}
