package sk.westland.core.blocks;

import org.bukkit.Material;

public enum BlockType {

    BLOCK_PLACER(0, Material.DISPENSER),
    BLOCK_BREAKER(1, Material.DROPPER),
    MOB_KILLER(2, Material.DROPPER),
    MINER(3, Material.CHEST),
    ANIMAL_BREEDER(4, Material.DISPENSER),
    WORTH_CHEST(5, Material.CHEST),
            ;

    private final int id;
    private final Material material;

    BlockType(int id, Material material) {
        this.id = id;
        this.material = material;
    }

    public int getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }
}
