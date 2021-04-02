package sk.westland.core.enums;

public enum EServerData {

    VOTES_TOTAL(Integer.class)
    ;

    private final Class<?> clazz;

    EServerData(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
