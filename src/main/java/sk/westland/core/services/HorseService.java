package sk.westland.core.services;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
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
import sk.westland.core.enums.HorseArmour;
import sk.westland.core.enums.HorseStats;
import sk.westland.core.enums.HorseTier;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.world.items.Materials;

import java.util.HashMap;
import java.util.Map;

public class HorseService implements Listener {

    public static final double HORSE_MAX_HEALTH = 50d;
    private final Map<Player, AbstractHorse> activateHorse = new HashMap<>();

    public ItemStack buildHorse(Player player,int type, int jump, int speed, int health, int color, int style, int armor, int armor_color) {
        ItemStack item = buildHorse(type, jump, speed, health, color, style, armor, armor_color);
        player.getInventory().addItem(item);
        return item;
    }

    public ItemStack buildHorse(Colors horseColor, Style horseStyle) {
        ItemStack saddle = Materials.Items.SADDLE_ITEM.getItem().clone();

        saddle = applyStats(saddle, HorseStats.COLOR, horseColor.getId());
        saddle = applyStats(saddle, HorseStats.STYLE, horseStyle.getId());
        saddle = applyStats(saddle, HorseStats.TYPE, HorseType.HORSE.getId());

        saddle = Nbt.setNbt_Int(saddle, HorseStats.JUMP.getStatName(), 1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.SPEED.getStatName(), 1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.HEALTH.getStatName(), 1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.ARMOR.getStatName(), -1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.ARMOR_COLOR.getStatName(), -1);
        return saddle;
    }

    public ItemStack buildHorse(Colors horseColor, Style horseStyle, int jump, int speed, int health) {
        ItemStack saddle = Materials.Items.SADDLE_ITEM.getItem().clone();

        saddle = applyStats(saddle, HorseStats.COLOR, horseColor.getId());
        saddle = applyStats(saddle, HorseStats.STYLE, horseStyle.getId());
        saddle = applyStats(saddle, HorseStats.TYPE, HorseType.HORSE.getId());

        saddle = Nbt.setNbt_Int(saddle, HorseStats.JUMP.getStatName(), 1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.SPEED.getStatName(), 1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.HEALTH.getStatName(), 1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.ARMOR.getStatName(), -1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.ARMOR_COLOR.getStatName(), -1);

        saddle = applyStats(saddle, HorseStats.JUMP, jump);
        saddle = applyStats(saddle, HorseStats.SPEED, speed);
        return applyStats(saddle, HorseStats.HEALTH, health);
    }

