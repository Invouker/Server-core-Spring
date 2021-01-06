package sk.wildwest.core;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import sk.wildwest.core.inventory.CustomInventory;
import sk.wildwest.core.player.WWPlayer;

public class CustomOwnerInventory extends CustomInventory {

    public CustomOwnerInventory() {
        super("test", CustomInventorySize.SIZE_9);
    }


    @Override
    protected void onClick(WWPlayer player, int slot, int rawSlot, ItemStack clickedItem, InventoryClickEvent event) {

    }

    @Override
    protected void onOpen(WWPlayer player, InventoryOpenEvent event) {

    }

    @Override
    protected void onClose(WWPlayer player, InventoryCloseEvent event) {

    }

}
