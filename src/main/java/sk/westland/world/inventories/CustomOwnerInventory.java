package sk.westland.world.inventories;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.inventory.ItemShopMenu;
import sk.westland.core.player.WWPlayer;

public class CustomOwnerInventory extends ItemShopMenu {

    public CustomOwnerInventory() {
        super(Type.Chest4, "test");
    }

    @Override
    protected void onClick(@NotNull WWPlayer player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {

    }

    @Override
    protected void onOpen(WWPlayer player) {

    }

    @Override
    protected void onClose(WWPlayer player) {

    }


}
