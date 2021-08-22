package sk.xpress.worldgen;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public WestChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new WestChunkGenerator();
    }
}
