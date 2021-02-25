package sk.westland.core.services;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.springframework.stereotype.Service;
import sk.westland.core.event.PluginEnableEvent;

@Service
public class VaultService implements Listener {

    private Permission perms = null;
    private Economy economy = null;

    @EventHandler
    private void onEnable(PluginEnableEvent event) {
        RegisteredServiceProvider<Permission> rsp = event.getWestLand().getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();

        RegisteredServiceProvider<Economy> eResponse = event.getWestLand().getServer().getServicesManager().getRegistration(Economy.class);
        economy = eResponse.getProvider();
    }

    public Permission getPerms() {
        return perms;
    }

    public Economy getEconomy() {
        return economy;
    }
}
