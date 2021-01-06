package sk.wildwest.core.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.reflections.Reflections;
import sk.wildwest.core.WildWest;

import java.lang.reflect.Method;
import java.util.Set;

public class AutoEventRegister {

    public AutoEventRegister() {
        Reflections reflections = new Reflections("sk.wildwest");
        Set<Method> eventMethod = reflections.getMethodsAnnotatedWith(EventHandler.class);

        eventMethod.forEach((method -> {
            try {
                Bukkit.getPluginManager().registerEvents((Listener) method.getDeclaringClass().newInstance(), WildWest.getInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }));
    }
}
