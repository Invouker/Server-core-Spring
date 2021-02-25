package sk.westland.core.enums;

public enum MoneyType {

    Shard("Shardy"),
    Gems("Gemy"),
    Money("Doláre");

    private String name;

    MoneyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
