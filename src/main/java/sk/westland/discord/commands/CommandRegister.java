package sk.westland.discord.commands;


import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandRegister extends ListenerAdapter {

    private final List<Method> registerCommands = new ArrayList<>();

    public void CommandRegisters() {
        /*RunnableHelper.runTaskLater(()-> {
            registerCommands.addAll(scanForMethods());
        }, 10L);*/
    }
/*
    private List<Method> scanForMethods() {
        Reflections reflections = new Reflections("sk.westland.world.commands.discord");
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Command.class);
        return new ArrayList<>(methods);
    }*/

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
                if(finalArgs == null){
                    finalArgs = new String[0];
                }

                if(finalArg == null) {
                    finalArg = "";
                }
                System.out.println(event.getAuthor().getName() + "," +
                        commandName.toString() + "," +
                        Arrays.toString(finalArgs) + "," +
                        finalArg.toString());
                method.setAccessible(true);
                try {
                    method.invoke(
                            method.getDeclaringClass().getConstructors()[0].newInstance(),
                            event.getAuthor(),
                            commandName,
                            finalArgs,
                            finalArg,
                            event);
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