package sk.westland.core.services;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import sk.westland.core.WestLand;

public class SlimefunService implements BeanWire {

    private ItemGroup itemGroup;

    public WestLand getInstance() {
        return WestLand.getInstance();
    }

    public SlimefunAddon getSlimefunAddon() {
        return WestLand.getSlimefunAddonInstance();
    }

    public void registerItem() {

    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public void setCategory(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }
}
