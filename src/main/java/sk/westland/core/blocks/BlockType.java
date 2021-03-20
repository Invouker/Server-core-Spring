package sk.westland.core.blocks;

import org.bukkit.Material;
import sk.westland.world.blocks.type.BlockBreaker;
import sk.westland.world.blocks.type.BlockPlacer;
import sk.westland.world.blocks.type.MobGrinder;
import sk.westland.world.blocks.type.WorthChest;

public enum BlockType {

    BLOCK_PLACER(0, Material.DISPENSER, BlockPlacer.class),
    BLOCK_BREAKER(1, Material.DROPPER, BlockBreaker.class),
    MOB_GRINDER(2, Material.DROPPER, MobGrinder.class),
    WORTH_CHEST(3, Material.CHEST, WorthChest.class),
    //MINER(3, Material.CHEST),
    //ANIMAL_BREEDER(4, Material.DISPENSER),
            ;

    private final int id;
    private final Material material;
    private final Class<? extends CustomBlock> clazz;

    BlockType(int id, Material material, Class<? extends CustomBlock> clazz) {
        this.id = id;
        this.material = material;
        this.clazz = clazz;
    }

    public Class<? extends CustomBlock> getClazz() {
        return clazz;
    }

    public int getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }
}
