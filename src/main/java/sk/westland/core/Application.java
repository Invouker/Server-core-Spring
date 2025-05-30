package sk.westland.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sk.westland.core.services.*;

@SpringBootApplication(scanBasePackages = "sk.westland")
@EnableJpaRepositories()
@EnableAutoConfiguration
public class Application {

    @Bean
    public PlayerService playerService() {
        return new PlayerService();
    }

    @Bean
    public MessageService messageService() { return new MessageService(); }

    @Bean
    public VaultService permissionService() { return new VaultService(); }

    @Bean
    public APIServices apiServices() { return new APIServices(); }

    @Bean
    public ItemInteractionService itemInteractionService() { return new ItemInteractionService(); }

    @Bean
    public RecipeService recipeService() { return new RecipeService(); }

    @Bean
    public BlockService blockService() { return new BlockService(); }

    @Bean
    public MoneyService moneyService() { return new MoneyService(); }

    @Bean
    public HorseService horseService() { return new HorseService(); }

    @Bean
    public DiscordService discordService() { return new DiscordService(); }

    @Bean
    public ServerDataService serverDataService() { return new ServerDataService(); }

    @Bean
    public ScoreboardService scoreboardService() { return new ScoreboardService(); }

    @Bean
    public VotePartyService voteParty() { return new VotePartyService(); }

    @Bean
    public VaultService vaultService() { return new VaultService(); }

    @Bean
    public InventoryService inventoryService() { return new InventoryService(); }

    @Bean
    public RunnableService runnableService() { return  new RunnableService(); }

    @Bean
    public UtilsService utilsService() { return new UtilsService(); }

    @Bean
    public EventManagerService eventManagerService() { return new EventManagerService(); }

    @Bean
    public ResourcePackService resourcePackService() { return new ResourcePackService(); }

    @Bean
    public SlimefunService slimefunService() { return new SlimefunService(); }

    @Bean
    public LotteryService lotteryService() { return new LotteryService(); }
}
