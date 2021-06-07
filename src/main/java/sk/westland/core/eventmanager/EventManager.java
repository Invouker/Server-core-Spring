package sk.westland.core.eventmanager;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import sk.westland.core.App;
import sk.westland.core.WestLand;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.EEventType;
import sk.westland.core.enums.EPlayerCauseEventLeave;
import sk.westland.core.enums.EPlayerOptions;
import sk.westland.core.services.EventManagerService;
import sk.westland.core.utils.ChatInfo;

import java.util.*;

public abstract class EventManager implements Runnable, Listener {

    private final EEventType eEventType;

    private ProtectedRegion protectedRegion;
    protected EEventState eEventState = EEventState.NONE;
    private final Location playerLocationStart;
    protected World world;

    private List<ItemStack> rewardList = new ArrayList<>();
    private Location minLocation;
    private Location maxLocation;

    private final Set<WLPlayer> players;
    private final Set<WLPlayer> playerRewards;

    private final HashMap<Player, PlayerEventData> playerEventDataMap = new HashMap<>();
    private final HashMap<Integer, ItemStack> playerInventory = new HashMap<>();

    private int WARMUP_TIME = 20;
    private boolean canStart;
    private boolean isRunning;

    protected final int MIN_PLAYER_TO_START = 3;

    protected BukkitTask task;

    public EventManager(EEventType eEventType, Location playerLocationStart, World world, int minLocX, int minLocY, int minLocZ, int maxLocX, int maxLocY, int maxLocZ) {
        this.eEventType = eEventType;
        this.playerLocationStart = playerLocationStart;
        this.minLocation = new Location(world, Math.min(minLocX, maxLocX), Math.min(minLocY, maxLocY), Math.min(minLocZ, maxLocZ));
        this.minLocation = new Location(world, Math.max(minLocX, maxLocX), Math.max(minLocY, maxLocY), Math.max(minLocZ, maxLocZ));
        this.world = playerLocationStart.getWorld();

        players = new HashSet<>();
        playerRewards = new HashSet<>();

        canStart = false;
        isRunning = false;

        { // REGION
            BlockVector3 min = BlockVector3.at(Math.min(minLocX, maxLocX), Math.min(minLocY, maxLocY), Math.min(minLocZ, maxLocZ));
            BlockVector3 max = BlockVector3.at(Math.max(minLocX, maxLocX), Math.max(minLocY, maxLocY), Math.max(minLocZ, maxLocZ));
            ProtectedRegion region = new ProtectedCuboidRegion(eEventType.getRegionName(), min, max);
            region.setFlag(Flags.BUILD, StateFlag.State.DENY);
            region.setFlag(Flags.BLOCK_PLACE, StateFlag.State.DENY);
            Set<String> allowedComands = new HashSet<>();
            allowedComands.add("event");
            region.setFlag(Flags.ALLOWED_CMDS, allowedComands);
        }

        task = Bukkit.getScheduler().runTaskTimer(WestLand.getInstance(), this, 20, 20);
    }

    public void givePlayerRewards() {
        playerRewards.forEach(wlPlayer ->
            rewardList.forEach((wlPlayer::addItemToInventory))
        );

        playerRewards.clear();
    }

    public void run() {
        { // WARMUP LOGIC
            if (eEventState == EEventState.WARMUP && getWarmUpTime() == 0 && isCanStart()) {
                setCanStart(false);
                setRunning(true);
                setWarmUpTime(-1);



                changeState(EEventState.PROGRESS);
            }

            if(eEventState == EEventState.WARMUP && isCanStart()) {
                setWarmUpTime(getWarmUpTime()-1);

                if(getWarmUpTime() <= 5 && getWarmUpTime() > 3) {
                    broadcastToEventPlayer("Event začne o " + getWarmUpTime());
                }

                if(getWarmUpTime() <= 3) {
                    titleForEventPlayer("Začne o " + getWarmUpTime());
                }

            }

            if(!isRunning() && eEventState == EEventState.LOBBY && getPlayers().size() >= MIN_PLAYER_TO_START) {
                setCanStart(true);
            }
        }
    }

    public void broadcastToEventPlayer(String message) {
        players.forEach((player) -> player.sendMessage("§e[E] §r" + message));
    }

    public void titleForEventPlayer(String subTitle) {
        players.forEach((player) -> player.sendTitle("Event", subTitle));
    }

    public void titleForEventPlayer(String title, String subTitle) {
        players.forEach((player) -> player.sendTitle(ChatColor.of("#ffdd99") + title, ChatColor.of("#ccff33") + subTitle));
    }

    public void addPlayerToEvent(WLPlayer wlPlayer) {
        players.add(wlPlayer);
        broadcastToEventPlayer("Hráč " + wlPlayer.getPlayer().getName() + " sa pripojil na event!");
        Player player = wlPlayer.getPlayer();
        playerEventDataMap.put(player, new PlayerEventData(player.getInventory().getContents(), player.getLocation(), player.getGameMode()));

        wlPlayer.teleport(playerLocationStart);
        player.setFlying(false);
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        wlPlayer.sendTitle(ChatColor.of("#ffdd99") + "Event", ChatColor.of("#ccff33") + "Pripojil si sa na event");
    }

    public void leavePlayerFromEvent(WLPlayer wlPlayer, EPlayerCauseEventLeave quitCause) {
        if(!players.contains(wlPlayer))
            return;

        wlPlayer.setGameMode(GameMode.SURVIVAL);

        Player player = wlPlayer.getPlayer();
        if(playerEventDataMap.containsKey(player)) {
            PlayerEventData playerEventData = playerEventDataMap.get(player);
            player.getInventory().setContents(playerEventData.getItemStacksContents());
            player.teleport(playerEventData.getLastKnownLocation());
            player.setGameMode(playerEventData.getLastKnownGamemode());
            playerEventDataMap.remove(player);
        }

        broadcastToEventPlayer("Hráč " + wlPlayer.getName() + " opustil event! (" + quitCause.getCause() + ")");
    }



