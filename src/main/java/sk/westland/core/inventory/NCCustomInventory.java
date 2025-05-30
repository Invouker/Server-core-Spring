package sk.westland.core.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class NCCustomInventory extends CustomInventory {

    public NCCustomInventory(@NotNull CustomInventory.Type type, @NotNull String title) {
        super(type, title);
        itemInit();
    }

    public NCCustomInventory(@NotNull Inventory inventory, @NotNull String title) {
        super(inventory, title);
        itemInit();
    }

    public NCCustomInventory(@NotNull CustomInventory.Type type, @NotNull String title, String withoutInit) {
        super(type, title);
    }

    protected abstract void itemInit();
    protected abstract void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event);
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if (event.getInventory().getHolder() != this)
            return;

        /*
        switch (event.getAction())
        {
            case MOVE_TO_OTHER_INVENTORY:
            case HOTBAR_MOVE_AND_READD:
            case HOTBAR_SWAP:
                event.setCancelled(true);
                return;
        }*/

        if (event.getClickedInventory() != getInventory())
            return;

        Player player = (Player) event.getWhoClicked();

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            clickedItem = null;


        ItemStack cursor = event.getCursor();
        if (cursor == null || cursor.getType() == Material.AIR)
            cursor = null;

        if(clickedItem == null)
            return;

        if(clickedItem.isSimilar(CLOSE_INVENTORY_ITEM)) {
            event.setCancelled(true);
            close(player);
            return;
        }

        onClick(player, event.getSlot(), clickedItem, cursor, event);

    }

    // Must be public
    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event)
    {
        if (event.getInventory().getHolder() != this)
            return;
        if (!Objects.equals(event.getInventory(), getInventory())) {
            return;
        }

        /*
        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

         */
    }

}
