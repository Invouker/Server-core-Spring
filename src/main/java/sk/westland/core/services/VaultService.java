package sk.westland.core.services;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import sk.westland.core.WestLand;
import sk.westland.core.event.PluginEnableEvent;

public class VaultService implements Listener, BeanWire {

    private Permission perms = null;
    private Economy economy = null;
    private Chat chat = null;

    @EventHandler
    private void onEnable(PluginEnableEvent event) {
        RegisteredServiceProvider<Permission> rsp = WestLand.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();

        RegisteredServiceProvider<Economy> eResponse = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if(eResponse != null)
            economy = eResponse.getProvider();

        RegisteredServiceProvider<Chat> chatResponse = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        chat = chatResponse.getProvider();
    }

    public Permission getPerms() {
        return perms;
    }

    public Economy getEconomy() {
        if(economy == null)
            throw new NullPointerException("None of economy manager registred yet.");
        return economy;
    }

    public Chat getChat() {
        return chat;
    }
}
