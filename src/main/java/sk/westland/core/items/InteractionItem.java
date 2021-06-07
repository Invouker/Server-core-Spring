package sk.westland.core.items;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.event.player.WLPlayerDamageEvent;

import java.util.function.Consumer;

public class InteractionItem implements Listener {

    private final int customModelID;
    private final ItemStack itemStack;

    private final Consumer<PlayerInteractEvent> consumerInteract;
    private final Consumer<WLPlayerDamageEvent> consumerDamage;
    private final Consumer<PlayerInteractAtEntityEvent> consumerInteractAtEntity;
    private final Consumer<BlockPlaceEvent> consumerBlockPlaceEvent;
    private final Consumer<BlockBreakEvent> consumerBlockBreakEvent;
    private final Consumer<BlockBreakEvent> consumerBlockBreakEventAll;

    public InteractionItem(int customModelID, ItemStack itemStack, Consumer<PlayerInteractEvent> consumerInteract, Consumer<WLPlayerDamageEvent> consumerDamage, Consumer<PlayerInteractAtEntityEvent> consumerInteractAtEntity, Consumer<BlockPlaceEvent> consumerBlockPlaceEvent, Consumer<BlockBreakEvent> consumerBlockBreakEvent, Consumer<BlockBreakEvent> blockBreakEventConsumer) {
        this.customModelID = customModelID;
        this.itemStack = itemStack;
        this.consumerInteract = consumerInteract;
        this.consumerDamage = consumerDamage;
        this.consumerInteractAtEntity = consumerInteractAtEntity;
        this.consumerBlockPlaceEvent = consumerBlockPlaceEvent;
        this.consumerBlockBreakEvent = consumerBlockBreakEvent;
        this.consumerBlockBreakEventAll = blockBreakEventConsumer;
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

    public Consumer<BlockPlaceEvent> getConsumerBlockPlaceEvent () {
        return consumerBlockPlaceEvent;
    }

    public Consumer<BlockBreakEvent> getConsumerBlockBreakEvent() {
        return consumerBlockBreakEvent;
    }

    public Consumer<BlockBreakEvent> getConsumerBlockBreakEventAll() {
        return consumerBlockBreakEventAll;
    }
}
