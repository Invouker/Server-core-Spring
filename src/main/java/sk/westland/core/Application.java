package sk.westland.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sk.westland.core.services.PlayerDataStorageService;
import sk.westland.core.services.PlayerService;

@SpringBootApplication(scanBasePackages = "sk.westland")
@EnableJpaRepositories()
public class Application {

    @Bean
    public PlayerService playerService() {
        return new PlayerService();
    }

    @Bean
    public PlayerDataStorageService playerDataStorageService() { return new PlayerDataStorageService(); }

}
