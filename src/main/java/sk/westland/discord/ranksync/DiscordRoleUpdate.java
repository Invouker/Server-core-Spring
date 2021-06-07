package sk.westland.discord.ranksync;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.VaultService;
import sk.westland.discord.PermissionHandler;

@Component
public class DiscordRoleUpdate implements Listener {

    @Autowired
    private VaultService vaultService;

    @Autowired
    private PlayerService playerService;

    public DiscordRoleUpdate() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(WestLand.getInstance(), () -> {

            Bukkit.getOnlinePlayers().forEach((player -> {
                WLPlayer wlPlayer = playerService.getWLPlayer(player);
                String groupName = vaultService.getPerms().getPrimaryGroup(player);
                if(wlPlayer == null)
                    return;

                PermissionHandler.getPermissionHandler().updateRole(wlPlayer.getPlayerId(), groupName);
            }));

        }, 20*10L, 20*60L);
    }
}
