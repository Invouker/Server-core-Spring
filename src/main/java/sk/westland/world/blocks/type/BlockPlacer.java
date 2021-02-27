package sk.westland.world.blocks.type;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.blocks.BlockLevel;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.blocks.CustomBlock;
import sk.westland.core.database.data.BlockData;
import sk.westland.core.services.BlockService;
import sk.westland.world.inventories.blocks.PlacerInventory;
import sk.westland.world.items.Materials;

import java.util.Map;
import java.util.UUID;

public class BlockPlacer extends CustomBlock {

    private static final BlockType BLOCK_TYPE = BlockType.BLOCK_PLACER;

    public BlockPlacer(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockData blockData, BlockService blockService) {
        super(owner, ownerUUID, location, blockLevel, Materials.Items.BLOCK_PLACER.getCustomItem(), blockData, blockService);
        this.blockType = BLOCK_TYPE;
    }

    public BlockPlacer(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockService blockService) {
        super(owner, ownerUUID, location, blockLevel, Materials.Items.BLOCK_PLACER.getCustomItem(),  new BlockData(owner, ownerUUID, location, blockLevel, BLOCK_TYPE), blockService);
    }

    @Override
    public void onBlockLoad() {
        System.out.println("Loaded block");
    }

    @Override
    public void onBlockTimeUpdate() {

    }

    @Override
    public void onBlockUpdate() {
        ItemStack itemStack = null;

        if(items == null || items.isEmpty())
            return;

        for(Map.Entry<Integer, ItemStack> itemStackEntry : items.entrySet()) {
            if(itemStackEntry == null) continue;
            if(itemStackEntry.getValue() == null) continue;
            if(itemStackEntry.getValue().getType() == Material.AIR) continue;

            itemStack = itemStackEntry.getValue();
        }

        if(itemStack == null)
            return;

        if(!itemStack.getType().isBlock())
            return;

        if(!(block.getBlockData() instanceof Directional))
            return;

       Directional facingDirection = (Directional) block.getBlockData();
       Block relativeBlock = block.getRelative(facingDirection.getFacing());

       if(relativeBlock.getType() != Material.AIR)
           return;

       relativeBlock.setType(itemStack.getType());
       itemStack.setAmount(itemStack.getAmount()-1);
    }

    @Override
    public void onBlockInteract(PlayerInteractEvent event, BlockService blockService) {
        event.setCancelled(true);
        event.setUseInteractedBlock(Event.Result.DENY);

        PlacerInventory blockPlacerInventory = new PlacerInventory(blockService, this);
        blockPlacerInventory.open(event.getPlayer());
    }

    @Override
    public void onRedstoneActivate(BlockRedstoneEvent event) {
        Dispenser dispenser = null;
        if(block instanceof Dispenser)
            dispenser = (Dispenser) block.getState();

        if(dispenser == null)
            return;

        if(dispenser.getInventory().isEmpty())
            return;

        ItemStack selectedItem = null;
    }

    @Override
    public void onBlockUnload() {
        System.out.println("Unloaded block");
    }


}
