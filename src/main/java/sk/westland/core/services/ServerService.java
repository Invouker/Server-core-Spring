package sk.westland.core.services;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ServerService implements Listener {

    public String getName() {
        return "Name? Of Server";
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {

    }
}
