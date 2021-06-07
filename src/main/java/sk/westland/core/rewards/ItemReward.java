package sk.westland.core.rewards;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.items.ItemBuilder;
import sk.westland.world.items.Materials;

import java.util.Locale;

public class ItemReward implements IReward<ItemReward> {

    private final ItemStack itemStack;

    public ItemReward(ItemStack itemStack, int amount) {
        this.itemStack = new ItemBuilder(itemStack).setAmount(amount).build();
    }

    public ItemReward(Materials.Items items) {
        this.itemStack = items.getItem();
    }

    public ItemReward(Materials.Resources items) {
        this.itemStack = items.getItem();
    }

    public ItemReward(ItemBuilder itemBuilder) {
        this.itemStack = itemBuilder.build();
    }

    public ItemReward(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemReward(Material mat) {
        this.itemStack = new ItemStack(mat);
    }

    public ItemReward(Material mat, int amount) {
        this.itemStack = new ItemStack(mat, amount);
    }

    @Override
    public ItemReward reward(Player player) {
        if(player.getInventory().firstEmpty() == -1)
            return this;

        player.getInventory().addItem(itemStack);
        return this;
    }

    @Override
    public String render() {
        if(itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName())
            return itemStack.getAmount() + "x " + ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());

        String typeName = itemStack.getType().toString().toLowerCase(Locale.ROOT).replace('_', ' ');
        String capitalized = StringUtils.capitalize(typeName);
        return itemStack.getAmount() + "x " + ChatColor.stripColor(capitalized);
    }
}
