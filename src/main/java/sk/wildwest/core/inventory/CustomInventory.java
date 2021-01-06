package sk.wildwest.core.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.wildwest.core.player.WWPlayer;

public abstract class CustomInventory implements InventoryHolder, Listener {

    private Inventory inventory;
    private String inventoryName;

    public CustomInventory(String inventoryName, CustomInventorySize inventorySize) {
        this.inventory = Bukkit.createInventory(this, inventorySize.getSize(), inventoryName);
        this.inventoryName = inventoryName;

    }

    public CustomInventory(String inventoryName, CustomInventoryType inventorySize) {
        this.inventory = Bukkit.createInventory(this, inventorySize.getInventoryType(), inventoryName);
        this.inventoryName = inventoryName;
    }

    @EventHandler
    private void onInventoryClickEvent(@NotNull InventoryClickEvent event) {
        if(event.getInventory() == null)
            return;

        if(!event.getView().getTitle().equals(inventoryName))
            return;

        if(!(event.getWhoClicked() instanceof Player))
            return;

        if(event.getCurrentItem() == null)
            return;

        onClick(null, event.getSlot(), event.getRawSlot(), event.getCurrentItem(), event);

    }

    @EventHandler
    private void onInventoryOpen(@NotNull InventoryOpenEvent event) {
        if(event.getInventory() == null)
            return;

        if(!event.getView().getTitle().equals(inventoryName))
            return;

        if(!(event.getPlayer() instanceof Player))
            return;

        onOpen(null, event);

    }

    @EventHandler
    private void onInventoryClose(@NotNull InventoryCloseEvent event) {
        if(event.getInventory() == null)
            return;

        if(!event.getView().getTitle().equals(inventoryName))
            return;

        if(!(event.getPlayer() instanceof Player))
            return;

        onClose(null, event);

    }

    protected abstract void onClick(WWPlayer player, int slot, int rawSlot, ItemStack clickedItem, InventoryClickEvent event);
    protected abstract void onOpen(WWPlayer player, InventoryOpenEvent event);
    protected abstract void onClose(WWPlayer player, InventoryCloseEvent event);

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public enum CustomInventorySize {

        SIZE_9(9),
        SIZE_18(18),
        SIZE_27(27),
        SIZE_36(36),
        SIZE_45(45),
        SIZE_54(54);

        private int size;

        CustomInventorySize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    public enum CustomInventoryType {

        SIZE_5_HOPPER(InventoryType.HOPPER),
        SIZE_9_DROPPER(InventoryType.DROPPER),
        SIZE_9(InventoryType.CHEST),
        SIZE_18(InventoryType.CHEST),
        SIZE_27(InventoryType.CHEST),
        SIZE_36(InventoryType.CHEST),
        SIZE_45(InventoryType.CHEST),
        SIZE_54(InventoryType.CHEST);

        private InventoryType inventoryType;

        CustomInventoryType(InventoryType inventoryType) {
            this.inventoryType = inventoryType;
        }

        public InventoryType getInventoryType() {
            return inventoryType;
        }
    }
}
