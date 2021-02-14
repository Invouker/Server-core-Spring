package sk.westland.world.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ServerEvent implements Listener {

    @EventHandler
    private void onServerPingEvent(ServerListPingEvent event) {
        event.setMotd(
                ChatColor.of("#39c7f1") + "                    §lWestLand §7[1.16]\n" +
                ChatColor.of("#EBF5FB") + "           §lPRÍPRAVA SURVIVAL SERVERU"
        );
    }

    @EventHandler
    private void onPluginEnable(PluginEnableEvent eventPlugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(eventPlugin.getPlugin(), PacketType.Status.Server.SERVER_INFO) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        WrappedServerPing ping = event.getPacket().getServerPings().read(0);
                        ping.setPlayersOnline(eventPlugin.getPlugin().getServer().getOnlinePlayers().size() + 1);

                      //  WrappedGameProfile wrappedGameProfile = new WrappedGameProfile(UUID.randomUUID(), "idk ty kokot");
                        //ping.setPlayers(new ArrayList<>(Collections.singletonList(wrappedGameProfile)));

                        event.getPacket().getServerPings().write(0, ping);
                    }
                });
    }
}
