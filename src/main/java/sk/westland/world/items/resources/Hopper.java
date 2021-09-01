package sk.westland.world.items.resources;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.items.Craftable;
import sk.westland.core.items.CraftingRecipe;
import sk.westland.core.items.CustomResourceItem;
import sk.westland.core.items.RecipeType;

@Component
public class Hopper extends CustomResourceItem implements Listener, Craftable {

    @Override
    public CraftingRecipe getCraftingRecipe() {
        return new CraftingRecipe("hopper", RecipeType.Item, getItem())
                .shape("IAI", "IEI", "AIA")
                .setIngredient('A', Material.AIR)
                .setIngredient('E', Material.ENDER_CHEST)
                .setIngredient('I', Material.IRON_BLOCK);
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        recipeService.registerRecipe(getCraftingRecipe());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.HOPPER);
    }

    @Override
    public int getModelID() {
        return 0;
    }


}
