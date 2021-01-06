package sk.wildwest.core;

import org.bukkit.plugin.java.JavaPlugin;
import sk.wildwest.core.events.AutoEventRegister;

public class WildWest extends JavaPlugin {

    public static WildWest wildWest;

    @Override
    public void onEnable() {
        super.onEnable();

        wildWest = this;

        new AutoEventRegister(); // Creating register for events ( should it work )
    }

    @Override
    public void onDisable() {
        super.onDisable();

    }

    public static WildWest getInstance() {
        return wildWest;
    }
}
