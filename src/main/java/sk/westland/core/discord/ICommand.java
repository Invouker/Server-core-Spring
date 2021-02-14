package sk.westland.core.discord;

import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.entities.User;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.events.message.MessageReceivedEvent;

public interface ICommand {

    public void onCommand(User user, String command, String[] args, String arg, MessageReceivedEvent event);

}