package sk.westland.world.commands.player;

import dev.alangomes.springspigot.context.Context;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.services.MoneyService;
import sk.westland.core.utils.ChatInfo;

@Component
@CommandLine.Command(name = "shards", aliases = {"shard", "shardy"})
public class ShardCommands implements Runnable {

    @Autowired
    private Context context;

    @Autowired
    private MoneyService moneyService;

    @Override
    public void run() {
        Player player = context.getPlayer();
        double shards = moneyService.get(player, MoneyType.Shard);
        ChatInfo.SUCCESS.send(context.getPlayer(), "Aktuálne máš §6" + shards + "§f shardov.");
    }
}
