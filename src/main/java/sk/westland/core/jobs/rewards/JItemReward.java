package sk.westland.core.jobs.rewards;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.utils.ChatInfo;

import java.util.Locale;

public class JItemReward implements JIReward {

    private ItemStack itemStack;

    public JItemReward(ItemStack itemStack, int amount) {
        this.itemStack = new ItemBuilder(itemStack).setAmount(amount).build();
    }

    public JItemReward(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public JItemReward(Material mat) {
        this.itemStack = new ItemStack(mat);
    }

    public JItemReward(Material mat, int amount) {
        this.itemStack = new ItemStack(mat, amount);
    }

    @Override
    public JItemReward reward(Player player) {
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