    public ItemStack buildHorse(HorseType horseType) {
        ItemStack saddle = Materials.Items.SADDLE_ITEM.getItem().clone();

        saddle = applyStats(saddle, HorseStats.TYPE, horseType.getId());
        saddle = Nbt.setNbt_Int(saddle, HorseStats.JUMP.getStatName(), 1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.SPEED.getStatName(), 1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.HEALTH.getStatName(), 1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.ARMOR.getStatName(), -1);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.ARMOR_COLOR.getStatName(), -1);
        StringBuilder itemName = new StringBuilder("§e");
        if(horseType == HorseType.SKELETON) itemName.append("Skeleton horse");
        if(horseType == HorseType.ZOMBIE) itemName.append("Zombie horse");
        return new ItemBuilder(saddle).setName(itemName.toString()).removeLoreLine(0).setLoreLine(0, "§r").build();
    }


    public ItemStack buildHorse(int type, int jump, int speed, int health, int color, int style, int armor, int armor_color) {
        ItemStack saddle = Materials.Items.SADDLE_ITEM.getItem();

        saddle = Nbt.setNbt_Int(saddle, HorseStats.JUMP.getStatName(), jump);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.TYPE.getStatName(), type);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.SPEED.getStatName(), speed);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.HEALTH.getStatName(), health);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.COLOR.getStatName(), color);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.STYLE.getStatName(), style);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.ARMOR.getStatName(), armor);
        saddle = Nbt.setNbt_Int(saddle, HorseStats.ARMOR_COLOR.getStatName(), armor_color);
        return new ItemBuilder(saddle).build();
    }

    public ItemStack applyStats(ItemStack itemStack, HorseStats horseStats, int value) {
        ItemBuilder itemBuilder = new ItemBuilder(itemStack);

        int type = Nbt.getNbt_Int(itemStack, HorseStats.TYPE.getStatName(), -2);

        if(value == -1)
            return itemBuilder.build();

        if(type == -2)
            throw new NullPointerException("NBT Horse type is null!");


        int posUpgrade = 0;
        if(type != HorseType.HORSE.getId())
            posUpgrade = 1;

        switch (horseStats) {
            case TYPE:
                return itemBuilder
                        .setNbt_Int(horseStats.getStatName(), value)
                        .build();
            case COLOR: {
                return itemBuilder
                        .setNbt_Int(horseStats.getStatName(), value)
                        .setName(ChatColor.of("#99ddff") + StringUtils.capitalise(Colors.findById(value).getName().replace("_", " ") + "") + " kôň")
                        .build();
            }
            case STYLE:
                return itemBuilder
                        .setNbt_Int(HorseStats.STYLE.getStatName(), value)
                        .setLoreLine(0, "§7Štýl: §f" + StringUtils.capitalise(Style.findById(value).getName().toLowerCase()))
                        .build();
            case JUMP:
                return itemBuilder
                        .setNbt_Int(horseStats.getStatName(), value)
                        .setLoreLine(2-posUpgrade, "§7Jump Tier: §f" + value).build();
            case SPEED:
                return itemBuilder
                    .setNbt_Int(horseStats.getStatName(), value)
                    .setLoreLine(3-posUpgrade, "§7Speed Tier: §f" + value).build();
            case HEALTH:
                return itemBuilder
                        .setNbt_Int(horseStats.getStatName(), value)
                        .setLoreLine(4-posUpgrade, "§7Health Tier: §f" + value).build();
            case ARMOR:
            case ARMOR_COLOR:
                return itemBuilder
                        .setNbt_Int(horseStats.getStatName(), value)
                        .build();

        }

        return itemBuilder.build();
    }
    
    public boolean addPlayer(Player player, AbstractHorse horse) {
        if(activateHorse.containsKey(player))
           return false;

        activateHorse.put(player, horse);
        return true;
    }

    public void removePlayer(Player player) {
        if(!activateHorse.containsKey(player))
            return;

        AbstractHorse horse = getPlayerHorse(player);
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

    public AbstractHorse getPlayerHorse(Player player) {
        if(!activateHorse.containsKey(player))
            return null;

        return activateHorse.get(player);
    }

    public void applyStat(AbstractHorse horse, ItemStack itemStack, HorseStats horseStat) {
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
                ((Horse)horse).setColor(Horse.Color.values()[id]);
                break;
            }
            case STYLE: {
                ((Horse)horse).setStyle(Horse.Style.values()[id]);
                break;
            }
            case ARMOR: {
                if(id == -1)
                    return;

                HorseArmour horseArmour = HorseArmour.findById(id);
                if(horseArmour == null)
                    return;

                Material material = horseArmour.getMaterial();
                ((Horse)horse).getInventory().setArmor(new ItemStack(material));
                break;
            }

            case ARMOR_COLOR: { // 0 - 2
                ItemStack armor = ((Horse)horse).getInventory().getArmor();
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

    private void setHorseHealth(AbstractHorse horse, double health) {
        if(health > HORSE_MAX_HEALTH)
            return;

        horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
    }

    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        if(!event.getPlugin().getName().equalsIgnoreCase("westland"))
            return;

        activateHorse.forEach(((player, horse) -> horse.remove()));
    }

    private void removeHorseOnRestart(PluginDisableEvent event) {
        if(activateHorse != null)
            for(Map.Entry<Player, ? extends AbstractHorse> playerHorseEntry : activateHorse.entrySet()) {
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


    public enum HorseType {
        HORSE(1, "Obyčajný"),
        ZOMBIE(2, "Zombie"),
        SKELETON(3, "Skeleton")
        ;

        private int id;
        private String typeName;

        HorseType(int id, String typeName) {
            this.id = id;
            this.typeName = typeName;
        }

        public int getId() {
            return id;
        }

        public String getTypeName() {
            return typeName;
        }

        public static HorseType findById(int id) {
            for(HorseType horseType : HorseType.values()) {
                if(horseType.getId() == id)
                    return horseType;
            }
            return null;
        }
    }

    public enum Colors {
        WHITE(0, "Biely"),
        CREAMY(1, "Krémový"),
        CHESTNUT(2, "Gaštanový"),
        BROWN(3, "Hnedý"),
        BLACK(4, "Čierny"),
        GRAY(5, "Sivý"),
        DARKBROWN(6, "Tmavo hnedý");

        private int id;
        private String name;

        Colors(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public static Colors findById(int id) {
            for(Colors horses : Colors.values()) {
                if(horses.getId() == id)
                    return horses;
            }
            return null;
        }
    }

    public enum Style {
        NONE(0, "Žiadny"),
        WHITE(1, "Biely"),
        WHITE_FIELD(2, "Biele fľaky"),
        WHITE_DOTS(3, "Biele bodky"),
        BLACK_DOTS(4, "Čierne bodky")
        ;

        private int id;
        private String name;

        Style(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public static Style findById(int id) {
            for(Style style : Style.values()) {
                if(style.getId() == id)
                    return style;
            }
            return null;
        }
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
