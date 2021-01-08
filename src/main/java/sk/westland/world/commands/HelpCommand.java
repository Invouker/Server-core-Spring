package sk.westland.world.commands;


import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.services.ServerService;

@Component
@CommandLine.Command(name = "playerinfo")
@HasPermission("playerinfo")
public class HelpCommand implements Runnable {

    @Autowired
    private ServerService serverService;

    @Autowired
    private Context context;

    @Override
    public void run() {
       context.getSender().sendMessage("Sending a message?" + serverService.getName());
    }
}
