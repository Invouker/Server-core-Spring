package sk.westland.core.inventory.rc;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.enums.InventoryChestType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class InventoryHandler {

    private String inventoryName;
    private InventoryHolder inventoryHolder;
    private InventoryChestType inventoryType;

    private Map<Integer, ItemStack> items = new HashMap<>();

    private final Inventory inventory;

    private Consumer<InventoryInteractEvent> inventoryInteractEventConsumer;
    private Consumer<InventoryDragEvent> inventoryDragEventConsumer;
    private Consumer<InventoryClickEvent> inventoryClickEventConsumer;
    private Consumer<InventoryOpenEvent> inventoryOpenEventConsumer;
    private Consumer<InventoryCloseEvent> inventoryCloseEventConsumer;

    public InventoryHandler(String inventoryName, InventoryHolder inventoryHolder, InventoryChestType inventoryType, Consumer<InventoryInteractEvent> inventoryInteractEventConsumer, Consumer<InventoryDragEvent> inventoryDragEventConsumer, Consumer<InventoryClickEvent> inventoryClickEventConsumer, Consumer<InventoryOpenEvent> inventoryOpenEventConsumer, Consumer<InventoryCloseEvent> inventoryCloseEventConsumer) {
        this.inventoryName = inventoryName;
        this.inventoryHolder = inventoryHolder;
        this.inventoryType = inventoryType;

        inventory = Bukkit.createInventory(inventoryHolder, inventoryType.getSize(), inventoryName);

        this.inventoryInteractEventConsumer = inventoryInteractEventConsumer;
        this.inventoryDragEventConsumer = inventoryDragEventConsumer;
        this.inventoryClickEventConsumer = inventoryClickEventConsumer;
        this.inventoryOpenEventConsumer = inventoryOpenEventConsumer;
        this.inventoryCloseEventConsumer = inventoryCloseEventConsumer;
    }

    public void updateInventory() {
        for(Map.Entry<Integer, ItemStack> itemStackEntry : items.entrySet()) {
            inventory.setItem(itemStackEntry.getKey(), itemStackEntry.getValue());
        }
    }

    public Consumer<InventoryOpenEvent> getInventoryOpenEventConsumer() {
        return inventoryOpenEventConsumer;
    }

    public void setInventoryOpenEventConsumer(Consumer<InventoryOpenEvent> inventoryOpenEventConsumer) {
        this.inventoryOpenEventConsumer = inventoryOpenEventConsumer;
    }

    public Consumer<InventoryCloseEvent> getInventoryCloseEventConsumer() {
        return inventoryCloseEventConsumer;
    }

    public void setInventoryCloseEventConsumer(Consumer<InventoryCloseEvent> inventoryCloseEventConsumer) {
        this.inventoryCloseEventConsumer = inventoryCloseEventConsumer;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public InventoryHolder getInventoryHolder() {
        return inventoryHolder;
    }

    public void setInventoryHolder(InventoryHolder inventoryHolder) {
        this.inventoryHolder = inventoryHolder;
    }

    public Consumer<InventoryInteractEvent> getInventoryInteractEventConsumer() {
        return inventoryInteractEventConsumer;
    }

    public void setInventoryInteractEventConsumer(Consumer<InventoryInteractEvent> inventoryInteractEventConsumer) {
        this.inventoryInteractEventConsumer = inventoryInteractEventConsumer;
    }

    public Consumer<InventoryDragEvent> getInventoryDragEventConsumer() {
        return inventoryDragEventConsumer;
    }

    public void setInventoryDragEventConsumer(Consumer<InventoryDragEvent> inventoryDragEventConsumer) {
        this.inventoryDragEventConsumer = inventoryDragEventConsumer;
    }

    public Consumer<InventoryClickEvent> getInventoryClickEventConsumer() {
        return inventoryClickEventConsumer;
    }

    public void setInventoryClickEventConsumer(Consumer<InventoryClickEvent> inventoryClickEventConsumer) {
        this.inventoryClickEventConsumer = inventoryClickEventConsumer;
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public void setItems(Map<Integer, ItemStack> items) {
        this.items = items;
    }

    public void setItems(int pos, ItemStack itemStack) {
        if(items == null)
            items = new HashMap<>();

        items.put(pos, itemStack);
    }

    public void addItems(ItemStack itemStack) {
        if(items == null)
            items = new HashMap<>();

        for(int a = 0; a < inventoryType.getSize(); a++) {
            if(items.get(a) != null) continue;

            items.put(a, itemStack);
        }
    }

    public InventoryChestType getInventoryType() {
        return inventoryType;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
