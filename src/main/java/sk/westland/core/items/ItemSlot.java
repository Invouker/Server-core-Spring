package sk.westland.core.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemSlot {

    private InventoryHolder entity;
    private Inventory inventory;
    private int selectedSlot;

    public ItemSlot(InventoryHolder entity, Inventory inventory, int selectedSlot) {
        this.entity = entity;
        this.inventory = inventory;
        this.selectedSlot = selectedSlot;
    }

    public @Nullable
    ItemStack get() {
        return this.inventory.getItem(this.selectedSlot);
    }

    public void set(@Nullable ItemStack itemStack) {
        this.inventory.setItem(this.selectedSlot, itemStack);
    }

    public InventoryHolder getEntity() {
        return entity;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public static @Nullable ItemSlot findByItem(@Nullable InventoryHolder entity, @Nullable ItemStack similiar, boolean exactAmount) {
        if(entity == null || similiar == null) {
            return null;
        }

        Inventory inventory = entity.getInventory();

        if(entity instanceof Player) {
            Player player = (Player) entity;
            PlayerInventory playerInventory = player.getInventory();

            int heldSlot = playerInventory.getHeldItemSlot();

            if(checkSlot(playerInventory, heldSlot, similiar, exactAmount)) {
                return new ItemSlot(entity, playerInventory, heldSlot);
            }
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if(checkSlot(inventory, i, similiar, exactAmount)) {
                return new ItemSlot(entity, inventory, i);
            }
        }

        return null;
    }

    private static boolean checkSlot(@NotNull Inventory inventory, int slot, @Nullable ItemStack similiar, boolean exactAmount) {
        ItemStack foundStack = inventory.getItem(slot);
        return foundStack != null && similiar != null && foundStack.getType() == similiar.getType() && (exactAmount ? (foundStack.getAmount() == similiar.getAmount()) : (foundStack.getAmount() >= similiar.getAmount()));
    }

}
