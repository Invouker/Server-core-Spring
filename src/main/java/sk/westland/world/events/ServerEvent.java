package sk.westland.world.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.springframework.stereotype.Component;
import sk.westland.core.App;
import sk.westland.core.WestLand;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.services.UtilsService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServerEvent implements Listener {

    private final List<String> motd;
    public ServerEvent() {
        motd = new ArrayList<>();

        motd.add(ChatColor.AQUA + "                    §lWestLand §7[1.16]\n" +
                ChatColor.GREEN + ChatColor.BOLD + "              NOVINKA: SLIMEFUN");
    }

    @EventHandler
    private void onServerPingEvent(ServerListPingEvent event) {
        event.setMotd(motd.get(App.getService(UtilsService.class).getRandomInt(motd.size())));
    }

    @EventHandler
    private void onPluginEnable(PluginEnableEvent eventPlugin) {
        ProtocolManager protocolManager = WestLand.getProtocolManager();

        if(protocolManager == null)
            throw new NullPointerException("ProtocolManager is not loaded or cannot be hooked into it");

        protocolManager.addPacketListener(
                new PacketAdapter(eventPlugin.getWestLand(), PacketType.Status.Server.SERVER_INFO) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        WrappedServerPing ping = event.getPacket().getServerPings().read(0);
                        ping.setPlayersOnline(eventPlugin.getWestLand().getServer().getOnlinePlayers().size() + 1);
                        //ping.setVersionName("I dont care what i will do");
                      //  WrappedGameProfile wrappedGameProfile = new WrappedGameProfile(UUID.randomUUID(), "idk ty kokot");
                        //ping.setPlayers(new ArrayList<>(Collections.singletonList(wrappedGameProfile)));

                        event.getPacket().getServerPings().write(0, ping);
                    }
                });
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommandPreprocess(ServerCommandEvent event) {
        if(event.getCommand().toLowerCase().startsWith("say")) {

            String message = ChatColor.translateAlternateColorCodes('?', event.getCommand().substring(4));
            if(message.toLowerCase().startsWith("!")) {
                Bukkit.broadcastMessage(message.substring(1));
                event.setCancelled(true);
            } else {
                event.setCommand("say " + message);
            }

        }

 //ChatColor.translateAlternateColorCodes('&', event.getMessage())
    }
}
