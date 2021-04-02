
package sk.westland.core.inventory;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.ImmutableTriple;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Triple;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.core.services.MoneyService;
import sk.westland.core.utils.ChatInfo;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class ItemShopMenu extends ItemMenu {

    private MoneyService moneyService;
    private Player player;

    public ItemShopMenu(Type type, String title, MoneyService moneyService, Player player) {
        super(type, title);

        items = new ItemStack[type.size];
        this.moneyService = moneyService;
        this.player = player;
    }

    public void updateInventory() {
        Inventory inventory = getInventory();

        int i = 0;
        for(ItemStack is : items)
            inventory.setItem(i++, is);
    }

    @Override
    protected void onClick(Player player, int slot, ItemStack item, ItemStack cursor, InventoryClickEvent event) {
        if(item == null || item.getType() == Material.AIR)
            return;

        Triple<ItemStack, MoneyType, Integer> shopItem = this.itemMap.getOrDefault(item, null);

        if(shopItem == null)
            return;

        ItemStack shopItemStack = shopItem.getLeft();
        MoneyType moneyType = shopItem.getMiddle();
        int itemPrice = shopItem.getRight();

        if(shopItemStack == null)
            return;

        if(moneyService.canPay(player, moneyType, itemPrice)) {
            // Pay for the item
            moneyService.pay(player, moneyType, itemPrice);

            // Git items to player
            for(ItemStack isDrop : player.getInventory().addItem(shopItemStack.clone()).values())
                player.getWorld().dropItemNaturally(player.getLocation(), isDrop); //THINK drop items which cannot be placed to player's inventory



            // Send info message
            ChatInfo.SUCCESS.send(
                    player,
                    "Úspešne si kúpil " + generateItemName(item) + "§f, za §6" + itemPrice + " " + moneyType.getMultipleName().toLowerCase(Locale.ROOT)
            );
        } else
            ChatInfo.WARNING.send(player, "Nemáš dostatok penazí!");
    }

    private String generateItemName(ItemStack itemStack) {
        if(itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName())
            return itemStack.getItemMeta().getDisplayName();

        String name = itemStack.getType().name().replace("_", " ").toLowerCase(Locale.ROOT);
        return StringUtils.capitalize(name);
    }

    // Key = Display item
    // Value:
    // - Item to give
    // - Type of money
    // - Amount of money
    private Map<ItemStack, Triple<ItemStack, MoneyType, Integer>> itemMap = new HashMap<>();
    private ItemStack[] items;

    protected void setItem(int index, @NotNull ItemStack isDisplay, @Nullable ItemStack isGive, @NotNull MoneyType moneyType, int price) {
        int size = getSize();
        if(index < 0 || index >= size)
            return;

        isDisplay = Nbt.setNbt_Bool(isDisplay, "ShopItem", true);

        if(isGive != null)
            this.itemMap.put(isDisplay, new ImmutableTriple<>(isGive, moneyType, price));

        this.items[index] = isDisplay;
    }

    private int addItem(@NotNull ItemStack isDisplay, @Nullable ItemStack isGive, @NotNull MoneyType moneyType, int price) {
        int index;
        int size = getSize();
        for(index = 0; index < size; index++)
            if(items[index] == null)
                break;
        if(index == size)
            return -1;

        isDisplay = Nbt.setNbt_Bool(isDisplay, "ShopItem", true);

        if(isGive != null)
            this.itemMap.put(isDisplay, new ImmutableTriple<>(isGive, moneyType, price));

        this.items[index] = isDisplay;
        return index;
    }

    protected int addDisplayItem(@NotNull ItemStack itemStack, ItemStack itemStackDisplay, MoneyType moneyType, int price) {
        ItemStack isDisplay = new ItemBuilder(itemStackDisplay)
                .addLore(
                        "§fCena:§a " + price + " " + moneyType.getMultipleName(),
                        "§7Aktuálne máš " + moneyService.get(player, moneyType) + " " + moneyType.getMultipleName().toLowerCase(Locale.ROOT),
                        "",
                        "§aKlikni pre zakúpenie!"
                )
                .build();
        return addItem(isDisplay, itemStack, moneyType, price);
    }

    protected int addItem(@NotNull ItemStack is, @NotNull MoneyType moneyType, int price) {
        ItemStack isDisplay = new ItemBuilder(is.clone())
                .addLore(
                        "§fCena§a " + price + " " + moneyType.getMultipleName(),
                        "§7Aktuálne máš " + moneyService.get(player, moneyType) + " " + moneyType.getMultipleName(),
                        "",
                        "§aKlikni pre zakúpenie!"
                )
                .build();
        return addItem(isDisplay, is, moneyType, price);
    }

    protected void setItem(int index, @NotNull ItemStack is, @NotNull MoneyType moneyType, int price) {
        ItemStack isDisplay = new ItemBuilder(is)
                .addLore("", "§eCena§f " + price + " " + moneyType.getMultipleName() )
                .build();
        setItem(index, isDisplay, is, moneyType, price);
    }

    protected void onOpen(@NotNull Player player)
    {
        updateInventory();
    }
}
