package sk.westland.core.items;

import org.bukkit.inventory.ItemStack;

public interface Craftable {

    CraftingRecipe getCraftingRecipe();

    ItemStack getItem();
    int getModelID();
}
