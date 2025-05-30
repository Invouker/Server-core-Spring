package sk.westland.world.commands;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.App;
import sk.westland.core.WestLand;
import sk.westland.core.enums.InventoryChestType;
import sk.westland.core.inventory.rc.InventoryHandler;
import sk.westland.core.services.*;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.Utils;
import sk.westland.world.inventories.ChangeJoinMessageItemMenu;
import sk.westland.world.inventories.ChangeQuitMessageItemMenu;
import sk.westland.world.inventories.entities.HorseUpgradeInventory;
import sk.westland.world.items.Materials;

@Component
@CommandLine.Command(name = "test")
@HasPermission("commands.test")
public class TestCommand {

    @Component
    @CommandLine.Command(name = "2")
    @HasPermission("commands.test2")
    static class Test2 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            ChangeJoinMessageItemMenu testInventory = new ChangeJoinMessageItemMenu(playerService.getWLPlayer(context.getPlayer()));
            testInventory.open(context.getPlayer());
        }
    }

    @Component
    @CommandLine.Command(name = "3")
    @HasPermission("commands.test3")
    static class Test3 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;
        @Override
        public void run() {
            ChangeQuitMessageItemMenu testInventory = new ChangeQuitMessageItemMenu(playerService.getWLPlayer(context.getPlayer()));
            testInventory.open(context.getPlayer());
        }
    }

    @Component
    @CommandLine.Command(name = "4")
    @HasPermission("commands.test4")
    class Test4 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            context.getPlayer().getInventory().addItem(Materials.Items.SADDLE_ITEM.getItem());
        }
    }

    @Component
    @CommandLine.Command(name = "5")
    @HasPermission("commands.test5")
    static class Test5 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Autowired
        private HorseService horseService;

        @Autowired
        private MoneyService moneyService;

        @Autowired
        private RunnableService runnableService;

        @Override
        public void run() {
            HorseUpgradeInventory horseUpgradeInventory = new HorseUpgradeInventory(horseService, moneyService, runnableService, context.getPlayer());
            horseUpgradeInventory.open(context.getPlayer());
        }
    }

    @Component
    @CommandLine.Command(name = "6")
    @HasPermission("commands.test6")
    class Test6 implements Runnable {

        @Autowired
        private InventoryService inventoryService;

        @Autowired
        private Context context;

        @Override
        public void run() {
            InventoryHandler inventoryHandler = new InventoryHandler("IDK", Bukkit.getPlayer("XpresS"), InventoryChestType.CHEST_9,
                    (interactEvent) -> {
                        System.out.println("Klikanie s : " + interactEvent.getWhoClicked().getName());
                        interactEvent.setResult(Event.Result.ALLOW);
                        interactEvent.setCancelled(true);
                    },
                    (inventoryDragEvent) -> {


                    },
                    (inventoryClickEvent) -> {

                        System.out.println("Klikanie s : " + inventoryClickEvent.getWhoClicked().getName());
                        inventoryClickEvent.setResult(Event.Result.ALLOW);
                        inventoryClickEvent.setCancelled(true);
                    },
                    (inventoryOpenEvent) -> {
                inventoryOpenEvent.getInventory().addItem(new ItemStack(Material.ACACIA_BUTTON));},
                    inventoryCloseEvent -> { });

            inventoryHandler.setItems(7, new ItemStack(Material.ACACIA_WALL_SIGN));

            inventoryHandler.updateInventory();
            inventoryService.registerInventory(inventoryHandler);
            context.getPlayer().openInventory(inventoryHandler.getInventory());
        }
    }

    @Component
    @CommandLine.Command(name = "7")
    @HasPermission("commands.test7")
    class Test7 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Autowired
        private MoneyService moneyService;

        @Autowired
        private VotePartyService votePartyService;

        @Override
        public void run() {
            votePartyService.spawnVoteParty();
            ChatInfo.SUCCESS.send(context.getPlayer(), "Spawnol si VoteParty!");
        }
    }

    @Component
    @CommandLine.Command(name = "8")
    @HasPermission("commands.test8")
    static
    class Test8 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        public void createHelix(Location location, Color color) {

            System.out.println("X: " + location.getX());
            System.out.println("Y: " + location.getY());
            System.out.println("Z: " + location.getZ());

            double radius = 0.5;
            for(double y = -0.5; y < 1.5; y+=0.005) {
                //radius -= 0.0000003;
                double x = radius * Math.cos(y);
                double z = radius * Math.sin(y);

                double finalX = location.getX() + x;
                double finalY = location.getY() + y;
                double finalZ = location.getZ() + z;



                Particle.DustOptions dust = new Particle.DustOptions(color, 1);
                location.getWorld().spawnParticle(Particle.REDSTONE, finalX, finalY, finalZ, 0, 0,0,0, dust);
                    //player.spawnParticle(Particle.REDSTONE, loc.getX() + x, loc.getY() + finalY, loc.getZ() + z, 10);

                //PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(ParticleParam.a.BARRIER,true, (float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
                //for(Player online : Bukkit.getOnlinePlayers()) {
                //  ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
                //}
            }
        }
        @Override
        public void run() {
            Player player = context.getPlayer();
            Bukkit.getScheduler().runTaskAsynchronously(WestLand.getInstance(), () -> {
                Location location = player.getLocation();
                double x = location.getBlockX();
                double y = location.getBlockY();
                double z = location.getBlockZ();
                World world = location.getWorld();

                createHelix(new Location(world, x-.5, y, z+1), Color.fromRGB(255 , 0 , 0 )); // R
                createHelix(new Location(world, x+.5, y, z+1), Color.fromRGB(0 , 0 , 255 )); // B
                createHelix(new Location(world, x-.5, y, z), Color.fromRGB(255 , 255 , 255 )); // W
                createHelix(new Location(world, x+.5, y, z), Color.fromRGB(0 , 255 , 0 )); // G
            });

            Utils.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH);
        }
    }

    @Component
    @CommandLine.Command(name = "9")
    @HasPermission("commands.test9")
    class Test9 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            ChatInfo.SUCCESS.sendAll("Shards: " +
                    App.
                            getService(
                                    PlayerService.class)
                            .getWLPlayer(
                                    context.getPlayer())
                            .getShards());
        }
    }
}
