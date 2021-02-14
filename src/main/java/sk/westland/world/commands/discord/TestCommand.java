package sk.westland.world.commands.discord;


import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.entities.User;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.events.message.MessageReceivedEvent;
import sk.westland.core.discord.Command;
import sk.westland.core.discord.ICommand;

public class TestCommand implements ICommand {

    @Override
    @Command(command = "yo", description = {"A", "B", "C", "D"}, aliases = {"help","desc", "test"})
    public void onCommand(User user, String command, String[] args, String arg, MessageReceivedEvent event) {
        event.getChannel().sendMessage("Ty piča, čo skušaš, " + event.getAuthor().getName()).queue();
    }
}
