package sk.westland.world.items.tools;

import net.minecraft.server.v1_16_R3.HorseColor;
import net.minecraft.server.v1_16_R3.HorseStyle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.enums.HorseStats;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.CustomItem;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.core.services.HorseService;

import java.util.EnumSet;

@Component
public class SaddleItem extends CustomItem implements Listener {

    @Autowired
    private HorseService horseService;

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
        NONE(0, "Nič"),
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

    @Override
    public ItemStack getItem() {
        int color = Random.nextInt(HorseColor.values().length-1);
        int style = Random.nextInt(HorseStyle.values().length-1);
        return new ItemBuilder(Material.SADDLE)
                .setName("§bKôň")

                .addLore("§f" + StringUtils.capitalise(Colors.findById(color).getName().replace("_", " ") + "") + " kôň")
                .addLore("§7Štýl: §f" + StringUtils.capitalise(Style.findById(style).getName().toLowerCase()))
                .setModelId(getModelID())

                // Jump
                // 0.5 - tier 1
                // 0.6 - tier 2
                // 0.7 - tier 3
                // 0.8 - tier 4
                // 0.9 - tier 5
                .addLore("", "§7Jump Tier: §f1")
                .setNbt_Int(HorseStats.JUMP.getStatName(), 1)

                // Speed
                // 0.1 - tier 1
                // 0.2 - tier 2
                // 0.3 - tier 3
                // 0.4 - tier 4
                // 0.5 - tier 5
                .addLore("§7Speed Tier: §f1")
                .setNbt_Int(HorseStats.SPEED.getStatName(), 1)

                // Health
                // 10 - tier 1
                // 20 - tier 2
                // 30 - tier 3
                // 40 - tier 4
                // 50 - tier 5
                .addLore("§7Health Tier: §f1")
                .setNbt_Int(HorseStats.HEALTH.getStatName(), 1)

                .setNbt_Int(HorseStats.COLOR.getStatName(), color)
                .setNbt_Int(HorseStats.STYLE.getStatName(), Random.nextInt(3))
                .setNbt_Int(HorseStats.ARMOR.getStatName(), -1)
                .setNbt_Int(HorseStats.ARMOR_COLOR.getStatName(), -1)

                .setNbt_Bool(HorseStats.SADDLE.getStatName(), true)
                .setNbt_Bool(HorseStats.SPAWNED.getStatName(), false)
                .addLore("", "§aKlikni pre spawnutie!")
                .build();
    }

    @Override
    public int getModelID() {
        return 2;
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {

    }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) { }

    @Override
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        Player player = event.getPlayer();

        if(player.isInsideVehicle())
            return;

        if(Nbt.getNbt_Bool(itemStack, HorseStats.SPAWNED.getStatName(), false))
            horseService.removePlayer(player);

        Location location = event.getPlayer().getLocation();
        location.getWorld().spawn(location, Horse.class, (horse) -> {
            if(!horseService.addPlayer(player, horse))
                horseService.removePlayer(player);

            horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HorseService.HORSE_MAX_HEALTH);
            horse.setTamed(true);
            horse.setOwner(player);
            horse.setAdult();
            horse.addPassenger(player);
            horse.setAI(true);

            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
            EnumSet<HorseStats> horseStats = EnumSet.of(
                    HorseStats.JUMP, HorseStats.SPEED, HorseStats.HEALTH,
                    HorseStats.COLOR, HorseStats.STYLE, HorseStats.ARMOR,
                    HorseStats.ARMOR_COLOR);

            for(HorseStats horseStat : horseStats)
                horseService.applyStat(horse, itemStack, horseStat);

            ItemBuilder itemBuilder = new ItemBuilder(itemStack);
            itemBuilder.setNbt_Bool(HorseStats.SPAWNED.getStatName(), true);
            player.getInventory().setItemInMainHand(itemBuilder.build());
        });
    }


    @Override
    protected void onPlayerDamageWithItem(WLPlayerDamageEvent event) { }

    @Override
    protected void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event) {
        if(!(event.getRightClicked() instanceof Horse))
            return;
        Player player = event.getPlayer();
        Horse horse  = horseService.getPlayerHorse(player);
        if(horse == null)
            return;

        if(horse.getEntityId() == event.getRightClicked().getEntityId())
            horseService.removePlayer(player);
    }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) {}
}