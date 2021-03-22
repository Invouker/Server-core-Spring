package sk.westland.core.enums;

public enum MoneyType {

    Shard("Shard", "Shardov"),
    Gems("Gem", "Gemov"),
    Money("Dolár", "Dolárov");

    private String name;
    private String multipleName;

    MoneyType(String name, String multipleName) {
        this.name = name;
        this.multipleName = multipleName;
    }

    public String getName() {
        return name;
    }

    public String getMultipleName() {
        return multipleName;
    }
}
