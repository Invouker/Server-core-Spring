package sk.westland.world.commands;

import co.aikar.commands.annotation.Dependency;
import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.PlayerData;
import sk.westland.core.database.player.PlayerDataRepostiroy;
import sk.westland.core.database.player.UserData;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.InventoryChestType;
import sk.westland.core.hibernate.AbstractController;
import sk.westland.core.hibernate.AsyncCallBackExceptionHandler;
import sk.westland.core.hibernate.RunicException;
import sk.westland.core.hibernate.repositories.UserRepository;
import sk.westland.core.inventory.rc.InventoryHandler;
import sk.westland.core.services.*;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.Utils;
import sk.westland.world.inventories.ChangeJoinMessageItemMenu;
import sk.westland.world.inventories.ChangeQuitMessageItemMenu;
import sk.westland.world.inventories.entities.HorseUpgradeInventory;
import sk.westland.world.items.Materials;
import sk.westland.world.minigame.PartyGame;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
@CommandLine.Command(name = "test")
@HasPermission("commands.test")
public class TestCommand implements Runnable {

    @Autowired
    private Context context;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MessageService messageService;

    @CommandLine.Parameters(index = "0", defaultValue = "0")
    private String join;

    @Override
    public void run() {
        if(join.equalsIgnoreCase("j")) {
            ChangeJoinMessageItemMenu testInventory = new ChangeJoinMessageItemMenu(playerService.getWLPlayer(context.getPlayer()), messageService);
            testInventory.open(context.getPlayer());
        }else if(join.equalsIgnoreCase("q")){
            ChangeQuitMessageItemMenu testInventory = new ChangeQuitMessageItemMenu(playerService.getWLPlayer(context.getPlayer()), messageService);
            testInventory.open(context.getPlayer());
        }
    }

    @Component
    @CommandLine.Command(name = "2")
    @HasPermission("commands.test2")
    class Test2 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Autowired
        private MessageService messageService;


        @Override
        public void run() {
            context.getPlayer().getInventory().addItem(Materials.Items.BLOCK_PLACER.getItem());
            context.getSender().sendMessage("Pridal sa ti item do inventára!");

            PlayerData playerData = playerService.getWLPlayer(context.getPlayer()).getPlayerData();

            List<String> recipes = playerData.getCraftingRecipe();
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
    class Test5 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Autowired
        private HorseService horseService;

        @Autowired
        private MoneyService moneyService;

        @Override
        public void run() {
            HorseUpgradeInventory horseUpgradeInventory = new HorseUpgradeInventory(horseService, moneyService, context.getPlayer());
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

        @Override
        public void run() {
            Location location = context.getPlayer().getLocation();
            new PartyGame(location);
            ChatInfo.ERROR.send(context.getPlayer(), "Spawnol si zombika, pičo");
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

        @Override
        public void run() {
           /* EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            PlayerDataRepostiroy playerDataRepository = new PlayerDataRepostiroy(em);
            Optional<PlayerData> playerData =  playerDataRepository.findByPlayerId(3);
            if(playerData.isPresent()) {
                ChatInfo.SUCCESS.sendAll("Test shards: " + playerData.get().getShards());
            }else {
                ChatInfo.ERROR.sendAll("Doesnt found");
            }

            tx.commit();
            em.close();
            emf.close();*/

            UserRepository userRepository = new UserRepository();
            try {
                System.out.println("userRepository.findById(3).get().getUserName(): " + userRepository.findByUserName("XpresS").get().getId());
            } catch (RunicException e) {
                e.printStackTrace();
            }

            AbstractController<PlayerData> abstractController = new AbstractController<>(PlayerData.class);
            abstractController.findAll((a)-> {
                for(int i = 0; i < 10; i++) {
                    System.out.println("Shards: " + a.get(i).getShards());
                }
                 }, (c)-> {
                System.out.println(c.getMessage());
            });
        }
    }

    @Component
    @CommandLine.Command(name = "10")
    @HasPermission("commands.test10")
    class Test10 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {

        }
    }
}
