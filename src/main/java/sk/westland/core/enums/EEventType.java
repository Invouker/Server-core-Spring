package sk.westland.core.enums;

public enum EEventType {

    FALLEN_BLOCKS("Padajúce bloky", "fallenBlocks")
    ;

    private final String eventName;
    private final String regionName;

    EEventType(String eventName, String regionName) {
        this.eventName = eventName;
        this.regionName = regionName;
    }

    public String getEventName() {
        return eventName;
    }

    public String getRegionName() {
        return regionName;
    }
}
