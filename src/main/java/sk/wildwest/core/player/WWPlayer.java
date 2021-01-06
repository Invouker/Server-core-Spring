package sk.wildwest.core.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WWPlayer {

    private Set<UUID> players = new HashSet<>();

    @EventHandler
    private void onPlayerConnect(PlayerJoinEvent event) {

    }
}
