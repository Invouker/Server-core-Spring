package sk.westland.core.services;

import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.springframework.stereotype.Service;
import sk.westland.core.event.PluginEnableEvent;


public class APIServices implements Listener {

    private TitleManagerAPI titleManagerAPI;

    public void onPluginEnable(PluginEnableEvent event) {
        try {
            titleManagerAPI = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public TitleManagerAPI getTitleManagerAPI() {
        return titleManagerAPI;
    }
}
