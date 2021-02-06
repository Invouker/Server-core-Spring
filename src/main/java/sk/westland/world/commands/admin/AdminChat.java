package sk.westland.world.commands.admin;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.services.MessageService;
import sk.westland.core.utils.ChatInfo;


@Component
@CommandLine.Command(name = "ac")
@HasPermission("adminchat")
public class AdminChat implements Runnable{

    @Autowired
    private Context context;

    @Autowired
    private MessageService messageService;

    @Override
    public void run() {
        if(!(context.getSender() instanceof Player))
            return;

        Player player = context.getPlayer();

        if(messageService.isPlayerInAdminChat(player)) {
            ChatInfo.SUCCESS.send(player, "Admin chat §cdeaktivovaný§a!");
            messageService.removePlayerFromAdminChat(player);
        } else {
            ChatInfo.SUCCESS.send(player, "Admin chat aktivovaný!");
            messageService.addPlayerToAdminChat(player);
        }
    }
}
