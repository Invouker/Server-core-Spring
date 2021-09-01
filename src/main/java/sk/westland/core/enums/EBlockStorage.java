package sk.westland.core.enums;

public enum EBlockStorage {

    ACTIVE("Active"),
    OWNER("Owner");


    private String name;

    EBlockStorage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
