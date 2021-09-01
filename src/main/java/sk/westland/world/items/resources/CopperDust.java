package sk.westland.world.items.resources;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.items.CustomResourceItem;
import sk.westland.core.items.ItemBuilder;

@Component
public class CopperDust extends CustomResourceItem implements Listener {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.PAPER)
                .setName("§rCopper dust")
                .setModelId(getModelID()).build();
    }

    @Override
    public int getModelID() {
        return 2;
    }

}
