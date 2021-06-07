package sk.westland.core.enums;

import net.md_5.bungee.api.ChatColor;

public enum JoinMessages {

    BASIC("Predvolené", "Hráč %player% sa pripojil!", ChatColor.of("#269900") + "§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00")),
    KING("Si kráľ?", "Kráľ %player% sa pripojil!", ChatColor.of("#269900") + "§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00")),
    QUEEN("Si královna?", "Královna %player% sa pripojila!", ChatColor.of("#269900")+"§l[!]", ChatColor.of("#53ff1a"), ChatColor.of("#33cc00")),
    PREMIUM("Premium Hráč", "Prémium hráč %player% sa práve napojil!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff")),
    DIERA("Diera do sveta", "Hráč %player% spravil dieru do sveta, po pripojení!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff")),
    BANNED("Zabanovaný", "Hráč %player% sa pripojil ako zabanovaný!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff")),
    VYHODENY("Vyhodený", "Hráč %player% sa pripojil ako vyhodený!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff")),
    ULITA("Ulitník", "Hráč %player% tvári po pripojení ako ulita!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff")),
    ZANEDBAVAC("Zanedbávač", "Hráč %player% zanedbal pripojenie!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff")),
    ZOMBIE("Zombík", "Zombík %player% sa znovuzrodil a pripojil sa na server!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff")),
    SKELETON("Skeleton", "Skeleton %player% sa znovuzrodil a pripojil sa na server!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff")),
    MR("Mr.", "Mr.%player% sa práve napojil!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff")),
    MRS("Mrs.", "Mrs.%player% sa práve napojil!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff")),
    MRROBOT("MR.Robot", "Mr.Robot %player% sa práve napojil!", ChatColor.of("#1a8cff")+"§l[!]", ChatColor.of("#ffffff"), ChatColor.of("#80aaff"))
    ;

    private final String name;
    private final String joinMessage;
    private final String prefix;

    private final ChatColor nameColor;
    private final ChatColor textColor;

    JoinMessages(String name, String joinMessage, String prefix, ChatColor nameColor, ChatColor textColor) {
        this.name = "§f" + name;
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
