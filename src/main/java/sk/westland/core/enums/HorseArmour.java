package sk.westland.core.enums;

import org.bukkit.Material;

public enum HorseArmour {

    NONE(Material.AIR, -1),
    LEATHER(Material.LEATHER_HORSE_ARMOR, 0),
    IRON(Material.IRON_HORSE_ARMOR, 1),
    GOLDEN(Material.GOLDEN_HORSE_ARMOR, 2),
    DIAMOND(Material.DIAMOND_HORSE_ARMOR, 3);

    private final Material material;
    private final int id;

    HorseArmour(Material material, int id) {
        this.material = material;
        this.id = id;
    }

    public static HorseArmour findById(int id) {
        for(HorseArmour armour : values()) {
            if(armour.id == id)
                return armour;
        }
        return null;
    }

    public Material getMaterial() {
        return material;
    }

    public int getId() {
        return id;
    }


}
