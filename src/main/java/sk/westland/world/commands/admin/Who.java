package sk.westland.world.commands.admin;

import dev.alangomes.springspigot.context.Context;
import org.bukkit.Bukkit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "who", aliases = {"list", "players", "player", "onlines", "online"})
public class Who implements Runnable {

    @Autowired
    private Context context;

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        Bukkit.getOnlinePlayers().forEach((player -> stringBuilder.append("§7").append(player.getName()).append("§8, ")));
        context.getSender().sendMessage("\n§cOnline hráči ( " + Bukkit.getOnlinePlayers().size() + " ): \n" + stringBuilder + "\n");
    }
}
