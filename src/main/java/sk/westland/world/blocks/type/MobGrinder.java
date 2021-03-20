package sk.westland.world.blocks.type;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import sk.westland.core.blocks.BlockLevel;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.blocks.CustomBlock;
import sk.westland.core.database.data.BlockData;
import sk.westland.core.services.BlockService;
import sk.westland.core.utils.ResFlag;
import sk.westland.core.utils.Utils;
import sk.westland.world.items.Materials;

import java.util.EnumSet;
import java.util.UUID;

public class MobGrinder extends CustomBlock {

    private static final BlockType BLOCK_TYPE = BlockType.MOB_GRINDER;
    private static final int DISTANCE = 3;

    public MobGrinder(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockData blockData, BlockService blockService) {
        super(owner, ownerUUID, location, blockLevel, Materials.Items.MOB_GRINDER.getCustomItem(), blockData, blockService);
        this.blockType = BLOCK_TYPE;
    }

    public MobGrinder(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockService blockService) {
        super(owner, ownerUUID, location, blockLevel, Materials.Items.MOB_GRINDER.getCustomItem(),  new BlockData(owner, ownerUUID, location, blockLevel, BLOCK_TYPE), blockService);
    }

    @Override
    public void onBlockLoad() {
        System.out.println("Loaded block Mob grinder");
    }

    @Override
    public void onBlockUpdate() {
        if(!(block.getBlockData() instanceof Directional))
            return;

        Directional directional = (Directional) block.getBlockData();
        BlockDirection blockDirection = BlockDirection.getBlockFace(directional.getFacing());

        int x = blockDirection.getX();
        int y = blockDirection.getY();
        int z = blockDirection.getZ();

        Location location = block.getLocation().clone();
        location.setX(location.getX() + x);
        location.setY(location.getY() + y);
        location.setZ(location.getZ() + z);

        if(location.getWorld() == null)
            return;

        int killEntityLimit = 0;
        switch(blockLevel) {
            case UNCOMMON: {
                killEntityLimit = 1;
                break;
            }
            case COMMON: {
                killEntityLimit = 2;
                break;
            }
            case BASIC: {
                killEntityLimit = 3;
                break;
            }
            case RARE: {
                killEntityLimit = 4;
                break;
            }
            case EPIC: {
                killEntityLimit = 5;
                break;
            }
            case LEGEND: {
                killEntityLimit = Integer.MAX_VALUE;
                break;
            }
        }

        EnumSet<EntityType> blockedEntityTypes = EnumSet.of(
                EntityType.GHAST,EntityType.GUARDIAN,EntityType.HOGLIN,EntityType.PIGLIN_BRUTE,EntityType.PILLAGER,
                EntityType.RAVAGER, EntityType.SHULKER, EntityType.STRAY, EntityType.VEX, EntityType.WITHER_SKELETON,
                EntityType.ZOGLIN, EntityType.ENDER_DRAGON, EntityType.WITHER, EntityType.WITHER_SKULL, EntityType.PIGLIN,
                EntityType.STRIDER, EntityType.ZOMBIFIED_PIGLIN, EntityType.HORSE, EntityType.SKELETON_HORSE, EntityType.ZOMBIE_HORSE,
                EntityType.PLAYER);

        location.getWorld().getEntities()
                .stream()
                .filter((entity -> entity.getLocation().distance(location) < DISTANCE))
                .filter(entity -> entity instanceof Damageable)
                .filter((entity -> Utils.locationResPermission(getOwner(), location, ResFlag.MOB_GRINDER)))
                .filter((entity -> Utils.locationResPermission(getOwner(), blockLocation, ResFlag.MOB_GRINDER)))
                .filter(entity -> !blockedEntityTypes.contains(entity.getType()))
                .limit(killEntityLimit)
                .forEach(entity -> {
                    ((Damageable) entity).damage(Integer.MAX_VALUE);
                    if(!BlockLevel.isEqualsOrBetter(BlockLevel.RARE))
                        return;
                });
    }

    @Override
    public void onBlockTimeUpdate() { }

    @Override
    public void onBlockUnload() {

    }

    @Override
    public void onBlockInteract(PlayerInteractEvent event, BlockService blockService) {
        event.setCancelled(true);
        event.setUseInteractedBlock(Event.Result.DENY);
    }

    @Override
    public void onRedstoneActivate(BlockRedstoneEvent event) {

    }


    private enum BlockDirection {

        NORTH(0, 1, -2, BlockFace.NORTH),
        EAST(2, 1, 0, BlockFace.EAST),
        SOUTH(0, 1, 2, BlockFace.SOUTH),
        WEST(-2, 1, 0, BlockFace.WEST),
        UP(0, 2, 0, BlockFace.UP),
        DOWN(0, -2, 0,BlockFace.DOWN);

        private int x;
        private int y;
        private int z;
        private BlockFace blockFace;

        BlockDirection(int x, int y, int z, BlockFace blockFace) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.blockFace = blockFace;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public BlockFace getBlockFace() {
            return blockFace;
        }

        public static BlockDirection getBlockFace(BlockFace blockFace_) {
            for (BlockDirection blockDirection : values()) {
                if(blockDirection.getBlockFace() == blockFace_)
                    return blockDirection;
            }
            return null;
        }
    }
}
