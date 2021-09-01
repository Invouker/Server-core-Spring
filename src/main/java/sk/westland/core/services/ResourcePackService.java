package sk.westland.core.services;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.springframework.beans.factory.annotation.Autowired;
import sk.westland.core.App;
import sk.westland.core.WestLand;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.EBadge;
import sk.westland.core.enums.EEmoji;
import sk.westland.core.enums.EServerData;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.ComponentBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourcePackService implements Listener, BeanWire {

    @Autowired
    private MessageService messageService;

    @Autowired
    private PlayerService playerService;

    private final List<Player> playersWithResourcePack = new ArrayList<>();

    @EventHandler
    private void onPluginEnable(PluginEnableEvent event) {
        ServerDataService serverDataService = App.getService(ServerDataService.class);

        String data = serverDataService.getStringData(EServerData.PLAYERS_WITH_RP_AFTER_RESTART);
        if(data == null)
            return;

        String[] players = data.split(",");
        for (String player_ : players) {
            Player player = Bukkit.getPlayer(player_);
            if(player != null)
                addToResourcePack(player);
        }

        App.getService(ServerDataService.class).setStringData(EServerData.PLAYERS_WITH_RP_AFTER_RESTART, "");
    }

    @EventHandler
    private void onServerDisable(PluginDisableEvent event) {
        if(!event.getPlugin().getName().equals(WestLand.getInstance().getName()))
            return;

        StringBuilder stringBuilder = new StringBuilder();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(hasPlayerResourcePack(onlinePlayer))
                stringBuilder
                        .append(onlinePlayer.getName())
                        .append(",");
        }
        String data = stringBuilder.toString();
        if(data.length() > 0)
        App.getService(ServerDataService.class).setStringData(EServerData.PLAYERS_WITH_RP_AFTER_RESTART, data.substring(0, data.length()-1));
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(hasPlayerResourcePack(player)) {
            playersWithResourcePack.remove(player);
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + event.getPlayer().getName() + " parent remove resourcepack");
        }
    }

    private void addToResourcePack(Player player) {
        if(hasPlayerResourcePack(player))
            return;

        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent add resourcepack");
        playersWithResourcePack.add(player);
    }

    @EventHandler
    private void onResourcePackStatusEvent(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), () -> {
            if(event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
                System.out.println("Hráč " + event.getPlayer().getName() + " si povolil ResourcePack!");
                Bukkit.dispatchCommand(player, "cmi ctext welcome");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "skin update " + event.getPlayer().getName());
                addToResourcePack(player);
            }
        }, 10L);
    }

    public Group getGroupByPrimary(Player player) {
        String[] groups = App.getService(VaultService.class).getPerms().getPlayerGroups(null, player);
        String findGroup = "default";
        for (int i = 0; i < groups.length; i++) {
            if(groups[i].equalsIgnoreCase("resourcepack"))
                continue;

            findGroup = groups[i];
        }
        return Group.findByName(findGroup);
    }

    //TODO: OZNAČOVANIE v chate
    //@EventHandler
    private void onAsyncChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {

        if(event.getFormat().equalsIgnoreCase("cancel")) {
            event.setCancelled(true);
            return;
        }

        if(checkWebsite(event.getMessage())) {
            ChatInfo.WARNING.sendAdmin("Hráč napísal do chatu URL: §7§o'" + event.getMessage()  + "'");
            ChatInfo.ERROR.send(event.getPlayer(), "Reklama bola zablokovaná!");
            event.setFormat("");
            event.setCancelled(true);
            return;
        }

        if(checkIp(event.getMessage())) {
            ChatInfo.WARNING.sendAdmin("Hráč napísal do chatu IP: §7§o'" + event.getMessage()  + "'");
            ChatInfo.ERROR.send(event.getPlayer(), "Reklama bola zablokovaná!");
            event.setFormat("");
            event.setCancelled(true);
            return;
        }

        String regexPat = "\\p{IsHan}";
        final Pattern CHINESE_REGEX = Pattern.compile(regexPat);
        Matcher matcher = CHINESE_REGEX.matcher(event.getMessage());

        if(matcher.find()) {
            ChatInfo.WARNING.send(event.getPlayer(), "Používanie špeciálnych znakov nie je povolené!");
            event.setCancelled(true);
        }

        event.setMessage(findPlayer(event.getPlayer(), event.getMessage()));
        Bukkit.getConsoleSender().sendMessage("[Chat] " + event.getPlayer().getName() + ": " + event.getMessage());

        Bukkit.getOnlinePlayers().forEach(player -> {

            var group = getGroupByPrimary(event.getPlayer());

            var hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT,new TextComponent[] {
                    new TextComponent(PlaceholderAPI.setPlaceholders(event.getPlayer(), "§b§l%player_name%") + "\n"),
                    new TextComponent(PlaceholderAPI.setPlaceholders(event.getPlayer(), "§8Informácie") + "\n"),
                    new TextComponent("" + "\n"),
                    new TextComponent(PlaceholderAPI.setPlaceholders(event.getPlayer(), "&fShardy: &a%westland_shards%") + "\n"),
                    new TextComponent(PlaceholderAPI.setPlaceholders(event.getPlayer(), "&fGemy: &2%westland_gems%") + "\n"),
                    new TextComponent("" + "\n"),
                    new TextComponent(PlaceholderAPI.setPlaceholders(event.getPlayer(), "&fPeniaze: &6%cmi_user_balance_formated%") + "\n"),
                    new TextComponent("" + "\n"),
                    new TextComponent(PlaceholderAPI.setPlaceholders(event.getPlayer(), "&fRola: &7-") + "\n"),
                    new TextComponent(PlaceholderAPI.setPlaceholders(event.getPlayer(), "Nahratý čas: &e%cmi_user_playtime_hoursf%") + "\n"),
                    new TextComponent("" + "\n"),
                    new TextComponent("§aKlikni pre napísanie správy!")
            });

            var clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + event.getPlayer().getName() + " ");

            {
                if (hasPlayerResourcePack(player)) {
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

                    var x = ComponentBuilder.empty()

                            .extra(eBadge == null ?
                                    ComponentBuilder.empty().build() : ComponentBuilder.text(eBadge.getCharacter())
                                    .hover(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {
                                            new TextComponent("§b§l" + eBadge.getName() + "\n"),
                                            new TextComponent("§8Informácie" + "\n"),
                                            new TextComponent("§7Odznak ktorý dostal hráč" + "\n"),
                                            new TextComponent("§r" + "\n"),
                                            new TextComponent("§7" + cutLongString(eBadge.getDescription()) + "\n"),
                                            new TextComponent("§r" + "\n"),
                                            new TextComponent("§aKlikni pre nastavenie odznaku!"),
                                    }))
                                    .click(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/badge"))
                                    .build())
                            .extra(ComponentBuilder.text(group.getRpPrefix())
                                    .click(clickEvent).hover(hoverEvent)
                                    .build())
                            .extra(ComponentBuilder.text(" " + event.getPlayer().getName())
                                    .click(clickEvent).hover(hoverEvent)
                                    .color(group.getPrefixColor()).build())
                            .extra(ComponentBuilder.text(": §r" + event.getMessage()).build())
                            .build();

                    player.spigot().sendMessage(x);
                } else {
                    var x = ComponentBuilder.empty()
                            .extra(ComponentBuilder.text(group.getPrefix())
                                    .click(clickEvent).hover(hoverEvent)
                                    .color(group.getPrefixColor())
                                    .bold(true)
                                    .build())
                            .extra(ComponentBuilder.text(" " + event.getPlayer().getName())
                                    .click(clickEvent).hover(hoverEvent)
                                    .color(group.getPrefixColor()).build())
                            .extra(ComponentBuilder.text(": §r" + event.getMessage()).build())
                            .build();


                    player.spigot().sendMessage(x);
                }
            }
        });
        event.setCancelled(true);
    }

    private String cutLongString(String text) {
        if(text.length() <= 25) {
            return text;
        }
        if(text.length() > 25 && text.length() < 50) {
            return text.substring(0, 25) + "\n" + text.substring(25);
        }

        if(text.length() > 50) {
            return text.substring(0, 25) + "\n" + text.substring(25, 50) + "\n" + text.substring(50);
        }

        return text;
    }

    private String findPlayer(Player player, String text) {
        String[] message = text.split(" ");
        for (int i = 0; i < message.length; i++) {
            Player target = Bukkit.getPlayer(message[i]);
            if(Bukkit.getOnlinePlayers().contains(target)) {

                if(target == null)
                    continue;

                if(player.getName().contains(message[i]))
                    continue;

                target.sendTitle("§eZmienka v chate!", "§fHráč §7" + target.getName() + " §fťa označil.", 20,40,60);
                target.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                message[i] = "§r" + ChatColor.of("#FFFFB3") + message[i] + "§r";
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : message) {
            stringBuilder.append(s).append(" ");
        }

        return stringBuilder.toString();
    }

    private boolean checkIp(String message) {
        Pattern pattern = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]):[0-9]+$");
        Pattern pattern_ = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
        if(pattern.matcher(message).matches() || pattern_.matcher(message).matches()) {
            return true;
        }
        return false;
    }


    private boolean checkWebsite(String message) {
        if(message.contains("spigot") || message.contains("westland") || message.contains("facebook")|| message.contains("instagram") )
            return false;

        Pattern pattern = Pattern.compile("(?:[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?\\.)+[a-z0-9][a-z0-9-]{0,61}[a-z0-9]");
        if(pattern.matcher(message).matches()) {
            return true;
        }
        return false;
    }

    public boolean hasPlayerResourcePack(Player player) {
        return playersWithResourcePack.contains(player);
    }

    public List<Player> getPlayersWithResourcePack() {
        return playersWithResourcePack;
    }

    public enum Group {

        LEADER(1,"妎", ChatColor.of("#d33a53"), "LEADER"),
        ADMIN(2,"院", ChatColor.of("#d33a53"), "ADMIN"),
        DEV(3,"總", ChatColor.of("#d4770b"), "DEV"),
        MODERATOR(4,"總", ChatColor.of("#e159f3"),"MOD"),
        BUILDER(5,"榮", ChatColor.of("#2284b5"),"BUILDER"),
        SUPPORT(6,"站",ChatColor.of("#8fd1a8"), "Support"),
        EVENTER(7,"網",ChatColor.of("#eb8c19"), "Eventer"),
        TWITCH(8,"台",ChatColor.of("#9d19cc"), "Twitch"),
        YOUTUBE(9,"版",ChatColor.of("#fb526e"), "Youtube"),
        LEGION(10,"民",ChatColor.of("#3fd1d9"), "Legion"),
        HERO(11,"國",ChatColor.of("#6fbf60"), "Hero"),
        ELITE(12,"韻",ChatColor.of("#d09c42"), "Elite"),
        DEFAULT(13,"此",ChatColor.GRAY, "Hrac");

        private final int prioritySorting;

        private final String rpPrefix;

        private final ChatColor prefixColor;
        private final String prefix;

        Group(int prioritySorting, String rpPrefix, ChatColor prefixColor, String prefix) {
            this.prioritySorting = prioritySorting;
            this.rpPrefix = rpPrefix;
            this.prefixColor = prefixColor;
            this.prefix = prefix;
        }

        public int getPrioritySorting() {
            return prioritySorting;
        }

        public String getRpPrefix() {
            return rpPrefix;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getFormattedPrefix() {
            return prefixColor + prefix + "§r";
        }

        public ChatColor getPrefixColor() {
            return prefixColor;
        }

        public static Group findByName(String name) {
            for(Group group : Group.values()) {
                if(group.getPrefix().equalsIgnoreCase(name)) {
                    return group;
                }
            }
            return Group.DEFAULT;
        }
    }
}
