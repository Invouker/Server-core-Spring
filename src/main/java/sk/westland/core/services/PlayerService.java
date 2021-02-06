package sk.westland.core.services;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.database.player.UserData;
import sk.westland.core.event.player.WLPlayerJoinEvent;
import sk.westland.core.event.player.WLPlayerQuitEvent;
import sk.westland.core.entity.player.WLPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class PlayerService implements Listener {

    @Autowired
    private PlayerDataStorageService playerDataStorageService;

    private static HashMap<Player, WLPlayer> wlPlayers = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        WLPlayer wlPlayer = loadUser(player);
        Bukkit.getPluginManager().callEvent(new WLPlayerJoinEvent(wlPlayer));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Bukkit.getPluginManager().callEvent(new WLPlayerQuitEvent(getWLPlayer(player)));
        saveAndUnloadUser(player);
    }

    public WLPlayer getWLPlayer(Player player) {
        return wlPlayers.get(player);
    }

    public HashMap<Player, WLPlayer> getWLPlayers() {
        return wlPlayers;
    }

    public Player getPlayer(WLPlayer WLPlayer) {
        for(Map.Entry<Player, WLPlayer> entry : wlPlayers.entrySet()) {
            if(entry.getValue().equals(WLPlayer))
                return WLPlayer.getPlayer();
        }
        return null;
    }

    public Set<Player> getJoinedPlayers() {
        return wlPlayers.keySet();
    }

    public WLPlayer loadUser(Player player) {
        PlayerDataStorageService.Data data = playerDataStorageService.load(player);
        UserData user =  data.getUserData();
        WLPlayer wlPlayer = new WLPlayer(player, data);
        wlPlayers.put(player, wlPlayer);
        Bukkit.getConsoleSender().sendMessage("§aLoading player with his name: " + user.getName() + ", UUID[" + user.getUuid()+"]");
        return wlPlayer;
    }

    public void saveAndUnloadUser(Player player) {
        Bukkit.getConsoleSender().sendMessage("§cUnloading player with his name: "
                + getWLPlayer(player).getUserData().getName() + ", UUID[" + getWLPlayer(player).getUserData().getUuid()+"]");
        playerDataStorageService.save(player);
        playerDataStorageService.unloadUser(player);

        if(wlPlayers.containsKey(player))
            wlPlayers.remove(player);
    }
}
