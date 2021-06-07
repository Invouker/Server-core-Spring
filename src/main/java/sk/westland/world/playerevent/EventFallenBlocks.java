package sk.westland.world.playerevent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.App;
import sk.westland.core.WestLand;
import sk.westland.core.enums.EEventType;
import sk.westland.core.enums.EPlayerOptions;
import sk.westland.core.eventmanager.EEventState;
import sk.westland.core.eventmanager.EventManager;
import sk.westland.core.services.UtilsService;
import sk.westland.core.utils.ChatInfo;

import java.util.List;

public class EventFallenBlocks extends EventManager implements Listener {

    private final Location loc1 = new Location(world, -33,89,125);
    private final Location loc2 = new Location(world, -29,91,125);

    private final List<Location> floorLocations;

    private final String worldName;
    private final UtilsService utilsService = App.getService(UtilsService.class);

    public EventFallenBlocks(String worldName) {
        super(EEventType.FALLEN_BLOCKS,
                new Location(Bukkit.getWorld(worldName),-30.5,89,119.5),
                Bukkit.getWorld(worldName),
                -60, 102, 112,
                0,53,171);

        Bukkit.getPluginManager().registerEvents(this, WestLand.getInstance());

        this.worldName = worldName;
        eEventState = EEventState.LOBBY;
        setCanStart(false);

        Location floorLocation1 = new Location(world, -46, 88, 127);
        Location floorLocation2 = new Location(world, -16, 88, 157);
        floorLocations = setBlocks(Material.WARPED_HYPHAE, floorLocation1, floorLocation2);
        setBlocks(Material.BARRIER, loc1, loc2);

        addReward(new ItemStack(Material.GUNPOWDER, 61));
    }


    @Override
    public void changeEventState(EEventState eEventState) {
        if(eEventState == EEventState.PROGRESS) {
            Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), () -> {

                setBlocks(Material.BARRIER, loc1, loc2);
                    broadcastToEventPlayer("Barrier");
            }, 20*10L);

        }
    }

    @Override
    public void run() {
        super.run();
        { // GAME LOGIC

            if(this.isRunning() && eEventState == EEventState.PROGRESS && this.getPlayers().size() == 1) {
                getPlayers().forEach(this::addToPlayerRewards);
            }

            if(this.isRunning() && eEventState == EEventState.PROGRESS && this.getPlayers().size() <= 0) {
                ChatInfo.GENERAL_INFO.sendAll(EPlayerOptions.SHOW_EVENT_ANNOUNCE, "Event " + eEventState.name() + " práve skončil!");
                changeState(EEventState.END);
                return;
            }

            if(this.isRunning() && eEventState == EEventState.PROGRESS && this.getPlayers().size() > 0) {
            //-46 88 127
                //-16 88 157

                if(floorLocations.size() == 0) {
                    changeState(EEventState.END_TIME);
                    return;
                }

                for(int i = 0; i < 5; i++) {
                    if(floorLocations.size() <= 0)
                        break;

                    int pos = utilsService.getRandomInt(floorLocations.size());
                    Location location = floorLocations.get(pos);
                    BlockData blockData = location.getWorld().getBlockAt(location).getBlockData();
                    location.getWorld().getBlockAt(location).setType(Material.AIR);
                    location.getWorld().spawnFallingBlock(location.add(.5, 0, .5), blockData);

                    floorLocations.remove(pos);
                }

            }
        }

        if (eEventState == EEventState.WARMUP && getWarmUpTime() == 0 && isCanStart()) {
            setBlocks(Material.AIR, loc1, loc2);
        }
    }

    @Override
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        super.onPlayerDeath(event);


        event.setCancelled(true);

    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {

        if(event.getCause() != EntityDamageEvent.DamageCause.VOID)
            return;

        super.onEntityDamage(event);
    }
}
