package sk.westland.core.services;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.springframework.stereotype.Service;
import sk.westland.core.event.PluginEnableEvent;

@Service
public class PermissionService implements Listener {

    private Permission perms = null;

    @EventHandler
    public void onEnable(PluginEnableEvent event) {
        RegisteredServiceProvider<Permission> rsp = event.getWestLand().getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
    }

    public Permission getPerms() {
        return perms;
    }
}
