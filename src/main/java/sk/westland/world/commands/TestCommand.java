package sk.westland.world.commands;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.database.player.UserData;
import sk.westland.core.items.CraftingRecipe;
import sk.westland.core.quest.QuestLogMenu;
import sk.westland.core.services.MessageService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.QuestService;
import sk.westland.world.inventories.ChangeJoinMessageInventory;
import sk.westland.world.inventories.ChangeQuitMessageInventory;
import sk.westland.world.items.Materials;

import java.util.List;

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

    @CommandLine.Parameters(index = "0", defaultValue = "false")
    private boolean join;

    @Override
    public void run() {
        if(join) {
            ChangeJoinMessageInventory testInventory = new ChangeJoinMessageInventory(playerService.getWLPlayer(context.getPlayer()), messageService);
            testInventory.open(context.getPlayer());
        }else {
            ChangeQuitMessageInventory testInventory = new ChangeQuitMessageInventory(playerService.getWLPlayer(context.getPlayer()), messageService);
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

            UserData userData = playerService.getWLPlayer(context.getPlayer()).getUserData();

            List<String> recipes = userData.getCraftingRecipe();
        }
    }

    @Component
    @CommandLine.Command(name = "3")
    @HasPermission("commands.test3")
    class Test3 implements Runnable {

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Autowired
        private QuestService questService;


        @Override
        public void run() {
            QuestLogMenu questLogMenu = new QuestLogMenu(playerService, questService);
            questLogMenu.open(context.getPlayer());
        }
    }
}
