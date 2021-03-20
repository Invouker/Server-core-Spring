package sk.westland.core.utils;

public enum ResFlag {
    MOB_CATCH("mob-catch"),
    BLOCK_PLACER("block-placer"),
    BLOCK_BREAKER("block-breaker"),
    MOB_GRINDER("mob-grinder")
    ;


    private String flagName;

    ResFlag(String flagName) {
        this.flagName = flagName;
    }

    public String getFlagName() {
        return flagName;
    }
}
