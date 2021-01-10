package sk.westland.core.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.player.WLPlayer;

import java.util.Objects;

public abstract class ItemMenu extends CustomInventory {

    public ItemMenu(@NotNull Type type, @NotNull String title) {
        super(type, title);
    }

    public ItemMenu(@NotNull Inventory inventory, @NotNull String title) {
        super(inventory, title);
    }

    protected abstract void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event);
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if (event.getInventory().getHolder() != this)
            return;

        switch (event.getAction())
        {
            case MOVE_TO_OTHER_INVENTORY:
            case HOTBAR_MOVE_AND_READD:
            case HOTBAR_SWAP:
                event.setCancelled(true);
                return;
        }

        if (event.getClickedInventory() != getInventory())
            return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            clickedItem = null;

        event.setResult(Event.Result.DENY);

        ItemStack cursor = event.getCursor();
        if (cursor == null || cursor.getType() == Material.AIR)
            cursor = null;

        if(clickedItem == null)
            return;

        onClick(player, event.getSlot(), clickedItem, cursor, event);

        /*
        if (cursor == null || cursor.getType() == Material.AIR)
            player.setItemOnCursor(null);
        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            event.setCurrentItem(null);
         */
    }

    // Must be public
    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event)
    {
        if (event.getInventory().getHolder() != this)
            return;
        if (!Objects.equals(event.getInventory(), getInventory()))
            return;

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);
    }

}
