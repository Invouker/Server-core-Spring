package sk.westland.core.services;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.JoinMessages;
import sk.westland.core.enums.QuitMessages;
import sk.westland.core.event.player.WLPlayerJoinEvent;
import sk.westland.core.event.player.WLPlayerQuitEvent;
import sk.westland.core.utils.ChatInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageService implements Listener{

    @Autowired
    private PlayerService playerService;

    @Autowired
    private VaultService vaultService;

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

        String regexPat = "\\p{IsHan}";
        final Pattern CHINESE_REGEX = Pattern.compile(regexPat);
        Matcher matcher = CHINESE_REGEX.matcher(event.getMessage());

        if(matcher.find()) {
            ChatInfo.WARNING.send(player, "Používanie špeciálnych znakov nie je povolené!");
            event.setCancelled(true);
        }

        if(!player.isOp())
            return;

        if(!player.hasPermission("adminchat"))
            return;

        String message = event.getMessage();
        if(message.startsWith("!") || isPlayerInAdminChat(player)) {
            String adminChat = message;

            if(message.startsWith("!"))
                adminChat = message.substring(1);

            event.setCancelled(true);

            String finalAdminChat = adminChat;
            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter((target) -> target.hasPermission("adminchat"))
                    .forEach((target) -> {
                target.sendMessage("§c[§lADMINCHAT§c] §f" + event.getPlayer().getName() + ": §7" + finalAdminChat.replace('&', '§'));
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

        String activeQuitMessage = QuitMessages.values()[wlPlayer.getActiveQuitMessage()].formattedJoinMessage();
        String quitMessage = activeQuitMessage.replace("%player%", player.getName());

        sendMessage(getListOfActiveMessage(true), quitMessage);

        removePlayerFromAdminChat(player);

        //Bukkit.broadcast(quitMessage, "westland.option.message.quit");
    }

    @EventHandler
    private void onPlayerJoin(WLPlayerJoinEvent event) {
        System.out.println("WLPlayer Join");
        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        if(wlPlayer == null)
            return;

        if(wlPlayer.getActiveJoinMessage() <= 0)
            return;

        if(!wlPlayer.hasPermission("westland.message.quit"))
            return;

        String activeJoinMessage = JoinMessages.values()[wlPlayer.getActiveJoinMessage()].formattedJoinMessage();
        String joinMessage = activeJoinMessage.replace("%player%", player.getName());

        sendMessage(getListOfActiveMessage(true), joinMessage);
    }

    public void sendMessage(List<Player> players, String message) {
        players.forEach(player->player.sendMessage(message));
    }

    private List<Player> getListOfActiveMessage(boolean join) {
        List<Player> players = new ArrayList<>();

        for(WLPlayer wlPlayer : playerService.getWlPlayerList()) {
            if(wlPlayer.getPlayerOptions().isShowJoinMessage() && join)
                players.add(wlPlayer.getPlayer());

            if(wlPlayer.getPlayerOptions().isShowQuitMessage() && !join)
                players.add(wlPlayer.getPlayer());
        }
        return players;
    }
}
