
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

    public ItemShopMenu(Type type, String title, MoneyService moneyService) {
        super(type, title);

        items = new ItemStack[type.size];
        this.moneyService = moneyService;
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
                    "Úspešne si kúpil " + generateItemName(item) + ", za " + itemPrice + " " + moneyType.getName()
                    /*ComponentBuilder.translate(
                            "shop.buy.ok",
                            ComponentBuilder.item(item).build(),
                            ComponentBuilder.text(itemPrice + "").build(),
                            PriceHelper.getMoneyComponent(moneyType)
                    ).build()*/
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

    /*
    public static boolean canPay(Player player, MoneyType moneyType, int amount)
    {
        PlayerInventory inventory = player.getInventory();
        for(ItemStack is : inventory)
        {
            if(is == null || is.getType() != material)
                continue;
            amount -= is.getAmount();

            if(amount <= 0)
                return true;
        }

        return amount <= 0;
    }

    public static void pay(Player player, Material material, int amount)
    {
        PlayerInventory inventory = player.getInventory();
        for(int i = 0; i < inventory.getSize() && amount > 0; i++)
        {
            ItemStack is = inventory.getItem(i);
            if(is == null || is.getType() != material)
                continue;

            if(amount > is.getAmount())
            {
                inventory.setItem(i, null);
            }
            else
            {
                ItemStack is_clone = is.clone();
                is_clone.setAmount(is.getAmount() - amount);
                inventory.setItem(i, is_clone);
            }

            amount -= is.getAmount();
        }
    }
*/
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

    protected int addItem(@NotNull ItemStack isDisplay, @Nullable ItemStack isGive, @NotNull MoneyType moneyType, int price) {
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

    protected int addItem(@NotNull ItemStack is, @NotNull MoneyType moneyType, int price) {
        ItemStack isDisplay = new ItemBuilder(is.clone())
                .addLore("", "§eCena§f " + price + " " + moneyType.getMultipleName())
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
