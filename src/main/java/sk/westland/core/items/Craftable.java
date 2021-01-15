package sk.westland.core.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public interface Craftable {

    NamespacedKey getNamespacedKey(Plugin plugin);
    CraftingRecipe getCraftingRecipe(Plugin plugin);

    ItemStack getItem();
    int getModelID();
}
