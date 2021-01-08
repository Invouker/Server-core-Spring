package sk.westland.core;

import dev.alangomes.springspigot.SpringSpigotInitializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import sk.westland.core.application.Application;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

public class WestLand extends JavaPlugin {

    public static WestLand westLand;
    private ConfigurableApplicationContext context;
    private ClassLoader defaultClassLoader;

    @Override
    public void onEnable() {
        super.onEnable();

        westLand = this;

        System.out.println("Loading Spring framework...");
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

        this.context = application.run();

        System.out.println("Spring framework loaded.");

        setupDatabase(properties);

        application.setDefaultProperties(properties);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        Thread.currentThread().setContextClassLoader(defaultClassLoader);

        if(context != null) {
            context.close();
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
