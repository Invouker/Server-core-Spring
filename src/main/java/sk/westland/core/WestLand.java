package sk.westland.core;

import org.bukkit.plugin.java.JavaPlugin;
import sk.westland.core.commands.AutoCommandRegister;
import sk.westland.core.events.AutoEventRegister;

public class WestLand extends JavaPlugin {

    public static WestLand westLand;

    @Override
    public void onEnable() {
        super.onEnable();

        westLand = this;

        new AutoEventRegister(); // Creating register for events ( should it work )

        new AutoCommandRegister(); //Auto command register
    }

    @Override
    public void onDisable() {
        super.onDisable();

    }

    public static WestLand getInstance() {
        return westLand;
    }
}
