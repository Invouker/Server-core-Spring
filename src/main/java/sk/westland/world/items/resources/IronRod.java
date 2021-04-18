package sk.westland.world.items.resources;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.items.*;

@Component
public class IronRod extends CustomResourceItem implements Craftable, Listener {

    @Override
    public CraftingRecipe getCraftingRecipe() {
        return new CraftingRecipe("ironRod", RecipeType.Item, getItem())
                .shape("IAA", "IAA", "AAA")
                .setIngredient('A', Material.AIR)
                .setIngredient('I', Material.IRON_INGOT);
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
    public void onPluginEnable(PluginEnableEvent event) {
        recipeService.registerRecipe(getCraftingRecipe());
    }
}
