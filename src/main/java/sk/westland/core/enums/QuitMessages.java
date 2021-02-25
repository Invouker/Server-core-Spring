package sk.westland.core.enums;

import net.md_5.bungee.api.ChatColor;

public enum QuitMessages {

    KING("Si kráľ?", "Kráľ %player% sa odpojil!", ChatColor.of("#269900") + "§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00")),
    QUEEN("Si královna?", "Královna %player% sa odpojila!", ChatColor.of("#269900")+"§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00"));


    private String name;
    private String joinMessage;
    private String prefix;

    private ChatColor nameColor;
    private ChatColor textColor;

    QuitMessages(String name, String joinMessage, String prefix, ChatColor nameColor, ChatColor textColor) {
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
