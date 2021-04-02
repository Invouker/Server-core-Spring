package sk.westland.core;

import com.bekvon.bukkit.residence.protection.FlagPermissions;
import dev.alangomes.springspigot.SpringSpigotInitializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.ServerDisableEvent;
import sk.westland.core.services.BlockService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.ScoreboardService;
import sk.westland.core.utils.PlaceHolder;
import sk.westland.core.utils.ResFlag;
import sk.westland.world.items.Materials;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

public class WestLand extends JavaPlugin {

    public static final String CUSTOM_BLOCK_NBT = "BLOCK_ID";

    public static WestLand westLand;
    private ConfigurableApplicationContext context;
    private ClassLoader defaultClassLoader;

    private PlaceHolder placeHolder;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ScoreboardService scoreboardService;

    @Autowired
    private BlockService blockService;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void onEnable() {
        super.onEnable();

        westLand = this;

        Bukkit.getConsoleSender().sendMessage("§a");
        Bukkit.getConsoleSender().sendMessage("§aLoading Spring framework...");

        saveDefaultConfig();

        defaultClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(getClassLoader());

        ResourceLoader loader = new DefaultResourceLoader(getClassLoader());
        SpringApplication application = new SpringApplication(loader, Application.class)  {
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

        try {
            this.context = application.run();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        setupDatabase(properties);

        application.setDefaultProperties(properties);

        Bukkit.getPluginManager().callEvent(new PluginEnableEvent(this));

        try {
            placeHolder = new PlaceHolder(this, playerService, scoreboardService);
            placeHolder.register();
        }  catch (Exception ex) {
            System.out.println("While hooking into PlaceholderAPI: " + ex.getLocalizedMessage());
        }
        if(Bukkit.getOnlinePlayers().size() > 0) {
            Bukkit.getOnlinePlayers().forEach((player -> playerService.loadUser(player)));
        }

        for (ResFlag resFlag : ResFlag.values()) {
            FlagPermissions.addFlag(resFlag.getFlagName());
        }

        //FlagPermissions.addFlag("mob-catch");

        Bukkit.getConsoleSender().sendMessage("§aLoaded " + Materials.Items.values().length + " custom items!");
        Bukkit.getConsoleSender().sendMessage("§aLoaded " + Materials.Resources.values().length + " resource items!");
        Bukkit.getConsoleSender().sendMessage("§aLoaded " + blockService.getLOADED_BLOCKS() + " blocks!");

        Bukkit.getConsoleSender().sendMessage("§a");
        Bukkit.getConsoleSender().sendMessage("§aSpring framework successfully started!");
        Bukkit.getConsoleSender().sendMessage("§a");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Bukkit.getPluginManager().callEvent(new ServerDisableEvent(westLand));

        placeHolder.unregister();

        em.close();

        Thread.currentThread().setContextClassLoader(defaultClassLoader);

        if(context != null) {
            try {
                context.close();
            }catch (Exception ignored) {}
            context = null;
        }
    }

    public static WestLand getInstance() {
        return westLand;
    }

    private void setupDatabase(Properties properties) {
            String host = "casa45.fakaheda.eu";
            int port = 3306;
            String database = "338529_mysql_db";
            String username = "338529_mysql_db";
            String password = "MCWdq6jQ2D5K5Zjp";

            properties.setProperty("spring.datasource.url", "jdbc:mysql://${MYSQL_HOST:" + host + "}:" + port + "/" + database + "?useUnicode=yes&characterEncoding=UTF-8");
            properties.setProperty("spring.datasource.username", username);
            properties.setProperty("spring.datasource.password", password);
    }

}
