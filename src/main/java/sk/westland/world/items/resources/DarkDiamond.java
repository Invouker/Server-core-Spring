package sk.westland.world.items.resources;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.items.*;
import sk.westland.world.items.Materials;

@Component
public class DarkDiamond extends CustomResourceItem implements Listener, Craftable {

    @Override
    public CraftingRecipe getCraftingRecipe() {
        return new CraftingRecipe("darkDiamond", RecipeType.Material, this)
                .shape( "ACA",
                        "CBC",
                        "ACA")
                .setIngredient('A', Material.OBSIDIAN)
                .setIngredient('B', Material.AIR)
                .setIngredient('C', recipeService.item(Materials.Resources.COMPRESSED_CARBON.getItem()))
                ;
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.PAPER)
                .setName("§rDark Diamond")
                .setModelId(getModelID())
                .build();
    }

    @Override
    public int getModelID() {
        return 7;
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        super.onPluginEnable(event);

        recipeService.registerRecipe(getCraftingRecipe());

        recipeService.registerRecipe(new CraftingRecipe("wlDiamond", RecipeType.Material, new ItemStack(Material.DIAMOND))
                .shape( "ABA",
                        "BAB",
                        "ABA")
                .setIngredient('A', Material.AIR)
                .setIngredient('B', recipeService.item(getItem()))
        );
    }
}
