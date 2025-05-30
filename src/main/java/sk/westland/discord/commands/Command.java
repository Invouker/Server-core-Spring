package sk.westland.discord.commands;


import net.dv8tion.jda.api.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
    String command();
    String commandPrefix() default "!";
    String[] aliases() default {};
    String[] description();
    Permission[] permission() default {Permission.UNKNOWN};

}