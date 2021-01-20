package sk.westland.world.inventories.blocks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.blocks.CustomBlock;
import sk.westland.core.inventory.CustomBlockInventory;
import sk.westland.core.services.BlockService;

import java.util.Map;

public class BlockPlacerInventory extends CustomBlockInventory {

    private BlockService blockService;
    private CustomBlock customBlock;

    private static final int[] BLOCK_POSITION = new int[] { 10,11,12, 19,20,21,  28,29,30 };

    public BlockPlacerInventory(BlockService blockService, CustomBlock customBlock) {
        super(Type.Chest5, "Block placer");
        this.blockService = blockService;
        this.customBlock = customBlock;
    }

    @Override
    protected void itemInit() {
        for (int i = 0; i < getInventory().getSize(); i++) {
            if(isBlockPosition(i)) continue;
            getInventory().setItem(i, GRAY_GLASS);
        }

        setItemCloseInventory(4, 4);
    }

    private boolean isBlockPosition(int i) {
        for (int x = 0; x < BLOCK_POSITION.length; x++)
            if(i == BLOCK_POSITION[x])
                return true;
        return false;
    }

    @Override
    protected void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        if(!isBlockPosition(slot)) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    @Override
    protected void onOpen(@NotNull Player player) {
        for (int x = 0; x < BLOCK_POSITION.length; x++) {
            int i = BLOCK_POSITION[x];
            getInventory().setItem(i, customBlock.getItems().get(i));
        }
        customBlock.setCanBeActive(false);
    }

    @Override
    protected void onClose(@NotNull Player player) {
        customBlock.setCanBeActive(true);

        Map<Integer, ItemStack> items = customBlock.getItems();
        for (int inventoryItem = 0; inventoryItem < BLOCK_POSITION.length; inventoryItem++) {
            int i = BLOCK_POSITION[inventoryItem];

            ItemStack itemStack = getInventory().getItem(inventoryItem);
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                items.remove(i);
                continue;
            }
                items.put(i, getInventory().getItem(i));
        }
        customBlock.setItems(items);
        blockService.saveBlockData(customBlock.getBlockData());
    }
}
