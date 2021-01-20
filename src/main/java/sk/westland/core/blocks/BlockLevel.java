package sk.westland.core.blocks;

public enum BlockLevel {

    UNCOMMON(10),
    COMMON(9),
    BASIC(8),
    RARE(7),
    EPIC(6),
    LEGEND(5);

    private int cooldown;

    BlockLevel(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }
}