    public abstract void changeEventState(EEventState eEventState);

    public void onEntityDamage(EntityDamageEvent event) {

    }

    public void onPlayerDeath(PlayerDeathEvent event) {
        if(event.getEntity().getType() != EntityType.PLAYER)
            return;

        if(event.getEntity().getLastDamageCause() != null &&
                event.getEntity().getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.VOID)
            return;

        getPlayers().stream()
                .filter(wlPlayer -> wlPlayer.getPlayer().getName().equals(event.getEntity().getName()))
                .forEach((wlPlayer -> {
                    leavePlayerFromEvent(wlPlayer, EPlayerCauseEventLeave.PLAYER_DIE);
                }));
    }

    public void changeState(EEventState eEventState) {
        if(this.eEventState == eEventState)
            return;

        this.eEventState = eEventState;

        changeEventState(eEventState);

        switch (eEventState) {
            case LOBBY: {
                ChatInfo.GENERAL_INFO.sendAll(EPlayerOptions.SHOW_EVENT_ANNOUNCE, "Event " + eEventType.getEventName() + " odštartoval, pre pripojenie skús /event join");

                // broadcast to all
                break;
            }
            case WARMUP: {
                broadcastToEventPlayer("Event sa o chviľku začne");
                // canConnect = false
                break;
            }
            case PROGRESS: {
                // taskHandler
                //
                break;
            }
            case END_TIME: {
                broadcastToEventPlayer("Máte 30 sekúnd na opustenie eventu, inak vás to automatický teleportuje na predchádzajúcu pozíciu!");
                // time to quit a event
                Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), () -> {changeState(EEventState.END);}, 20*30L);
                break;
            }
            case END: {
                players.forEach((player) -> {
                    if(!playerEventDataMap.containsKey(player))
                        return;

                    PlayerEventData playerEventData = playerEventDataMap.get(player);
                    player.getInventory().setContents(playerEventData.getItemStacksContents());
                    player.teleport(playerEventData.getLastKnownLocation());
                });

                givePlayerRewards();
                players.clear();

                App.getService(EventManagerService.class).forceServiceEndEvent();
                // remove players from arena
                // reseting area to default

                if(protectedRegion != null) {
                    RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    World world = Bukkit.getWorld("eventworld");
                    if(world == null)
                        return;

                    RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(world));

                    if(regionManager == null)
                        return;

                    regionManager.removeRegion(eEventType.getRegionName());
                }

                if(task != null)
                    task.cancel();

                task = null;
                break;
            }
            default:
                break;
        }
    }

    public boolean canPlayerConnect() {
        switch (eEventState) {
            case PROGRESS:
            case END:
            case END_TIME:
            case WARMUP:
                return false;
            default:
            case LOBBY:
                return true;
        }
    }

    public void addReward(ItemStack... rewards) {
        rewardList.addAll(Arrays.asList(rewards));
    }

    public List<ItemStack> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List<ItemStack> rewardList) {
        this.rewardList = rewardList;
    }

    static class PlayerEventData {
        private final ItemStack[] itemStacksContents;
        private final Location lastKnownLocation;
        private final GameMode lastKnownGamemode;

        public PlayerEventData(ItemStack[] itemStacksContents, Location lastKnownLocation, GameMode lastKnownGamemode) {
            this.itemStacksContents = itemStacksContents;
            this.lastKnownLocation = lastKnownLocation;
            this.lastKnownGamemode = lastKnownGamemode;
        }

        public Location getLastKnownLocation() {
            return lastKnownLocation;
        }

        public ItemStack[] getItemStacksContents() {
            return itemStacksContents;
        }

        public GameMode getLastKnownGamemode() {
            return lastKnownGamemode;
        }
    }

    public void addToPlayerRewards(WLPlayer... wlPlayer) {
        playerRewards.addAll(Arrays.asList(wlPlayer));
    }

    public EEventState getEventState() {
        return eEventState;
    }

    public boolean isCanStart() {
        return canStart;
    }

    public void setCanStart(boolean canStart) {
        System.out.println("canStart to: "  + canStart);
        this.canStart = canStart;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getWarmUpTime() {
        return WARMUP_TIME;
    }

    public void setWarmUpTime(int i) {
        WARMUP_TIME = i;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Set<WLPlayer> getPlayers() {
        return players;
    }

    public List<Location> setBlocks(Material mat, Location loc1, Location loc2) {
        List<Location> locations = new ArrayList<>();
        int topBlockX = (Math.max(loc1.getBlockX(), loc2.getBlockX()));
        int bottomBlockX = (Math.min(loc1.getBlockX(), loc2.getBlockX()));

        int topBlockY = (Math.max(loc1.getBlockY(), loc2.getBlockY()));
        int bottomBlockY = (Math.min(loc1.getBlockY(), loc2.getBlockY()));

        int topBlockZ = (Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
        int bottomBlockZ = (Math.min(loc1.getBlockZ(), loc2.getBlockZ()));

        for(int x = topBlockX; x >= bottomBlockX; x--) {
            for(int y = topBlockY; y >= bottomBlockY; y--) {
                for(int z = topBlockZ; z >= bottomBlockZ; z--) {
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(mat);
                    locations.add(block.getLocation());
                }
            }
        }
        return locations;
    }
}
