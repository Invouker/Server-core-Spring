package sk.westland.world.blocks.type;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import sk.westland.core.blocks.BlockLevel;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.blocks.CustomBlock;
import sk.westland.core.database.data.BlockData;
import sk.westland.core.services.BlockService;
import sk.westland.world.items.Materials;

import java.util.UUID;

public class ChunkCollector extends CustomBlock {

    private static final BlockType BLOCK_TYPE = BlockType.CHUNK_COLLECTOR;

    public ChunkCollector(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockData blockData, BlockService blockService) {
        super(owner, ownerUUID, location, blockLevel, Materials.Items.CHUNK_COLLECTOR.getCustomItem(), blockData, blockService);
        this.blockType = BlockType.CHUNK_COLLECTOR;
    }

    public ChunkCollector(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockService blockService) {
        super(owner, ownerUUID, location, blockLevel, Materials.Items.CHUNK_COLLECTOR.getCustomItem(),  new BlockData(owner, ownerUUID, location, blockLevel, BLOCK_TYPE), blockService);
    }

    @Override
    public void onBlockLoad() {

    }

    @Override
    public void onBlockUpdate() {
        if(block.getType() != Material.CHEST)
            return;

        Location location = getLocation();
        for(Entity entity : location.getChunk().getEntities()) {
            if(entity.getType() == EntityType.DROPPED_ITEM) {
                Item item = (Item) entity;
                InventoryHolder inventoryHolder = (InventoryHolder) block.getState();
                int emptySlot = inventoryHolder.getInventory().firstEmpty();
                if(emptySlot == -1)
                    return;

                inventoryHolder.getInventory().addItem(item.getItemStack());
                block.getState().update();

                entity.remove();
            }
        }
    }

    @Override
    public void onBlockTimeUpdate() { // every tick

    }

    @Override
    public void onBlockUnload() {

    }

    @Override
    public void onBlockInteract(PlayerInteractEvent event, BlockService blockService) {

    }

    @Override
    public void onRedstoneActivate(BlockRedstoneEvent event) {

    }
}
