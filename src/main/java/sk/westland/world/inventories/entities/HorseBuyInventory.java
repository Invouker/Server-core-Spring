package sk.westland.world.inventories.entities;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.inventory.ItemShopMenu;
import sk.westland.core.services.MoneyService;
import sk.westland.world.items.Materials;

public class HorseBuyInventory extends ItemShopMenu {

    public HorseBuyInventory(MoneyService moneyService) {
        super(Type.Chest3, "Predajca koní", moneyService);


        addItem(Materials.Items.SADDLE_ITEM.getItem(), MoneyType.Gems, 250);
        addItem(Materials.Items.SADDLE_ITEM.getItem(), MoneyType.Gems, 250);
        addItem(Materials.Items.SADDLE_ITEM.getItem(), MoneyType.Gems, 250);
    }

    @Override
    protected void itemInit() {
    }

    @Override
    protected void onClick(Player player, int slot, ItemStack item, ItemStack cursor, InventoryClickEvent event) {
        super.onClick(player, slot, item, cursor, event);

    }

    @Override
    protected void onClose(@NotNull Player player) {

    }
}
