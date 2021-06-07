package sk.westland.core;

import org.hibernate.service.NullServiceException;
import org.springframework.stereotype.Component;
import sk.westland.core.services.*;
import sk.westland.discord.PermissionHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class App {

    private static final List<Object> applications = new ArrayList<>();

    public App() {
    }

    public App(APIServices apiServices, BlockService blockService, DiscordService discordService, HorseService horseService, InventoryService inventoryService,
               ItemInteractionService itemInteractionService, MessageService messageService, MoneyService moneyService, PlayerService playerService,
               RecipeService recipeService, ScoreboardService scoreboardService, ServerDataService serverDataService, VaultService vaultService,
               VotePartyService votePartyService, UtilsService utilsService, EventManagerService eventManagerService, PermissionHandler permissionHandler) {

        applications.add(apiServices);
        applications.add(blockService);
        applications.add(discordService);
        applications.add(horseService);
        applications.add(inventoryService);
        applications.add(itemInteractionService);
        applications.add(messageService);
        applications.add(moneyService);
        applications.add(playerService);
        applications.add(recipeService);
        applications.add(scoreboardService);
        applications.add(serverDataService);
        applications.add(vaultService);
        applications.add(votePartyService);
        applications.add(utilsService);
        applications.add(eventManagerService);
        applications.add(permissionHandler);

        System.out.println("[APP] Registred services " + applications.size());

    }

    public static <T> T getService(Class<T> clazz) {
        Getter<T> getter = new Getter<>(applications, clazz);
        if(getter == null)
            throw new NullServiceException(clazz);

        return getter.getObject();
    }

    static class Getter<T> {

        private final List<Object> objects;
        private final Class<T> clazz;

        public Getter(List<Object> arrayList, Class<T> clazz) {
            this.objects = arrayList;
            this.clazz = clazz;
        }

        public T getObject() {
            for (Object object : objects) {
                if (object.getClass() == clazz) {
                    return (T) object;
                }
            }
            return null;
        }
    }


}
