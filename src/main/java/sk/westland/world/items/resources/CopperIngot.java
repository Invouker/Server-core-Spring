package sk.westland.world.items.resources;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.items.CustomResourceItem;
import sk.westland.core.items.ItemBuilder;
import sk.westland.world.items.Materials;

@Component
public class CopperIngot extends CustomResourceItem implements Listener {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.PAPER)
                .setName("§rCopper ingot")
                .setModelId(getModelID()).build();
    }

    @Override
    public int getModelID() {
        return 3;
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        recipeService.addFurnaceRecipe("copperIngot", Materials.Resources.COPPER_INGOT, Materials.Resources.COPPER_DUST, 3, 8);
    }
}
