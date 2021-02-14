package sk.westland.core.enums;

public enum HorseTier {

    UNCOMMON(0, 0.5,0.15,10),
    COMMON(1, 0.6,0.25,20),
    BASIC(2,0.7,0.35,30),
    RARE(3,0.8,0.45,40),
    LEGENDARY(4,0.9,0.55,50)
    ;

    // Jump
    // 0.5 - tier 1
    // 0.6 - tier 2
    // 0.7 - tier 3
    // 0.8 - tier 4
    // 0.9 - tier 5

    // Speed
    // 0.1 - tier 1
    // 0.2 - tier 2
    // 0.3 - tier 3
    // 0.4 - tier 4
    // 0.5 - tier 5

    // Health
    // 10 - tier 1
    // 20 - tier 2
    // 30 - tier 3
    // 40 - tier 4
    // 50 - tier 5

    private int tierID;

    private double jumpValue;
    private double speedValue;
    private int healthValue;

    HorseTier(int tierID, double jumpValue, double speedValue, int healthValue) {
        this.tierID = tierID;
        this.jumpValue = jumpValue;
        this.speedValue = speedValue;
        this.healthValue = healthValue;
    }

    public int getTierID() {
        return tierID;
    }

    public double getJumpValue() {
        return jumpValue;
    }

    public double getSpeedValue() {
        return speedValue;
    }

    public int getHealthValue() {
        return healthValue;
    }

    public static HorseTier findById(int id) {
        for (HorseTier horseTier : HorseTier.values()) {
            if(horseTier.getTierID() == id)
                return horseTier;
        }
        return null;
    }

    public static int getMaxTier() {
        return 4;
    }
}
