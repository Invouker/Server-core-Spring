package sk.westland.core.enums;

import net.md_5.bungee.api.ChatColor;

public enum QuitMessages {

    BASIC("Predvolené", "Hráč %player% sa odpojil!", ChatColor.of("#269900") + "§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00")),
    KING("Si kráľ?", "Kráľ %player% sa odpojil!", ChatColor.of("#269900") + "§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00")),
    QUEEN("Si královna?", "Královna %player% sa odpojila!", ChatColor.of("#269900")+"§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00"));


    private final String name;
    private final String quitMessage;
    private final String prefix;

    private final ChatColor nameColor;
    private final ChatColor textColor;

    QuitMessages(String name, String quitMessage, String prefix, ChatColor nameColor, ChatColor textColor) {
        this.name = name;
        this.quitMessage = quitMessage;
        this.prefix = prefix;
        this.nameColor =  nameColor;
        this.textColor = textColor;
    }

    public String getName() {
        return name;
    }

    public String getQuitMessage() {
        return quitMessage;
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
        return getPrefix() + " " + getTextColor() + getQuitMessage().replace("%player%", getNameColor() + "%player%" + getTextColor());
    }

    public String formattedJoinMessageWithoutPrefix() {
        return getTextColor() + getQuitMessage().replace("%player%", getNameColor() + "%player%" + getTextColor());
    }
}
