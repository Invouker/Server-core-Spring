package sk.westland.world.blocks.type;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Directional;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.blocks.BlockLevel;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.blocks.CustomBlock;
import sk.westland.core.database.data.BlockData;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.services.BlockService;
import sk.westland.world.inventories.blocks.BreakerInventory;
import sk.westland.world.items.Materials;

import java.util.UUID;

public class BlockBreaker extends CustomBlock {

    private static final BlockType BLOCK_TYPE = BlockType.BLOCK_BREAKER;

    public BlockBreaker(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockData blockData, BlockService blockService) {
        super(owner, ownerUUID, location, blockLevel, Materials.Items.BLOCK_BREAKER.getCustomItem(), blockData, blockService);
        this.blockType = BLOCK_TYPE;
    }

    public BlockBreaker(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockService blockService) {
        super(owner, ownerUUID, location, blockLevel, Materials.Items.BLOCK_BREAKER.getCustomItem(), new BlockData(owner, ownerUUID, location, blockLevel, BLOCK_TYPE), blockService);
    }

    @Override
    public void onBlockLoad() {
        System.out.println("Loaded block");
    }

    @Override
    public void onBlockUpdate() {
        ItemStack itemStack = getItems().get(13);
        if(itemStack == null)
            return;

        if(!itemStack.getType().toString().toLowerCase().contains("pickaxe"))
            return;

        Directional facingDirection = (Directional) block.getBlockData();
        Block relativeBlock = block.getRelative(facingDirection.getFacing());

        ItemBuilder itemBuilder = new ItemBuilder(itemStack);
        itemBuilder.applyDurability((short) 3);
        getItems().put(13, itemBuilder.build());

        if (!blockedMaterial(relativeBlock.getType(),
                Material.AIR, Material.BEDROCK, Material.BEACON,
                Material.DISPENSER, Material.DROPPER, Material.PISTON,
                Material.STICKY_PISTON, Material.REDSTONE_LAMP,
                Material.HOPPER, Material.REDSTONE_WIRE, Material.REDSTONE_TORCH,
                Material.OBSERVER, Material.END_GATEWAY,
                Material.END_PORTAL, Material.END_PORTAL_FRAME, Material.ENDER_CHEST,
                Material.NETHER_PORTAL, Material.CHEST, Material.TRAPPED_CHEST)) {

            relativeBlock.breakNaturally(getItems().get(13)); // BLOCK_POSITION IN BlockBreakInventory
        }
    }

    private boolean blockedMaterial(Material mat, Material... blockedMaterial) {
        for (Material material : blockedMaterial) {
            if(mat == material)
                return true;
        }
        return false;
    }

    @Override
    public void onBlockTimeUpdate() {

    }

    @Override
    public void onBlockUnload() {

    }

    @Override
    public void onBlockInteract(PlayerInteractEvent event, BlockService blockService) {
        event.setCancelled(true);
        event.setUseInteractedBlock(Event.Result.DENY);

        BreakerInventory blockBreakerInventory = new BreakerInventory(blockService, this);
        blockBreakerInventory.open(event.getPlayer());
    }

    @Override
    public void onRedstoneActivate(BlockRedstoneEvent event) {

    }
}
