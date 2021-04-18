package sk.westland.world.items.resources;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.items.CustomResourceItem;
import sk.westland.core.items.ItemBuilder;

public class CompressedCarbon extends CustomResourceItem {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.PAPER)
                .setName("§rCompressed Carbon")
                .setModelId(getModelID())
                .build();
    }

    @Override
    public int getModelID() {
        return 6;
    }
}
