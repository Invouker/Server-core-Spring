package sk.westland.core.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WWPlayer implements Listener {

    private static Set<Player> players = new HashSet<>();
    private static HashMap<Player, WWPlayer> wwPlayers = new HashMap<>();
    private WWPlayer wwPlayer;
    private Player player;

    @EventHandler
    private void onPlayerConnect(PlayerJoinEvent event) {
        wwPlayer = new WWPlayer(event.getPlayer());
    }

    @EventHandler
    private void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(players.contains(player))
            players.remove(player);

        if(wwPlayers.containsKey(player))
            wwPlayers.remove(player);
    }

    public WWPlayer(Player player) {
        players.add(player);
        wwPlayers.put(player, this);
    }

    public Player getPlayer() {
        return player;
    }

    public int getConnectedPlayers() {
        return players.size();
    }

    public Set<Player> getJoinedPlayers() {
        return players;
    }

    public void addItemToInventory(ItemStack... itemStack) {
        player.getInventory().addItem(itemStack);
    }

    public static WWPlayer getWWPlayer(Player player) {
        return wwPlayers.get(player);
    }

    public static Player getPlayer(WWPlayer wwPlayer) {
        for(Map.Entry<Player, WWPlayer> entry : wwPlayers.entrySet()) {
            if(entry.getValue().equals(wwPlayer))
                return wwPlayer.getPlayer();
        }
        return null;
    }
}
