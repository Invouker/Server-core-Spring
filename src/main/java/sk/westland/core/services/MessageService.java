package sk.westland.core.services;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.springframework.beans.factory.annotation.Autowired;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.EEmoji;
import sk.westland.core.enums.JoinMessages;
import sk.westland.core.enums.QuitMessages;
import sk.westland.core.event.player.WLPlayerJoinEvent;
import sk.westland.core.event.player.WLPlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class MessageService implements Listener, BeanWire {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private VaultService vaultService;

    private final List<Player> activeAdminChat = new ArrayList<>();

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
/*
 // Player with resource pack
                    EBadge eBadge = null;
                    WLPlayer wlPlayer = playerService.getWLPlayer(event.getPlayer());
                    eBadge = wlPlayer.getActiveBadge();

                    for(EEmoji eEmoji : EEmoji.values()) {
                        String message = event.getMessage().replaceAll(eEmoji.getText(), eEmoji.getReplacement());
                        event.setMessage(message);
                    }

                    var b =  ComponentBuilder.text(eBadge.getCharacter())
                            .hover(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {
                                    new TextComponent("§b§l" + eBadge.getName() + "\n"),
                                    new TextComponent("§8Informácie" + "\n"),
                                    new TextComponent("§7Odznak ktorý dostal hráč" + "\n"),
                                    new TextComponent("§r" + "\n"),
                                    new TextComponent("§7" + cutLongString(eBadge.getDescription()) + "\n"),
                                    new TextComponent("§r" + "\n"),
                                    new TextComponent("§aKlikni pre nastavenie odznaku!"),
                            }));
* */

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        for(EEmoji eEmoji : EEmoji.values()) {
            String message = event.getMessage().replaceAll(eEmoji.getText(), eEmoji.getReplacement());
            event.setMessage(message);
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

            //event.setFormat("cancel");

            String finalAdminChat = adminChat;
            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter((target) -> target.hasPermission("adminchat"))
                    .forEach((target) -> {
                target.sendMessage("§c[§lADMINCHAT§c] §f" + event.getPlayer().getName() + ": §7" + finalAdminChat.replace('&', '§'));
            });

            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerQuitEvent(WLPlayerQuitEvent event) {
        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);

        if(wlPlayer.getActiveQuitMessage() < 0)
            return;

        String activeQuitMessage;
        try {
            Thread.currentThread().getContextClassLoader().loadClass("sk.westland.core.enums.QuitMessages");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(!wlPlayer.hasPermission("westland.message.quit"))
            activeQuitMessage = QuitMessages.values()[0].formattedJoinMessage();
        else
            activeQuitMessage = QuitMessages.values()[wlPlayer.getActiveQuitMessage()].formattedJoinMessage();


        String quitMessage = PlaceholderAPI.setPlaceholders(player, activeQuitMessage);
        System.out.println(quitMessage);
        sendMessage(getListOfActivePlayerMessage(false), quitMessage);

        removePlayerFromAdminChat(player);

        //Bukkit.broadcast(quitMessage, "westland.option.message.quit");
    }

    @EventHandler
    private void onPlayerJoin(WLPlayerJoinEvent event) {
        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        if(wlPlayer == null)
            return;

        if(wlPlayer.getActiveJoinMessage() < 0)
            return;

        String activeJoinMessage;
        if(!wlPlayer.hasPermission("westland.message.join"))
            activeJoinMessage = JoinMessages.values()[0].formattedJoinMessage();
        else
            activeJoinMessage = JoinMessages.values()[wlPlayer.getActiveJoinMessage()].formattedJoinMessage();


        String joinMessage = PlaceholderAPI.setPlaceholders(player, activeJoinMessage);
        System.out.println(joinMessage);
        sendMessage(getListOfActivePlayerMessage(true), joinMessage);
    }

    public void sendMessage(List<Player> players, String message) {
        players.forEach(player->player.sendMessage(message));
    }

    private List<Player> getListOfActivePlayerMessage(boolean join) {
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
