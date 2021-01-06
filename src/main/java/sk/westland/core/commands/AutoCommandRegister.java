package sk.westland.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

public class AutoCommandRegister {

    public AutoCommandRegister()  {
        Field bukkitCommandMap = null;
        try {
            bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = null;

            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            Reflections reflections = new Reflections("sk.westland.world.commands");
            Set<Method> methodSet = reflections.getMethodsAnnotatedWith(CommandHandler.class);

            for(Method method : methodSet) {
                CommandHandler commandHandler = method.getAnnotation(CommandHandler.class);

                commandMap.register(commandHandler.commandName(), (Command) method.getDeclaringClass().newInstance());
                Command command = commandMap.getCommand(commandHandler.commandName());

                if(commandHandler.aliases().length > 0)
                    commandMap.getCommand(command.getName()).setAliases(Arrays.asList(commandHandler.aliases()));

                if(commandHandler.description().length() > 0)
                    command.setDescription(commandHandler.description());
            }

        } catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }





    }
}
