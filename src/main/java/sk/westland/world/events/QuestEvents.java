package sk.westland.world.events;

import dev.alangomes.springspigot.context.Context;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.event.player.WLPlayerJoinEvent;
import sk.westland.core.event.player.WLPlayerMoveEvent;
import sk.westland.core.event.player.WLPlayerQuitEvent;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.quest.QuestLogMenu;
import sk.westland.core.quest.action.event.TaskCommandExecutedEvent;
import sk.westland.core.quest.storage.QuestProgressStorage;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.QuestService;

import java.util.HashMap;
import java.util.Map;


@CommandLine.Command(name = "quest_task_action")
@Component
public class QuestEvents implements Listener, Runnable {

    @Autowired
    private QuestService questService;

    @Autowired
    private PlayerService playerService;


    @Autowired
    private Context context;

    @CommandLine.Parameters(index = "0")
    private String questId;

    @CommandLine.Parameters(index = "1")
    private int taskId;

    @CommandLine.Parameters(index = "2")
    private String arg;
    /////////////////////////////////////////////////////////////////

    private Map<Player, QuestLogMenu> questLogMenuMap = new HashMap<>();

    @EventHandler
    private void onPlayerMove(WLPlayerMoveEvent event) {
        //questService.updatePlayerProgressEvent(playerService.getWLPlayer(event.getPlayer()), event);
    }

    @EventHandler
    private void onPlayerInteractWithNPCEvent(WLPlayerInteractWithNPCEvent event) {
        questService.updatePlayerProgressEvent(event.getWLPlayer(), event);
    }


    @Override
    public void run() {
        if (!(context.getSender() instanceof Player)) {
            return;
        }

        WLPlayer wlPlayer = playerService.getWLPlayer(context.getPlayer());

        if (wlPlayer == null) {
            return;
        }

        QuestProgressStorage progressStorage = questService.getQuestProgress(wlPlayer, this.questId);

        if (progressStorage == null) {
            Log.error("cheating " + wlPlayer.getName());
            return;
        }

        if (!progressStorage.isTaskIdActive(this.taskId)) {
            Log.error("cheating" + wlPlayer.getName());
            return;
        }

        questService.updatePlayerProgressEvent(wlPlayer, new TaskCommandExecutedEvent("quest_task_action", questId, taskId, arg));
    }


    /* --------------  QuestLog Menu start --------------- */

    @EventHandler
    private void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() != null && event.getItem().getType() == Material.WRITTEN_BOOK) {
            WLPlayer wlPlayer = playerService.getWLPlayer(player);

            if (wlPlayer == null)
                return;

            event.setCancelled(true);
            if (!questLogMenuMap.get(player).hasViewingPlayers()) {
                questLogMenuMap.get(player).open(player);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerAnimation(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        if (inventory.getItemInMainHand().getType() == Material.WRITTEN_BOOK || inventory.getItemInOffHand().getType() == Material.WRITTEN_BOOK) {
            WLPlayer wlPlayer = playerService.getWLPlayer(player);

            if (wlPlayer == null)
                return;

            event.setCancelled(true);
            if (!questLogMenuMap.get(player).hasViewingPlayers()) {
                questLogMenuMap.get(player).open(player);
            }
        }
    }

    @EventHandler
    private void initQuestLogMenu(WLPlayerJoinEvent event) {
        questLogMenuMap.put(event.getPlayer(), new QuestLogMenu(playerService, questService));
    }

    @EventHandler
    private void destroyLogMenu(WLPlayerQuitEvent event) {
        questLogMenuMap.remove(event.getPlayer());
    }
}
