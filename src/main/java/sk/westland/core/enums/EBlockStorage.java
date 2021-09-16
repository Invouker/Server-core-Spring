package sk.westland.core.enums;

public enum EBlockStorage {

    ACTIVE("Active"),
    OWNER("Owner"),
    DISTANCE("Distance"),

    ENABLED("true"),
    DISABLED("false")
;

    private String name;

    EBlockStorage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
