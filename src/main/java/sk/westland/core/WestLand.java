package sk.westland.core;

import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.alangomes.springspigot.SpringSpigotInitializer;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import sk.westland.core.database.player.RankDataRepository;
import sk.westland.core.database.player.UserDataRepository;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.ServerDisableEvent;
import sk.westland.core.services.BlockService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.ScoreboardService;
import sk.westland.core.services.ServerDataService;
import sk.westland.core.utils.PlaceHolder;
import sk.westland.core.utils.ResFlag;
import sk.westland.discord.DiscordHandler;
import sk.westland.world.items.Materials;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

@EnableAutoConfiguration
public class WestLand extends JavaPlugin implements SlimefunAddon {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ScoreboardService scoreboardService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private ServerDataService serverDataService;

    @Autowired
    private RankDataRepository rankDataRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    private static boolean isLocalhost = false;

    public static final String CUSTOM_BLOCK_NBT = "BLOCK_ID";

    public static WestLand westLand;
    private ConfigurableApplicationContext context;
    private ClassLoader defaultClassLoader;
    private ResourceLoader loader;

    private PlaceHolder placeHolder;
    private static DiscordHandler discordHandler;
    private static ProtocolManager protocolManager;


    @PersistenceContext
    private EntityManager em;

    @Override
    public void onEnable() {
        super.onEnable();

        westLand = this;

        this.saveDefaultConfig();
        isLocalhost = this.getConfig().getBoolean("localhost", false);

        Bukkit.getConsoleSender().sendMessage("§a");
        Bukkit.getConsoleSender().sendMessage("§aLoading Spring framework...");

        defaultClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

        ResourceLoader loader = new DefaultResourceLoader(getClassLoader());
        SpringApplication application = new SpringApplication(loader, Application.class) {
            @Override
            public void setListeners(Collection<? extends ApplicationListener<?>> listeners) {
               super.setListeners(listeners
                       .stream()
                        .filter((listener) -> !(listener instanceof org.springframework.boot.context.logging.LoggingApplicationListener))
                        .collect(Collectors.toList()));
            }
        };

        application.addInitializers(new SpringSpigotInitializer(this));
        Properties properties = new Properties();
        try {
            properties.load(getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setupDatabase(properties);
        application.setDefaultProperties(properties);
        this.context = application.run();

        protocolManager = ProtocolLibrary.getProtocolManager();
        discordHandler = new DiscordHandler(rankDataRepository, userDataRepository);

        for (ResFlag resFlag : ResFlag.values()) {
            FlagPermissions.addFlag(resFlag.getFlagName());
        }

        try {
            placeHolder = new PlaceHolder(this, playerService, scoreboardService, serverDataService);
            placeHolder.register();
        }  catch (Exception ex) {
            System.out.println("While hooking into PlaceholderAPI: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }

        Bukkit.getConsoleSender().sendMessage("§aLoaded " + Materials.Items.values().length + " custom items!");
        Bukkit.getConsoleSender().sendMessage("§aLoaded " + Materials.Resources.values().length + " resource items!");
        Bukkit.getConsoleSender().sendMessage("§aLoaded " + blockService.getLOADED_BLOCKS() + " blocks!");

        Bukkit.getConsoleSender().sendMessage("§a");
        Bukkit.getConsoleSender().sendMessage("§aSpring framework successfully started!");
        Bukkit.getConsoleSender().sendMessage("§a");

        if(Bukkit.getOnlinePlayers().size() > 0) {
            Bukkit.getOnlinePlayers().forEach((player -> playerService.loadUser(player)));
        }

        Bukkit.getPluginManager().callEvent(new PluginEnableEvent(this));

        Bukkit.getConsoleSender().sendMessage("§a");


    }

    @Override
    public void onDisable() {
        //super.onDisable();
        Bukkit.getPluginManager().callEvent(new ServerDisableEvent(westLand));

        if(playerService != null) {
            playerService.saveAllUsers(true, false);
            Bukkit.getConsoleSender().sendMessage("§aUnloading & Saving all online players to database!");
        }

        if(placeHolder != null)
            placeHolder.unregister();

        if(discordHandler != null)
            discordHandler.shutdown();

        if(em != null)
            em.close();

        try {
            if (context != null) {
                context.close();
            }
        } finally {
            context = null;
            Thread.currentThread().setContextClassLoader(defaultClassLoader);
        }
    }

    private void setupDatabase(Properties properties) {
        boolean isLocal = this.getConfig().getBoolean("localhost", true);

        String host = "localhost";
        int port = 3306;
        String database = "minecraft";
        String username = "root";
        String password = "root";

        if(!isLocal) {
            host = "casa45.fakaheda.eu";
            database = "338529_mysql_db";
            username = "338529_mysql_db";
            password = "MCWdq6jQ2D5K5Zjp";
            System.out.println("Setting up database to external server!");
        }
        else System.out.println("Setting up database to local server!");

        properties.setProperty("spring.datasource.url", "jdbc:mysql://${MYSQL_HOST:" + host + "}:" + port + "/" + database + "?useUnicode=yes&characterEncoding=UTF-8");
        properties.setProperty("spring.datasource.username", username);
        properties.setProperty("spring.datasource.password", password);

    }

    public static WestLand getInstance() {
        return westLand;
    }

    public static DiscordHandler getDiscordHandler() {
        return discordHandler;
    }

    public static ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    @NotNull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nullable
    @Override
    public String getBugTrackerURL() {
        return null;
    }

    public static SlimefunAddon getSlimefunAddonInstance() {
        return westLand;
    }

    public static boolean isIsLocalhost() {
        return isLocalhost;
    }
}
