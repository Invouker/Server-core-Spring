package sk.westland.world.commands.player;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.App;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.EEventType;
import sk.westland.core.enums.EPlayerCauseEventLeave;
import sk.westland.core.eventmanager.EventManager;
import sk.westland.core.services.EventManagerService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.world.commands.suggestion.EventTypeSuggestion;
import sk.westland.world.playerevent.EventFallenBlocks;

import java.util.Arrays;

@Component
@CommandLine.Command(name = "event", aliases = {"events", "eventy"})
public class EventCommands {

    @Component
    @CommandLine.Command(name = "join")
    static class Join implements Runnable {

        @Autowired
        private EventManagerService eventManagerService;

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            if(!eventManagerService.isEventCreated()) {
                ChatInfo.WARNING.send(context, "Momentálne sa nedá pripojiť na žiaden event!");
                return;
            }

            if(eventManagerService.isEventRunning()) {
                ChatInfo.WARNING.send(context, "Nedá sa napojiť na prebiehajúcí event!");
                return;
            }

            EventManager eventManager = eventManagerService.getEventManager();
            WLPlayer wlPlayer = playerService.getWLPlayer(context.getPlayer());

            if(eventManager.getPlayers().contains(wlPlayer))
                return;

            if(wlPlayer == null) {
                throw new NullPointerException("WLPlayer is null");
            }

            eventManager.addPlayerToEvent(wlPlayer);
            ChatInfo.SUCCESS.send(wlPlayer, "Úspešne si sa pripojil na event!");
        }
    }

    @Component
    @CommandLine.Command(name = "create")
    @HasPermission("commands.event.create")
    static class Create implements Runnable {

        @CommandLine.Parameters(index = "0", completionCandidates = EventTypeSuggestion.class)
        private EEventType eEventType;

        @Autowired
        private EventManagerService eventManagerService;

        @Autowired
        private Context context;

        @Override
        public void run() {

            if(eEventType == null)
                return;

            EventManager eventManager = null;
            
            if(eEventType == EEventType.FALLEN_BLOCKS) {
                eventManager = new EventFallenBlocks("eventworld");
            }

            if(eventManagerService.isEventCreated())
                return;

            if(eventManagerService.isEventRunning())
                return;


            eventManagerService.createEvent(eventManager);
            ChatInfo.SUCCESS.send(context, "Úspešne si vytvoril event " + eEventType.getEventName() + "!");

        }
    }

    @Component
    @CommandLine.Command(name = "start")
    @HasPermission("commands.event.start")
    static class Start implements Runnable {

        @Autowired
        private EventManagerService eventManagerService;

        @Autowired
        private Context context;

        @Override
        public void run() {

            if (!eventManagerService.isEventCreated()) {
                ChatInfo.WARNING.send(context, "Žiadny event momentálne nieje vytvorený!");
                return;
            }

            if (eventManagerService.isEventRunning() || eventManagerService.getEventManager().isCanStart()) {
                ChatInfo.WARNING.send(context, "Event už bol odštartovaný!");
                return;
            }

            eventManagerService.startEvent();
            ChatInfo.SUCCESS.send(context, "Úspešne si odštartoval event!");
        }
    }

    @Component
    @CommandLine.Command(name = "leave")
    @HasPermission("commands.event.leave")
    static class Leave implements Runnable {

        @Autowired
        private EventManagerService eventManagerService;

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {

            if (!eventManagerService.isEventCreated()) {
                ChatInfo.WARNING.send(context, "Žiadny event momentálne nieje vytvorený!");
                return;
            }

            if (eventManagerService.isEventRunning() || eventManagerService.getEventManager().isCanStart()) {
                ChatInfo.WARNING.send(context, "Event už bol odštartovaný!");
                return;
            }

            EventManager eventManager = eventManagerService.getEventManager();
            WLPlayer wlPlayer = playerService.getWLPlayer(context.getPlayer());
            if(!eventManager.getPlayers().contains(wlPlayer)) {
                ChatInfo.WARNING.send(context, "Niesi na žiadnom evente!");
                return;
            }

            ChatInfo.SUCCESS.send(context, "Úspešne si opustil event!");
            eventManagerService.getEventManager().leavePlayerFromEvent(playerService.getWLPlayer(context.getPlayer()), EPlayerCauseEventLeave.LEAVE_TROUGHT_COMMAND);
        }
    }

    @Component
    @CommandLine.Command(name = "setrewards")
    @HasPermission("commands.event.reward")
    static class SetRewards implements Runnable, Listener {

        @Autowired
        private EventManagerService eventManagerService;

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {

            if (!eventManagerService.isEventCreated()) {
                ChatInfo.WARNING.send(context, "Žiadny event momentálne nieje vytvorený!");
                return;
            }

            if (eventManagerService.isEventRunning() || eventManagerService.getEventManager().isCanStart()) {
                ChatInfo.WARNING.send(context, "Event už bol odštartovaný!");
                return;
            }

            Inventory inventory = Bukkit.createInventory(context.getPlayer(), 9 * 3, "Nastavenia odmien pre event");
            context.getPlayer().openInventory(inventory);

        }

        @EventHandler(ignoreCancelled = true)
        private void onInventoryOpen(InventoryOpenEvent event) {

            if(!event.getView().getTitle().equalsIgnoreCase("Nastavenia odmien pre event"))
                return;

            if(eventManagerService == null)
                eventManagerService = App.getService(EventManagerService.class);

            if (!eventManagerService.isEventCreated())
                return;

            if (eventManagerService.isEventRunning() || eventManagerService.getEventManager().isCanStart()) {
                ChatInfo.SUCCESS.send(context, "Event už bol odštartovaný!");
                return;
            }

            event.getInventory().clear();
            eventManagerService.getEventManager().getRewardList().forEach((itemStack -> {
                if(itemStack == null)
                    return;

                event.getInventory().addItem(itemStack);
            }));
        }

        @EventHandler(ignoreCancelled = true)
        private void onInventoryClose(InventoryCloseEvent event) {

            if(!event.getView().getTitle().equalsIgnoreCase("Nastavenia odmien pre event"))
                return;

            if(eventManagerService == null)
                eventManagerService = App.getService(EventManagerService.class);

            if (!eventManagerService.isEventCreated())
                return;

            if (eventManagerService.isEventRunning() || eventManagerService.getEventManager().isCanStart()) {
                ChatInfo.SUCCESS.send(context, "Event už bol odštartovaný!");
                return;
            }

            eventManagerService.getEventManager().getRewardList().clear();
            Arrays.stream(event.getInventory().getContents()).forEach((itemStack -> {
                eventManagerService.getEventManager().addReward(itemStack);
            }));

        }
    }
}
