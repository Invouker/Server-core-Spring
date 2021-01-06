package sk.wildwest.core;

import org.bukkit.plugin.java.JavaPlugin;

public class WildWest extends JavaPlugin {

    public static WildWest wildWest;

    @Override
    public void onEnable() {
        super.onEnable();

        wildWest = this;

    }

    @Override
    public void onDisable() {
        super.onDisable();

    }

    public static WildWest getInstance() {
        return wildWest;
    }
}
