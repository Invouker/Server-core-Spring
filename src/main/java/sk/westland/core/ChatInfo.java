package sk.westland.core;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public enum ChatInfo {

        GENERAL_INFO("§6§l[*] §e", ComponentBuilder.text("[*] ").bold(true).color(ChatColor.DARK_BLUE).build(), ChatColor.GRAY),
        UNAVAILABLE("§8§l> §7", ComponentBuilder.text("> ").bold(true).color(ChatColor.DARK_GRAY).build(), ChatColor.GRAY),
        SUCCESS("§2§l[#] §a", ComponentBuilder.text("[#] ").bold(true).color(ChatColor.DARK_GREEN).build(), ChatColor.GREEN),
        WARNING("§c§l[!] §e", ComponentBuilder.text("[!] ").bold(true).color(ChatColor.RED).build(), ChatColor.YELLOW),
        ERROR("§4§l[!!] §c", ComponentBuilder.text("[!!] ").bold(true).color(ChatColor.DARK_RED).build(), ChatColor.RED);

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

    public void send(Player player, String message) {
        player.sendMessage(prefix + message);
    }

    public void send(CommandSender sender, String message) {
        sender.sendMessage(prefix + message);
    }

    @Deprecated
    public String format(String message) {
        return this.prefix + message;
    }

}
