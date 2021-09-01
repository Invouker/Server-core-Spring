package sk.westland.core.enums;

public enum EPlayerCauseEventLeave {

    LEAVE_TROUGHT_COMMAND("Opustil event"),
    PLAYER_QUIT("Opustil server"),
    PLAYER_DIE("Smrť"),
    PLAYER_WIN("Výhra"),
    EVENT_FORCE("Nasilu odpojený"),
    NONE("None/Error");

    private String cause;

    EPlayerCauseEventLeave(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
