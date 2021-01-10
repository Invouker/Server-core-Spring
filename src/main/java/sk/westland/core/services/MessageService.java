package sk.westland.core.services;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.player.WLPlayer;

@Service
public class MessageService implements Listener {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PermissionService permissionService;

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);

        if(wlPlayer.getActiveQuitMessage() <= 0)
            return;

        if(!wlPlayer.hasPermission("westland.message.quit"))
            return;

        event.setQuitMessage(null);
        String activeQuitMessage = QuitMessage.values()[wlPlayer.getActiveQuitMessage()].formattedJoinMessage();
        String quitMessage = activeQuitMessage.replace("%player%", player.getName());
        Bukkit.broadcast(quitMessage, "westland.option.message.quit");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);



        if(wlPlayer.getActiveJoinMessage() <= 0)
            return;

        if(!wlPlayer.hasPermission("westland.message.quit"))
            return;

        event.setJoinMessage(null);
        String activeJoinMessage = JoinMessage.values()[wlPlayer.getActiveJoinMessage()].formattedJoinMessage();
        String joinMessage = activeJoinMessage.replace("%player%", player.getName());
        Bukkit.broadcast(joinMessage, "westland.option.message.quit");
    }

    public enum JoinMessage {

        KING("Si kráľ?", "Kráľ %player% sa pripojil!", ChatColor.of("#269900") + "§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00")),
        QUEEN("Si královna?", "Královna %player% sa pripojila!", ChatColor.of("#269900")+"§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00"));


        private String name;
        private String joinMessage;
        private String prefix;

        private ChatColor nameColor;
        private ChatColor textColor;

        JoinMessage(String name, String joinMessage, String prefix, ChatColor nameColor, ChatColor textColor) {
            this.name = name;
            this.joinMessage = joinMessage;
            this.prefix = prefix;
            this.nameColor =  nameColor;
            this.textColor = textColor;
        }

        public String getName() {
            return name;
        }

        public String getJoinMessage() {
            return joinMessage;
        }

        public String getPrefix() {
            return prefix;
        }

        public ChatColor getNameColor() {
            return nameColor;
        }

        public ChatColor getTextColor() {
            return textColor;
        }

        public String formattedJoinMessage() {
            return getPrefix() + " " + getTextColor() + getJoinMessage().replace("%player%", getNameColor() + "%player%" + getTextColor());
        }

        public String formattedJoinMessageWithoutPrefix() {
            return getTextColor() + getJoinMessage().replace("%player%", getNameColor() + "%player%" + getTextColor());
        }
    }

    public enum QuitMessage {

        KING("Si kráľ?", "Kráľ %player% sa odpojil!", ChatColor.of("#269900") + "§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00")),
        QUEEN("Si královna?", "Královna %player% sa odpojila!", ChatColor.of("#269900")+"§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00"));


        private String name;
        private String joinMessage;
        private String prefix;

        private ChatColor nameColor;
        private ChatColor textColor;

        QuitMessage(String name, String joinMessage, String prefix, ChatColor nameColor, ChatColor textColor) {
            this.name = name;
            this.joinMessage = joinMessage;
            this.prefix = prefix;
            this.nameColor =  nameColor;
            this.textColor = textColor;
        }

        public String getName() {
            return name;
        }

        public String getJoinMessage() {
            return joinMessage;
        }

        public String getPrefix() {
            return prefix;
        }

        public ChatColor getNameColor() {
            return nameColor;
        }

        public ChatColor getTextColor() {
            return textColor;
        }

        public String formattedJoinMessage() {
            return getPrefix() + " " + getTextColor() + getJoinMessage().replace("%player%", getNameColor() + "%player%" + getTextColor());
        }

        public String formattedJoinMessageWithoutPrefix() {
            return getTextColor() + getJoinMessage().replace("%player%", getNameColor() + "%player%" + getTextColor());
        }
    }

}
