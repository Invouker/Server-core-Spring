package sk.westland.core.discord;


import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.Permission;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.events.message.MessageReceivedEvent;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.hooks.ListenerAdapter;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandRegister extends ListenerAdapter {

    private final List<Method> registerCommands = new ArrayList<>();

    public CommandRegister() {
        Reflections reflections = new Reflections("sk.westland.world.commands.discord", new MethodAnnotationsScanner());
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Command.class);
        registerCommands.addAll(methods);
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if(event.getMessage().getContentDisplay().isEmpty())
            return;

        if(event.getMessage().getAttachments().size() >= 1)
            return;

        String[] content = event.getMessage().getContentDisplay().split(" ");

        if(content.length < 1) {
            return;
        }

        String[] args = null;
        String arg = null;

        if(content.length >= 2) {
            arg = event.getMessage().getContentDisplay().substring(content[0].length()+1);
            args = arg.split(" ");
        }

        String finalArg = arg;
        String[] finalArgs = args;

        for (int i = 0; i < registerCommands.size(); i++) {
            Method method = registerCommands.get(i);
            Command command = method.getAnnotation(Command.class);
            if(command == null)
                return;

            if(event.getMember() == null)
                return;

            if(command.permission() != null &&
                    command.permission().length > 0
                    && command.permission()[0] != Permission.UNKNOWN) {

                if (!event.getMember().hasPermission(command.permission()))
                    return;
            }

            if(content[0].length() <= 0)
                return;
            String commandName = content[0].substring(1);
            if(command.command().equals(commandName) ||
                    (command.aliases() != null && command.aliases().length > 0 &&
                            isAliassesEqualsName(commandName, command.aliases()))) {

                if(!content[0].startsWith(command.commandPrefix()))
                    return;

                if(finalArg != null && finalArg.length() > 0 && finalArgs[0].length() > 0 && finalArgs[0].equalsIgnoreCase("-d")) {
                    if(command.description().length <= 0) return;
                    StringBuilder stringBuilder = new StringBuilder();
                    for(String desc : command.description()) {
                        if (desc.length() <= 0)
                            continue;
                        stringBuilder.append(desc+"\n");
                    }

                    event.getChannel().sendMessage(stringBuilder.toString()).queue();
                    return;
                }

                method.setAccessible(true);
                try {
                    method.invoke(method.getDeclaringClass().getConstructors()[0].newInstance(), event.getAuthor(), commandName, finalArgs, finalArg, event);
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }


    private boolean isAliassesEqualsName(String name, String[] aliases) {
        if(aliases.length <= 0)
            return false;
        for(String s : aliases) {
            if(s.equalsIgnoreCase(name))
                return true;
        }
        return false;
    }
}