package sk.westland.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sk.westland.core.services.BeanWire;

import java.util.ArrayList;
import java.util.List;

@Component
public class App {

    private static final List<Object> applications = new ArrayList<>();

    public App() {
        configurableApplicationContext = configurableApplicationContextWired;
    }

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContextWired;

    private static ConfigurableApplicationContext configurableApplicationContext;

    public static void registerService(Object object) {
        applications.add(object);
    }

    @EventListener
    public void showBeansRegistered(ApplicationReadyEvent event) {
        StringBuilder stringServices = new StringBuilder("[APP] Registring serices: ");
        event.getApplicationContext().getBeansOfType(BeanWire.class).forEach((a, b) -> {
            registerService(b);
            stringServices.append(a);
            stringServices.append(",");
        });


        System.out.println(stringServices);
    }

    public static <T> T getService(Class<T> clazz) {
        var getter = new Getter<>(applications, clazz);
        if(getter == null) {
            configurableApplicationContext.getBeansOfType(BeanWire.class).forEach((a, b) -> {
                if(b.getClass().getSimpleName().equals(clazz.getSimpleName())) {
                    registerService(b);
                    System.out.println("Registred unknown class!");
                }
            });
            //throw new NullPointerException("Service is not registred: " + clazz.getSimpleName());
        }

        return getter.getObject();
    }

    static class Getter<T> {

        private final List<Object> objects;
        private final Class<T> clazz;

        public Getter(List<Object> arrayList, Class<T> clazz) {
            this.objects = arrayList;
            this.clazz = clazz;
        }

        public T getObject() {
            for (Object object : objects) {
                if (object != null && object.getClass() == clazz) {
                    return (T) object;
                }
            }
            System.err.println("[APP] Service of " + clazz.getSimpleName() + " doesnt exist!");
            return null;
        }
    }


}
