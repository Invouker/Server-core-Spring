package sk.westland.core.services;


import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.springframework.stereotype.Service;
import sk.westland.core.event.PluginEnableEvent;

@Service
public class APIServices implements Listener {

    private LuckPerms luckPerms;

    @EventHandler(ignoreCancelled = true)
    public void onPluginEnable(PluginEnableEvent event) {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }
}
