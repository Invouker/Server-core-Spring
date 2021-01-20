package sk.westland.core.services;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.InteractionItem;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.player.WLPlayer;

import java.util.HashMap;
import java.util.Map;

public class ItemInteractionService implements Listener {

    private Map<String, InteractionItem> itemInteractions = new HashMap<>();

    @Autowired
    private PlayerService playerService;

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerInteract(@NotNull PlayerInteractEvent event) {
        if(event.getHand() == EquipmentSlot.OFF_HAND)
            return;

        Player player = event.getPlayer();
        ItemStack itemStack = player.getEquipment() == null ? null : player.getEquipment().getItemInMainHand();
        if(itemStack == null || itemStack.getType() == Material.AIR)
            return;

        if(!itemStack.hasItemMeta())
            return;

        if(!itemStack.getItemMeta().hasDisplayName())
            return;

        String localizedName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
        if(!itemInteractions.containsKey(localizedName))
            return;

        processItemInteraction(itemInteractions.get(localizedName),  event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onPlayerInteract(@NotNull PlayerInteractAtEntityEvent event) {
        if(event.getHand() == EquipmentSlot.OFF_HAND)
            return;

        Player player = event.getPlayer();
        ItemStack itemStack = player.getEquipment() == null ? null : player.getEquipment().getItemInMainHand();
        if(itemStack == null || itemStack.getType() == Material.AIR)
            return;

        if(!itemStack.hasItemMeta())
            return;

        if(!itemStack.getItemMeta().hasDisplayName())
            return;

        String localizedName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
        if(!itemInteractions.containsKey(localizedName))
            return;

        processItemInteraction(itemInteractions.get(localizedName),  event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onPlayerDamageEntity(@NotNull EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player))
            return;

        if(event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK)
            return;

        Player player = (Player) event.getDamager();

        ItemStack itemStack = player.getEquipment() == null ? null : player.getEquipment().getItemInMainHand();
        if(itemStack == null || itemStack.getType() == Material.AIR)
            return;

        if(!itemStack.hasItemMeta())
            return;

        if(!itemStack.getItemMeta().hasDisplayName())
            return;

        String localizedName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
        if(!itemInteractions.containsKey(localizedName))
            return;


        WLPlayer damager = playerService.getWLPlayer(player);
        Entity entity = event.getEntity();

        if(damager == null || entity == null)
            return;

        processItemInteraction(itemInteractions.get(localizedName),
                new WLPlayerDamageEvent(
                        damager,
                        entity,
                       event));
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onPlayerPlaceItem(@NotNull BlockPlaceEvent event) {
        Player player = event.getPlayer();

        ItemStack itemStack = player.getEquipment() == null ? null : player.getEquipment().getItemInMainHand();
        if(itemStack == null || itemStack.getType() == Material.AIR)
            return;

        if(!itemStack.getType().isBlock() &&
                !itemStack.getType().isSolid())
            return;

        if(!itemStack.hasItemMeta())
            return;

        if(!itemStack.getItemMeta().hasDisplayName())
            return;

        String localizedName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
        if(!itemInteractions.containsKey(localizedName))
            return;

        processItemInteraction(itemInteractions.get(localizedName), event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onPlayerBreakBlockWithItem(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();

        ItemStack itemStack = player.getEquipment() == null ? null : player.getEquipment().getItemInMainHand();
        if(itemStack == null || itemStack.getType() == Material.AIR)
            return;

        if(!itemStack.hasItemMeta())
            return;

        if(!itemStack.getItemMeta().hasDisplayName())
            return;

        String localizedName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
        if(!itemInteractions.containsKey(localizedName))
            return;

        processItemInteraction(itemInteractions.get(localizedName), event);
    }

    private void processItemInteraction(InteractionItem item, Event event) {
        if(item == null)
            throw new NullPointerException("ItemInteraction is null");

        if(event instanceof PlayerInteractEvent)
            item.getConsumerInteract().accept((PlayerInteractEvent) event);

        if(event instanceof WLPlayerDamageEvent)
            item.getConsumerDamage().accept((WLPlayerDamageEvent) event);

        if(event instanceof PlayerInteractAtEntityEvent)
            item.getConsumerInteractAtEntity().accept((PlayerInteractAtEntityEvent) event);

        if(event instanceof BlockPlaceEvent)
            item.getConsumerBlockPlaceEvent().accept((BlockPlaceEvent) event);

        if(event instanceof BlockBreakEvent)
            item.getConsumerBlockBreakEvent().accept((BlockBreakEvent) event);
    }

    public void registerItem(String localizedName, InteractionItem item) {
        itemInteractions.putIfAbsent(localizedName, item);
    }

    public ItemStack getItemInMainHand(Entity player) { return ((Player) player).getInventory().getItemInMainHand(); }

    public ItemStack getItemInMainHand(@NotNull Player player) { return player.getInventory().getItemInMainHand(); }

    public void consumeItemInMainHand(Entity player) { consumeItem(this.getItemInMainHand(player)); }

    public void consumeItemInMainHand(Player player) { consumeItem(this.getItemInMainHand(player)); }

    public void consumeItem(ItemStack itemStack) { consumeItem(itemStack, 1); }

    public void applyDamageToItemInMainHand(@NotNull Player player, int damage) {
        player.getInventory().setItemInMainHand(new ItemBuilder(player.getInventory().getItemInMainHand()).applyDurability((short) damage).build());
    }

    public void consumeItem(@NotNull ItemStack itemStack, int amount) {
        if(itemStack.getAmount() > 0) {
            itemStack.setAmount(itemStack.getAmount()-amount);
        }else {
            itemStack = null;
        }
    }

    public void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, SoundCategory.AMBIENT, 2f, 2f);

        for(Entity target : player.getNearbyEntities(10, 10 ,10))  {
            if(target instanceof Player)
                ((Player) target).playSound(player.getLocation(), sound, SoundCategory.AMBIENT, 2f, 2f);
        }
    }

}
