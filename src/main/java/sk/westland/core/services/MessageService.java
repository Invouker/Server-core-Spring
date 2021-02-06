package sk.westland.core.services;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import picocli.CommandLine;
import sk.westland.core.event.player.WLPlayerJoinEvent;
import sk.westland.core.event.player.WLPlayerQuitEvent;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.utils.ChatInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MessageService implements Listener{

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PermissionService permissionService;

    private List<Player> activeAdminChat = new ArrayList<>();


    public boolean isPlayerInAdminChat(Player player) {
        return activeAdminChat.contains(player);
    }

    public void addPlayerToAdminChat(Player player) {
        if(isPlayerInAdminChat(player))
            return;

        activeAdminChat.add(player);
    }

    public void removePlayerFromAdminChat(Player player) {
        if(!isPlayerInAdminChat(player))
            return;

        activeAdminChat.remove(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if(!player.isOp())
            return;

        if(!player.hasPermission("adminchat"))
            return;

        String message = event.getMessage();
        if(message.startsWith("!") || message.startsWith("?") || isPlayerInAdminChat(player)) {
            String adminChat = message.substring(1);
            event.setCancelled(true);

            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter((target) -> target.hasPermission("adminchat"))
                    .forEach((target) -> {
                target.sendMessage("§c[§lADMINCHAT§c] §f" + event.getPlayer().getName() + ": §7" + adminChat.replace('&', '§'));
            });
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerQuitEvent(WLPlayerQuitEvent event) {
        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);

        if(wlPlayer.getActiveQuitMessage() <= 0)
            return;

        if(!wlPlayer.hasPermission("westland.message.quit"))
            return;

        String activeQuitMessage = QuitMessage.values()[wlPlayer.getActiveQuitMessage()].formattedJoinMessage();
        String quitMessage = activeQuitMessage.replace("%player%", player.getName());

        sendMessage(getListOfActiveMessage(true), quitMessage);

        removePlayerFromAdminChat(player);

        //Bukkit.broadcast(quitMessage, "westland.option.message.quit");
    }

    @EventHandler
    private void onPlayerJoin(WLPlayerJoinEvent event) {
        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        if(wlPlayer == null)
            return;

        if(wlPlayer.getActiveJoinMessage() <= 0)
            return;

        if(!wlPlayer.hasPermission("westland.message.quit"))
            return;

        String activeJoinMessage = JoinMessage.values()[wlPlayer.getActiveJoinMessage()].formattedJoinMessage();
        String joinMessage = activeJoinMessage.replace("%player%", player.getName());

        sendMessage(getListOfActiveMessage(true), joinMessage);
    }

    public void sendMessage(List<Player> players, String message) {
        players.forEach(player->player.sendMessage(message));
    }

    private List<Player> getListOfActiveMessage(boolean join) {
        List<Player> players = new ArrayList<>();
        for(Map.Entry<Player, WLPlayer> entry : playerService.getWLPlayers().entrySet()) {
            WLPlayer wlPlayer = entry.getValue();
            if(wlPlayer.getUserOption().isShowJoinMessage() && join)
                players.add(wlPlayer.getPlayer());

            if(wlPlayer.getUserOption().isShowQuitMessage() && !join)
                players.add(wlPlayer.getPlayer());
        }

        return players;
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
