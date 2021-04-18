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
public class RawCarbonFibre extends CustomResourceItem implements Listener {

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        super.onPluginEnable(event);

        recipeService.addFurnaceRecipe("compressedCarbonFurnace", Materials.Resources.COMPRESSED_CARBON.getItem(), getItem(), 6, 16);

    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.PAPER)
                .setName("§rCarbon Fibre")
                .setModelId(getModelID())
                .build();
    }

    @Override
    public int getModelID() {
        return 5;
    }

}
