package sk.westland.core.blocks;

public enum BlockLevel {

    UNCOMMON(0,10, "I"),
    COMMON(1,9, "II"),
    BASIC(2,8, "III"),
    RARE(3,7, "IV"),
    EPIC(4,6, "V"),
    LEGEND(5,5, "VI");

    private int id;
    private int cooldown;
    private String romaCode;

    BlockLevel(int id, int cooldown, String romaCode) {
        this.id = id;
        this.cooldown = cooldown;
        this.romaCode = romaCode;
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

    public String getRomaCode() {
        return romaCode;
    }
}
