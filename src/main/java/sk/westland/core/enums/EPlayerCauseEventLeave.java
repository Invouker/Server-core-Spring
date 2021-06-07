package sk.westland.core.enums;

public enum EPlayerCauseEventLeave {

    LEAVE_TROUGHT_COMMAND("pomocou príkazu"),
    PLAYER_QUIT("opustil server"),
    PLAYER_DIE("zomrel"),
    PLAYER_WIN("výhry"),
    EVENT_FORCE("vynútené opustenie"),
    NONE("none");

    private String cause;

    EPlayerCauseEventLeave(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
