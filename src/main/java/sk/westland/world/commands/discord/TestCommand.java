package sk.westland.world.commands.discord;


import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import sk.westland.discord.commands.Command;
import sk.westland.discord.commands.ICommand;

public class TestCommand implements ICommand {

    @Override  //  !yo
    @Command(command = "yo", description = {"A", "B", "C", "D"}, aliases = {"help","desc", "test"})
    public void onCommand(User user, String command, String[] args, String arg, MessageReceivedEvent event) {
        event.getChannel().sendMessage("Ty piča, čo skušaš, " + event.getAuthor().getName()).queue();

        System.out.println("event.getGuild().getId(): " + event.getGuild().getId());
    }
}
