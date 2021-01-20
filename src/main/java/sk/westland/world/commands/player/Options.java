package sk.westland.world.commands.player;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.services.PlayerService;
import sk.westland.world.inventories.OptionInventory;

@Component
@CommandLine.Command(name = "option", aliases = {"nastavenia", "options", "settings"})
@HasPermission("commands.player.option")
public class Options implements Runnable {

    @Autowired
    private Context context;

    @Autowired
    private PlayerService playerService;

    @Override
    public void run() {
        OptionInventory optionInventory = new OptionInventory(playerService);
        optionInventory.open(context.getPlayer());
    }
}
