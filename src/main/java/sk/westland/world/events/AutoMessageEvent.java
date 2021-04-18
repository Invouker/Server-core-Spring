package sk.westland.world.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.enums.EPlayerOptions;
import sk.westland.core.enums.EServerData;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.ServerDataService;
import sk.westland.core.utils.RunnableDelay;

@Component
public class AutoMessageEvent implements Listener, Runnable {

    @Autowired
    private PlayerService playerService;

    private final String autoMessagePrefix = ChatColor.of("#fffca6") + "§l[*]§r ";
    private int position = 0;
    private BukkitTask task;
    private final String[] autoMessages = new String[] {
            "Každý deň si môžte na spawne vybrať dennú odmenu!",
            "Aby ste zarábali peniaze zo zamestnania\n,musíš sa najskôr zamestnať príkazom §6'/prace'",
            "Ak si chceš zakúpiť kľúče od bedien, skús príkaz §6'/kluce'",
            "Pre prípadne nastavenia slúži príkaz §6'/nastavenia'",
            "Ak by si chcel vybrať odmenu zo zamestnania skús §6'/prace'",
            "Ak hľadáš menu kde najdeš ďalšie informácie, skús §6'/menu'"
    };
    private static AutoMessageEvent autoMessageEvent = null;

    public AutoMessageEvent() {
        autoMessageEvent = this;
    }

    @Autowired
    private ServerDataService serverDataService;

    @EventHandler(ignoreCancelled = true)
    private void onPluginEnable(PluginEnableEvent event) {
        reStart();
    }

    public void reStart() {
        stop();

        if(serverDataService.getIntData(EServerData.AUTOMESSAGE_TIME) == 0)
            serverDataService.setIntData(EServerData.AUTOMESSAGE_TIME, 60);

        int data = serverDataService.getIntData(EServerData.AUTOMESSAGE_TIME);
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(WestLand.getInstance(), this, 20*10, (20L * data));
    }

    public void stop() {
        if(task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public void run() {
        String text = autoMessages[position % autoMessages.length];
        Bukkit.getOnlinePlayers().stream()
                .filter((player -> EPlayerOptions.SHOW_AUTOMESSAGE.getPlayerOptions(playerService.getWLPlayer(player))))
                .forEach((player -> player.sendMessage(" \n" +autoMessagePrefix + text + " \n ")));
        position++;
    }


    public static AutoMessageEvent getAutoMessageEvent() {
        return autoMessageEvent;
    }
}
