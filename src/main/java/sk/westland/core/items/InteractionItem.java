package sk.westland.core.items;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.event.player.WLPlayerDamageEvent;

import java.util.function.Consumer;

public class InteractionItem implements Listener {


    private int customModelID;
    private ItemStack itemStack;

    private Consumer<PlayerInteractEvent> consumerInteract;
    private Consumer<WLPlayerDamageEvent> consumerDamage;
    private Consumer<PlayerInteractAtEntityEvent> consumerInteractAtEntity;

    public InteractionItem(ItemStack itemStack, int CustomModelID, Consumer<PlayerInteractEvent> consumerInteract, Consumer<WLPlayerDamageEvent> consumerDamage, Consumer<PlayerInteractAtEntityEvent> consumerInteractAtEntity) {
        this.customModelID = CustomModelID;
        this.consumerInteract = consumerInteract;
        this.consumerDamage = consumerDamage;
        this.consumerInteractAtEntity = consumerInteractAtEntity;
    }

    public int getCustomModelID() {
        return customModelID;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<PlayerInteractEvent> getConsumerInteract() {
        return consumerInteract;
    }

    public Consumer<WLPlayerDamageEvent> getConsumerDamage() {
        return consumerDamage;
    }

    public Consumer<PlayerInteractAtEntityEvent> getConsumerInteractAtEntity() {
        return consumerInteractAtEntity;
    }
}
