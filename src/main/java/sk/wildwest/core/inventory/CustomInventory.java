package sk.wildwest.core.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import sk.wildwest.core.player.WWPlayer;

public abstract class CustomInventory implements InventoryHolder {

    private Inventory inventory;

    public CustomInventory(String inventoryName, CustomInventorySize inventorySize) {
        this.inventory = Bukkit.createInventory(this, inventorySize.getSize(), inventoryName);

    }

    public CustomInventory(String inventoryName, CustomInventoryType inventorySize) {
        this.inventory = Bukkit.createInventory(this, inventorySize.getInventoryType(), inventoryName);
    }

    public abstract void onClick(WWPlayer player);
    public abstract void onOpen();
    public abstract void onClose();

    @Override
    public Inventory getInventory() {
        return null;
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
