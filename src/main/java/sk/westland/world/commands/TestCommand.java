package sk.westland.world.commands;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.services.MessageService;
import sk.westland.core.services.PlayerService;
import sk.westland.world.inventories.ChangeJoinMessageInventory;

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

    @Override
    public void run() {
        ChangeJoinMessageInventory testInventory = new ChangeJoinMessageInventory(playerService.getWLPlayer(context.getPlayer()), messageService);
        testInventory.open(context.getPlayer());
    }
}
