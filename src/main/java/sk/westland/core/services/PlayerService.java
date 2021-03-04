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
import sk.westland.core.WestLand;
import sk.westland.core.database.player.*;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.event.player.WLPlayerQuitEvent;

import java.util.*;

@Service
public class PlayerService implements Listener {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private PlayerDataRepository playerDataRepository;

    @Autowired
    private PlayerOptionsRepository playerOptionsRepository;

    private final HashMap<Player, WLPlayer> wlPlayerHashMap = new HashMap<>();
    //private static final HashMap<UUID, UserData> userDataHashMap = new HashMap<>();

    public PlayerService() {
        Bukkit.getScheduler()
                .runTaskTimerAsynchronously(WestLand.getInstance(), this::saveAllUsers, 20* 5L,20*60L); // Every 1 minutes will save the userRepository
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        loadUser(player);
        //WLPlayer wlPlayer = loadUser(player);
        //Bukkit.getPluginManager().callEvent(new WLPlayerJoinEvent(wlPlayer));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        save(player);
        Bukkit.getPluginManager().callEvent(new WLPlayerQuitEvent(getWLPlayer(player)));
        unloadPlayer(player);

        //saveAndUnloadUser(player);
    }

    public void unloadPlayer(Player player) {
        if(isLoaded(player)) {
            wlPlayerHashMap.remove(player);
            Bukkit.getLogger().info("§cUnloading player from database " + player.getName() + " [" + player.getUniqueId() +"]");
        }
    }

    public WLPlayer getWLPlayer(Player player) {
        return wlPlayerHashMap.get(player);
    }

    public boolean isLoaded(Player player) {
        return wlPlayerHashMap.containsKey(player);
    }

    public void saveAllUsers() {
        Bukkit.getOnlinePlayers().forEach((this::save));
    }

    public HashMap<Player, WLPlayer> getWlPlayerHashMap() {
        return wlPlayerHashMap;
    }

    public List<WLPlayer> getWlPlayerList() {
        List<WLPlayer> wlPlayersList = new ArrayList<>();
        if(wlPlayerHashMap.isEmpty())
            return wlPlayersList;

        wlPlayerHashMap.forEach((player, wlPlayer) -> { wlPlayersList.add(wlPlayer); });
        return wlPlayersList;
    }

    public void loadUser(Player player) {
        if(isLoaded(player))
            return;

        UUID uuid = player.getUniqueId();
        Optional<UserData> userDataOptional = userDataRepository.findByUuid(uuid.toString());

        UserData userData = userDataOptional.orElseGet(
                () -> new UserData(player.getName(), uuid.toString())
        );

        userDataRepository.save(userData);
        long id = userData.getId();

        Optional<PlayerOptions> playerOptionsOptional = playerOptionsRepository.findByPlayerId(id);
        Optional<PlayerData> playerDataOptional = playerDataRepository.findByPlayerId(id);

        PlayerOptions playerOptions = playerOptionsOptional.orElseGet(() -> new PlayerOptions(id));
        PlayerData playerData = playerDataOptional.orElseGet(() -> new PlayerData(id));

        playerOptionsRepository.save(playerOptions);
        playerDataRepository.save(playerData);

        WLPlayer wlPlayer = new WLPlayer(player, userData, playerData, playerOptions);
        wlPlayerHashMap.put(player, wlPlayer);

        Bukkit.getLogger().info("§aLoading player from database " + player.getName() + " [" + player.getUniqueId() +"]");
    }

    public void save(Player player) {
        if(!isLoaded(player))
            loadUser(player);

        WLPlayer wlPlayer = wlPlayerHashMap.get(player);
        UserData userData = wlPlayer.getUserData();
        PlayerOptions playerOptions = wlPlayer.getPlayerOptions();
        PlayerData playerData = wlPlayer.getPlayerData();

        wlPlayer.setUserData(userDataRepository.save(userData));
        wlPlayer.setPlayerData(playerDataRepository.save(playerData));
        wlPlayer.setPlayerOptions(playerOptionsRepository.save(playerOptions));
    }











    /*
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
        ChatInfo.GENERAL_INFO.sendAll("event:player" + player.getName());
        PlayerDataStorageService.Data data = playerDataStorageService.load(player);
        PlayerData user =  data.getUserData();
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
/*
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
