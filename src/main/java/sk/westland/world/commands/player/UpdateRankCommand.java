package sk.westland.world.commands.player;

import dev.alangomes.springspigot.context.Context;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.VaultService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.discord.PermissionHandler;

@Component
@Command(name = "rankupdate", description = "Pre updatovanie ranku na discorde", aliases = {"update", "discordupdate", "updaterank"})
public class UpdateRankCommand implements Runnable {

     @Autowired
     private PlayerService playerService;

     @Autowired
     private Context context;

     @Autowired
     private VaultService vaultService;

    @Override
    public void run() {
        Player player = context.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        String groupName = vaultService.getPerms().getPrimaryGroup(player);
        PermissionHandler.getPermissionHandler().updateRole(wlPlayer.getPlayerId(), groupName);
        ChatInfo.SUCCESS.send(player, "Úspešne si si updatol rank na discorde!");
    }
}
