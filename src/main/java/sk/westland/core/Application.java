package sk.westland.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sk.westland.core.services.*;

@SpringBootApplication(scanBasePackages = "sk.westland")
@EnableJpaRepositories()
public class Application {

    @Bean
    public PlayerService playerService() {
        return new PlayerService();
    }

    @Bean
    public PlayerDataStorageService playerDataStorageService() { return new PlayerDataStorageService(); }

    @Bean
    public MessageService messageService() { return new MessageService(); }

    @Bean
    public PermissionService permissionService() { return new PermissionService(); }

    @Bean
    public PlaceholderAPIService placeholderAPIService() { return new PlaceholderAPIService(); }

    @Bean
    public ItemInteractionService itemInteractionService() { return new ItemInteractionService(); }

    @Bean
    public RecipeService recipeService() { return new RecipeService(); }

}
