package sk.westland.core.utils;

import dev.alangomes.springspigot.context.Context;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.entity.player.WLPlayer;

public enum ChatInfo {

        GENERAL_INFO("§6§l[*] §f", ComponentBuilder.text("[*] ").bold(true).color(ChatColor.DARK_BLUE).build(), ChatColor.GRAY),
        UNAVAILABLE("§8§l> §f", ComponentBuilder.text("> ").bold(true).color(ChatColor.DARK_GRAY).build(), ChatColor.GRAY),
        SUCCESS("§b§l[!] §f", ComponentBuilder.text("[#] ").bold(true).color(ChatColor.DARK_GREEN).build(), ChatColor.GREEN),
        WARNING("§c§l[!] §c", ComponentBuilder.text("[!] ").bold(true).color(ChatColor.RED).build(), ChatColor.YELLOW),
        ERROR("§4§l[!!] §c", ComponentBuilder.text("[!!] ").bold(true).color(ChatColor.DARK_RED).build(), ChatColor.RED),
        DEBUG("§c[DEBUG] §c", ComponentBuilder.text("[DEBUG]  ").bold(true).color(ChatColor.RED).build(), ChatColor.RED),
        COMMAND_HELPER(ChatColor.of("#005ce6") +"§l~~ ", ComponentBuilder.text("§l ").bold(true).color(ChatColor.of("#FF04AA")).build(), ChatColor.of("#FF04AA"));

    private String prefix;
    private BaseComponent component;
    private ChatColor messageColor;

    ChatInfo(String prefix, net.md_5.bungee.api.chat.BaseComponent component, net.md_5.bungee.api.ChatColor messageColor) {
        this.prefix = prefix;
        this.component = component;
        this.messageColor = messageColor;
    }

    public String getPrefix() {
        return prefix;
    }

    public BaseComponent getComponentPrefix() {
        return component;
    }

    public ChatColor getMessageColor() {
        return messageColor;
    }

    @NotNull
    public BaseComponent formatMessage(@NotNull BaseComponent component) {
        BaseComponent message = this.component == null ? null : this.component.duplicate();
        if(message == null) {
            message = component.duplicate();
            if(messageColor != null)
                message.setColor(messageColor);
        } else {
            BaseComponent msg = component.duplicate();
            if(messageColor != null)
                msg.setColor(messageColor);
            if(msg.isBoldRaw() == null)
                msg.setBold(false);

            message.addExtra(msg);
        }

        return message;
    }

    public static void sendTitle(String message, String subMessage) {
        Bukkit.getOnlinePlayers().forEach((player) -> player.sendTitle(message, subMessage, 30, 80, 30));
    }

    public static void setActionBar(String message) {
        Bukkit.getOnlinePlayers().forEach((player) -> player.sendActionBar(message));
    }

    public void send(Player player, String message) {
        player.sendMessage(prefix + message);
    }

    public void send(Context context, String message) {
        context.getSender().sendMessage(prefix + message);
    }

    public void send(WLPlayer player, String message) {
        player.getPlayer().sendMessage(prefix + message);
    }

    public void sendCommandHelperS(CommandSender commandSender, String baseCommand, String... commands) {
        commandSender.sendMessage(prefix + " Command Helper " + prefix);
        for (String command : commands) {
            commandSender.sendMessage("§l/" +baseCommand + " " + command);
        }
        commandSender.sendMessage("§a");
    }

    public void sendCommandHelperW(WLPlayer wlPlayer, String baseCommand, String... commands) {
        sendCommandHelperS(wlPlayer.getPlayer(), baseCommand, commands);
    }

    public void sendCommandHelperP(Player player, String baseCommand, String... commands) {
        sendCommandHelperS(player, baseCommand, commands);
    }

    public void send(CommandSender sender, String message) {
        sender.sendMessage(prefix + message);
    }

    public void sendAll(String message) {
        Bukkit.getOnlinePlayers().forEach((player) -> send(player, message));
    }

    public void sendAdmin(String message) {
        Bukkit.getOnlinePlayers().stream().filter((player -> player.hasPermission("chatinfo.admin"))).forEach((player) -> send(player, message));
    }

    @Deprecated
    public String format(String message) {
        return this.prefix + message;
    }

}
