package sk.westland.world.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.springframework.stereotype.Component;

@Component
public class ServerEvent implements Listener {

    @EventHandler
    private void onServerPingEvent(ServerListPingEvent event) {
        event.setMotd(
                ChatColor.of("#1E3859") + "                §m----<<§r "+ ChatColor.of("#A7D9D9") +"§lWestland "+ChatColor.of("#1E3859")+"§m>>----§r\n" +
                ChatColor.of("#4B8CA6")+"           §oServer je momentálne v príprave"
        );
    }

}
