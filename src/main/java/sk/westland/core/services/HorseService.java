package sk.westland.core.services;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.codehaus.plexus.util.StringUtils;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.springframework.stereotype.Service;
import sk.westland.core.enums.HorseArmour;
import sk.westland.core.enums.HorseStats;
import sk.westland.core.enums.HorseTier;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.world.items.Materials;
import sk.westland.world.items.tools.SaddleItem;

import java.util.HashMap;
import java.util.Map;

@Service
public class HorseService implements Listener {

    public static final double HORSE_MAX_HEALTH = 50d;
    private Map<Player, Horse> activateHorse = new HashMap<>();

    public ItemStack buildHorse(Player player, int jump, int speed, int health, int color, int style, int armor, int armor_color) {
        ItemStack item = buildHorse(jump, speed, health, color, style, armor, armor_color);
        player.getInventory().addItem(item);
        return item;
    }

    public ItemStack buildHorse(int jump, int speed, int health, int color, int style, int armor, int armor_color) {
        ItemStack saddle = Materials.Items.SADDLE_ITEM.getItem();

        saddle = Nbt.setNbt_Int(saddle, HorseStats.JUMP.getStatName(), jump);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.SPEED.getStatName(), speed);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.HEALTH.getStatName(), health);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.COLOR.getStatName(), color);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.STYLE.getStatName(), style);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.ARMOR.getStatName(), armor);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.ARMOR_COLOR.getStatName(), armor_color);
        return new ItemBuilder(saddle).build();
    }

    public ItemStack applyStats(ItemStack itemStack, HorseStats horseStats, int value) {
        switch (horseStats) {
            case COLOR:
                return new ItemBuilder(itemStack)
                        .setNbt_Int(horseStats.getStatName(), value)
                        .setLoreLine(0, "§f" + StringUtils.capitalise(SaddleItem.Colors.findById(value).getName().replace("_", " ") + "") + " kôň")
                        .build();
            case STYLE:
                return new ItemBuilder(itemStack)
                        .setNbt_Int(HorseStats.STYLE.getStatName(), value)
                        .setLoreLine(1, "§7Štýl: §f" + StringUtils.capitalise(SaddleItem.Style.findById(value).getName().toLowerCase()))
                        .build();
            case JUMP:
                return new ItemBuilder(itemStack)
                        .setNbt_Int(horseStats.getStatName(), value)
                        .setLoreLine(3, "§7Jump Tier: §f" + value).build();
            case SPEED:
                return new ItemBuilder(itemStack)
                    .setNbt_Int(horseStats.getStatName(), value)
                    .setLoreLine(4, "§7Speed Tier: §f" + value).build();
            case HEALTH:
                return new ItemBuilder(itemStack)
                        .setNbt_Int(horseStats.getStatName(), value)
                        .setLoreLine(5, "§7Health Tier: §f" + value).build();
            case ARMOR:
            case ARMOR_COLOR:
                return new ItemBuilder(itemStack)
                        .setNbt_Int(horseStats.getStatName(), value)
                        .build();

        }

        return null;
    }
    
    public boolean addPlayer(Player player, Horse horse) {
        if(activateHorse.containsKey(player))
           return false;

        activateHorse.put(player, horse);
        return true;
    }

    public void removePlayer(Player player) {
        if(!activateHorse.containsKey(player))
            return;

        Horse horse = getPlayerHorse(player);
        horse.remove();

        ItemStack saddle = findSaddleInInventory(player);
        if(saddle == null)
            return;

        ItemBuilder itemStack = new ItemBuilder(saddle);
        itemStack.setNbt_Bool(HorseStats.SPAWNED.getStatName(), false);

        activateHorse.remove(player);
    }

    private ItemStack findSaddleInInventory(Player player) {
        for(ItemStack itemStack : player.getInventory().getContents()) {
            if(Nbt.getNbt_Bool(itemStack, HorseStats.SADDLE.getStatName(), false))
                return itemStack;
        }
        return null;
    }

    public Horse getPlayerHorse(Player player) {
        if(!activateHorse.containsKey(player))
            return null;

        return activateHorse.get(player);
    }

    public void applyStat(Horse horse, ItemStack itemStack, HorseStats horseStat) {
        String statName = horseStat.getStatName();
        int id = Nbt.getNbt_Int(itemStack, statName, 1);
        HorseTier horseTier = HorseTier.findById(id);
        switch(horseStat) {
            case JUMP: { // 0 - 2
               horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(horseTier.getJumpValue());
                break;
            }
            case SPEED: {
                horse.getAttribute( Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horseTier.getSpeedValue());
                break;
            }
            case HEALTH: {
                 setHorseHealth(horse, horseTier.getHealthValue());
                break;
            }
            case COLOR: {
                horse.setColor(Horse.Color.values()[id]);
                break;
            }
            case STYLE: {
                horse.setStyle(Horse.Style.values()[id]);
                break;
            }
            case ARMOR: {
                if(id == -1)
                    return;

                HorseArmour horseArmour = HorseArmour.findById(id);
                if(horseArmour == null)
                    return;

                Material material = horseArmour.getMaterial();
                horse.getInventory().setArmor(new ItemStack(material));
                break;
            }

            case ARMOR_COLOR: { // 0 - 2
                ItemStack armor = horse.getInventory().getArmor();
                if (armor == null || armor.getType() != Material.LEATHER_HORSE_ARMOR)
                    return;

                if(!armor.hasItemMeta())
                    return;

                if(!(armor.getItemMeta() instanceof LeatherArmorMeta))
                    return;
                LeatherArmorMeta meta = (LeatherArmorMeta) armor.getItemMeta();

                int[] color = Nbt.getNbt_IntArray(itemStack, statName, new int[] {0, 0, 0});
                if(color.length > 0)
                    meta.setColor(Color.fromRGB(color[0], color[1], color[2]));

                break;
            }
            default:
                break;
        }
    }

    private void setHorseHealth(Horse horse, double health) {
        if(health > HORSE_MAX_HEALTH)
            return;

        horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
    }

    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        activateHorse.forEach(((player, horse) -> horse.remove()));
    }

    private void removeHorseOnRestart(PluginDisableEvent event) {
        if(activateHorse != null)
            for(Map.Entry<Player, Horse> playerHorseEntry : activateHorse.entrySet()) {
                playerHorseEntry.getValue().remove();
            }
    }

    @EventHandler(ignoreCancelled = true)
    private void onEntityDismount(EntityDismountEvent event) {
        if(!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        removePlayer(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {

    }

    @EventHandler(ignoreCancelled = true)
    private void onInventoryMoveItem(InventoryEvent event) { // TODO: zabraniť daniu špecialneho sedla na koňa

    }

    @EventHandler(ignoreCancelled = true)
    private void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null)
            return;

        if(event.getCurrentItem() == null || event.getCursor() == null)
            return;

        if(event.getClickedInventory() instanceof HorseInventory) {

            if(event.getClick() == ClickType.SHIFT_RIGHT || event.getClick() == ClickType.NUMBER_KEY || event.getClick() == ClickType.SHIFT_LEFT)

            if(!Nbt.getNbt_Bool(event.getCursor(), HorseStats.SADDLE.getStatName(), false))
                return;

            event.setCancelled(true);
            event.setResult(Event.Result.DENY);

            ItemStack cursorItem = event.getCursor();
            event.setCursor(null);

            event.getWhoClicked().getInventory().addItem(cursorItem);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onInventoryOpen(InventoryOpenEvent event) {
        removePlayer((Player) event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    private void onWLPlayerQuit(PlayerQuitEvent event) {
        removePlayer(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerInteract(PlayerInteractEvent event) {
        removePlayer(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    private void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if(event.getEntity() instanceof Horse) {
           Horse horse = (Horse) entity;
           horse.getInventory().clear();
           event.getDrops().clear();
           horse.remove();
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        removePlayer(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerDeath(PlayerDeathEvent event) {
       removePlayer(event.getEntity());
    }


    /*
    * Player Open Chest // DONE
    * Player Quit // DONE
    * Player Open Inventory // DONE
    * Player Dismount // DONE
    * Player Interact with lever/button/dispenser/hopper/dropper
    *
    * Entity death event ( Horse )
    *  - remove armour
    *
    * InventoryClickEvent // Vôbec nejde, Vypnuté cez Inventory Open
    * - Cancel remove
    * armor / saddle
    * */


}
























