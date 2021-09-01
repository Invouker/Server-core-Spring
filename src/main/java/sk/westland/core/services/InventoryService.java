package sk.westland.core.services;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.springframework.dao.DuplicateKeyException;
import sk.westland.core.inventory.rc.InventoryHandler;

import java.util.HashSet;
import java.util.Set;

public class InventoryService implements Listener, BeanWire {

    private final Set<InventoryHandler> inventoryHandlers = new HashSet<>();

    public void registerInventory(InventoryHandler inventoryHandler) {
        if(inventoryHandlers.contains(inventoryHandler))
            throw new DuplicateKeyException("InventoryHandler contains " + inventoryHandler.getInventoryName());
        inventoryHandlers.add(inventoryHandler);
    }

    //@EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        System.out.println("inventoryHandlers: " + inventoryHandlers.stream().findAny().get().getInventoryHolder());
        System.out.println("holder: " + event.getInventory().getHolder());
        inventoryHandlers.stream()
                .filter((inventoryHandler -> inventoryHandler.getInventoryHolder() == event.getInventory().getHolder()))
                //.filter(inventoryHandler -> inventoryHandler.getInventoryClickEventConsumer())
                .forEach(inventoryHandler ->  {
                    System.out.println("holder accept event: " + inventoryHandler.getInventoryName());
                    inventoryHandler.getInventoryClickEventConsumer().accept(event);
                });
    }

    //@EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) {
        inventoryHandlers.stream()
                .filter((inventoryHandler -> inventoryHandler.getInventoryHolder().equals(event.getInventory().getHolder())))
                .filter(inventoryHandler -> inventoryHandler.getInventoryOpenEventConsumer() != null)
                .forEach(inventoryHandler ->  {
                    inventoryHandler.getInventory().clear();
                    inventoryHandler.updateInventory();
                    inventoryHandler.getInventoryOpenEventConsumer().accept(event);
                });
    }
}
