package sk.westland.core.blocks.multiblock;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IMBRecipe {

    ItemStack getResult();
    List<ItemStack> getItems();
}
