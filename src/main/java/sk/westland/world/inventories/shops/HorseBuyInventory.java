package sk.westland.world.inventories.shops;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.inventory.ItemShopMenu;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.services.HorseService;
import sk.westland.core.services.MoneyService;

public class HorseBuyInventory extends ItemShopMenu {

    private HorseService horseService;

    public HorseBuyInventory(HorseService horseService, MoneyService moneyService, Player player) {
        super(Type.Chest3, "Predajca koní", moneyService, player);
        this.horseService = horseService;

        buildAndAddItem(HorseService.Colors.BLACK, HorseService.Style.NONE, MoneyType.Gems, 2705);
        buildAndAddItem(HorseService.Colors.BROWN, HorseService.Style.NONE, MoneyType.Gems,2350);
        buildAndAddItem(HorseService.Colors.CHESTNUT, HorseService.Style.NONE, MoneyType.Gems,2100);
        buildAndAddItem(HorseService.Colors.CREAMY, HorseService.Style.NONE, MoneyType.Gems,2120);
        buildAndAddItem(HorseService.Colors.DARKBROWN, HorseService.Style.NONE, MoneyType.Gems,2570);
        buildAndAddItem(HorseService.Colors.GRAY, HorseService.Style.NONE, MoneyType.Gems,2300);
        buildAndAddItem(HorseService.Colors.WHITE, HorseService.Style.NONE, MoneyType.Gems,3460);

        buildAndAddItem(HorseService.HorseType.ZOMBIE, MoneyType.Shard, 110);
        buildAndAddItem(HorseService.HorseType.SKELETON, MoneyType.Shard, 150);
    }

    private void buildAndAddItem(HorseService.HorseType horseType, MoneyType moneyType, int price) {
        ItemBuilder itemStack = new ItemBuilder(horseService.buildHorse(horseType));
        addDisplayItem(itemStack.build().clone(), itemStack.removeLoreLine(5).build().clone(), moneyType, price);
    }

    private void buildAndAddItem(HorseService.Colors horseColors, HorseService.Style horseStyle, MoneyType moneyType, int price){
        ItemBuilder itemStack = new ItemBuilder(horseService.buildHorse(horseColors, horseStyle));
        addDisplayItem(itemStack.build().clone(), itemStack.removeLoreLine(6).build().clone(), moneyType, price);
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
