package sk.westland.world.items.resources;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.items.CustomResourceItem;
import sk.westland.core.items.ItemBuilder;

public class IronRod extends CustomResourceItem {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.STICK).build();
    }

    @Override
    public int getModelID() {
        return 5;
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) { }
}
