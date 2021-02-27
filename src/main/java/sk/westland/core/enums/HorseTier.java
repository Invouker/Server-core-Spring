package sk.westland.core.enums;

public enum HorseTier {

    NONE(0, 0.5,0.15,10), // Tento nikto nikdy nedostane
    COMMON(1, 0.5,0.25,15),
    BASIC(2,0.6,0.30,20),
    EPIC(3,0.7,0.35,25),
    RARE(4,0.8,0.40,30),
    ULTRA(5,0.9,0.45,35),
    LEGENDARY(6,1,0.50,40)
    ;

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
        int highest = 0;
        for (HorseTier horseTier : HorseTier.values()) {
            if(horseTier.getTierID() > highest) {
                highest = horseTier.getTierID();
            }
        }
        return highest;
    }
}
