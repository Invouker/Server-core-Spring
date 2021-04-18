package sk.westland.core.enums;

public enum EPlayerTimeReward {

    Daily(86400L, "Denná odmena", "deň"),
    PremiumDaily(86400L, "VIP Denná odmena", "deň"),
    PremiumWeekly(604800L, "VIP Týždenná odmena", "týždeň"),
    Weekly(604800L, "Týždenná odmena", "týždeň"),
    Monthly(2592000L, "Mesačná odmena", "mesiac")
            ;

    private final long time;
    private final String name;
    private final String lore;

    EPlayerTimeReward(long time, String name, String lore) {
        this.time = time;
        this.name = name;
        this.lore = lore;
    }

    public String getLore() {
        return lore;
    }

    public long getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
