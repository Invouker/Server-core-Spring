package sk.westland.core.jobs.rewards;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class JItemReward implements JIReward {

    private ItemStack itemStack;

    public JItemReward(ItemStack itemStack) {
        this.itemStack = itemStack;
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
            return "§f" + itemStack.getAmount() + "x " + itemStack.getItemMeta().getDisplayName();

        String typeName = itemStack.getType().toString().toLowerCase(Locale.ROOT).replace('_', ' ');
        return "§f" + itemStack.getAmount() + "x " + StringUtils.capitalize(typeName);
    }
}
