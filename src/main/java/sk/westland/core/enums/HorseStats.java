package sk.westland.core.enums;

public enum HorseStats {

    //  CORE
    SPAWNED("spawned"),
    SADDLE("saddle"),
    HORSE_ID("horseID"),

    //  STATS
    JUMP("jump"), // 0 - 2
    SPEED("speed"),
    HEALTH("health"),
    COLOUR("color"),
    STYLE("style"),

    ARMOUR("armour"),
    ARMOUR_COLOR("leather_armour_col")
    ;

    private String stats;

    HorseStats(String stats) {
        this.stats = stats;
    }

    public String getStatName() {
        return stats;
    }
}
