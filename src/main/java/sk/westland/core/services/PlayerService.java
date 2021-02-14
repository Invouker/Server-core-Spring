package sk.westland.core.services;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLib;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.minecraft.server.v1_16_R3.DataWatcher;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.database.player.UserData;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerJoinEvent;
import sk.westland.core.event.player.WLPlayerQuitEvent;
import sk.westland.core.entity.player.WLPlayer;

import java.util.*;

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

    private void spawnPacketEntity(double x, double y, double z) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        packetContainer.getModifier().writeDefaults();

        packetContainer.getEntityTypeModifier().write(0, EntityType.ARMOR_STAND);
        Integer entityID = (int)(Math.random() * Integer.MAX_VALUE);

        packetContainer.getIntegers().write(0, entityID);
        packetContainer.getDoubles().write(0, x);
        packetContainer.getDoubles().write(1, y);
        packetContainer.getDoubles().write(2, z);

        protocolManager.broadcastServerPacket(packetContainer);
        System.out.println("packet: " + packetContainer.toString());

        PacketContainer entMetadata = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        entMetadata.getIntegers().write(0, entityID);
        List<WrappedWatchableObject> metadata = entMetadata.getWatchableCollectionModifier().read(0);


        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(metadata);
        WrappedDataWatcher.WrappedDataWatcherObject isInvisibleIndex = new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class));
        dataWatcher.setObject(isInvisibleIndex, (byte) 0x20);

        WrappedDataWatcher.WrappedDataWatcherObject nameValue = new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.get(String.class));
        WrappedDataWatcher.WrappedDataWatcherObject nameVisible = new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class));

        dataWatcher.setObject(nameValue, "§aHello, " + "fawrawr" + "!");
        dataWatcher.setObject(nameVisible, true);
        entMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());

        protocolManager.broadcastServerPacket(entMetadata);
        System.out.println("packet: " + entMetadata.toString());
    }

    private void onPluginEnable(PluginEnableEvent event) {

/*
        spawnPacketEntity(Bukkit.getPlayer("XpresS").getLocation().getX(),
                Bukkit.getPlayer("XpresS").getLocation().getY(),
                Bukkit.getPlayer("XpresS").getLocation().getZ());


        for(Entity entity : Bukkit.getWorld("worldspawn").getEntities()) {
            Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

            Objective objective = board.getObjective("dummys");
            if (objective == null)
                objective = board.registerNewObjective("dummys", "test");

            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            Score score = objective.getScore("XpresS");
            score.setScore(100);



                    objective.setDisplayName(ChatColor.RED + "Health" + ChatColor.GRAY + ": ");


            onlinePlayer.setScoreboard(board);

        }*/
    }


}
