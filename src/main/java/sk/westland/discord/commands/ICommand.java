package sk.westland.discord.commands;


import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand {

    void onCommand(User user, String command, String[] args, String arg, MessageReceivedEvent event);

}