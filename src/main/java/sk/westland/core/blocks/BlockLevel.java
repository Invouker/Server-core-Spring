package sk.westland.core.blocks;

public enum BlockLevel {

    UNCOMMON(0,10),
    COMMON(1,9),
    BASIC(2,8),
    RARE(3,7),
    EPIC(4,6),
    LEGEND(5,5);

    private int id;
    private int cooldown;

    BlockLevel(int id, int cooldown) {
        this.id = id;
        this.cooldown = cooldown;
    }

    public int getId() {
        return id;
    }

    public int getCooldown() {
        return cooldown;
    }

    public static boolean isEqualsOrBetter(BlockLevel blockLevel) {
        int id = blockLevel.getId();
        for (BlockLevel blockLevelChild : BlockLevel.values()) {
            if(blockLevelChild.getId() > id)
                id = blockLevelChild.getId();
        }
        return false;
    }
}
