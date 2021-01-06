package sk.westland.world.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.commands.CommandHandler;

public class HelpCommand implements CommandExecutor {

    @Override
    @CommandHandler(commandName="test", description = "idk what", aliases =  "test")
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(command.getName().equals("test")) {
            commandSender.sendMessage("lulw, funguje??");
        }

        return false;
    }
}
