package sk.westland.core.services;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Objects.Category;
import sk.westland.core.WestLand;

public class SlimefunService implements BeanWire {

    private Category category;

    public WestLand getInstance() {
        return WestLand.getInstance();
    }

    public SlimefunAddon getSlimefunAddon() {
        return WestLand.getSlimefunAddonInstance();
    }

    public void registerItem() {

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
