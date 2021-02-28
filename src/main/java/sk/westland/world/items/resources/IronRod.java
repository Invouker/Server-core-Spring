package sk.westland.world.items.resources;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.items.*;

public class IronRod extends CustomResourceItem implements Craftable {

    @Override
    public NamespacedKey getNamespacedKey(Plugin plugin) {
        return new NamespacedKey(plugin, "ironRod");
    }

    @Override
    public CraftingRecipe getCraftingRecipe(Plugin plugin) {
        return new CraftingRecipe(getNamespacedKey(plugin), RecipeType.Item, getItem())
                .shape("A  ", "A  ", "   ")
                .setIngredient('A', Material.IRON_INGOT);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.PAPER)
                .setName("§rIron rod")
                .setModelId(getModelID())
                .build();
    }

    @Override
    public int getModelID() {
        return 4;
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) { recipeService.registerRecipe(getCraftingRecipe(event.getWestLand())); }
}
