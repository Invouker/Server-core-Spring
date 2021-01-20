package sk.westland.core.blocks;

import org.bukkit.Material;

public enum BlockType {

    BLOCK_PLACER(0, Material.DISPENSER),
    BLOCK_BREAKER(1, Material.DROPPER),
    MOB_KILLER(2, Material.DROPPER);

    private int id;
    private Material material;

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
