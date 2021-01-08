package sk.westland.core.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import sk.westland.core.WestLand;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class AutoEventRegister {

    public AutoEventRegister() {
        Reflections reflections = new Reflections("sk.westland", new MethodAnnotationsScanner());
        Set<Method> eventMethod = reflections.getMethodsAnnotatedWith(EventHandler.class);


        eventMethod.forEach((method -> {
            try {
                Bukkit.getPluginManager().registerEvents((Listener) method.getDeclaringClass().newInstance(),
                        WestLand.getInstance());
            } catch (InstantiationException | IllegalAccessException  e) {
                e.printStackTrace();
            }
        }));
    }
}
