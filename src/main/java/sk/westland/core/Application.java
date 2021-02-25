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
    public VaultService permissionService() { return new VaultService(); }

    @Bean
    public ItemInteractionService itemInteractionService() { return new ItemInteractionService(); }

    @Bean
    public RecipeService recipeService() { return new RecipeService(); }

    @Bean
    public QuestService questService() { return new QuestService(); }

    @Bean
    public BlockService blockService() { return new BlockService(); }

    @Bean
    public InteractionService interactionService() { return new InteractionService(); }

    @Bean
    public MoneyService moneyService() { return new MoneyService(); }

    @Bean
    public HorseService horseService() { return new HorseService(); }

    @Bean
    public DiscordService discordService() { return new DiscordService(); }

}
