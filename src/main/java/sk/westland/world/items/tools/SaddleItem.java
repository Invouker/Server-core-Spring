package sk.westland.world.items.tools;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
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
public class SaddleItem extends CustomItem implements Listener  {

    @Autowired
    private HorseService horseService;

    @Override
    public ItemStack getItem() {

        return customItemStack = new ItemBuilder(Material.SADDLE)
                .setName(ChatColor.of("#99ddff") + StringUtils.capitalise(HorseService.Colors.BLACK.getName().replace("_", " ") + "") + " kôň")
                .addLore("§7Štýle: §f" + StringUtils.capitalise(HorseService.Style.NONE.getName().toLowerCase()))
                //.addLore("§7Typ: §f" + HorseService.HorseType.HORSE.getTypeName())
                .setNbt_Int(HorseStats.TYPE.getStatName(), HorseService.HorseType.HORSE.getId())

                .addLore("", "§7Jump Tier: §f1")
                .setNbt_Int(HorseStats.JUMP.getStatName(), 1)

                .addLore("§7Speed Tier: §f1")
                .setNbt_Int(HorseStats.SPEED.getStatName(), 1)

                .addLore("§7Health Tier: §f1")
                .setNbt_Int(HorseStats.HEALTH.getStatName(), 1)

                .setNbt_Int(HorseStats.COLOR.getStatName(), HorseService.Colors.BLACK.getId())
                .setNbt_Int(HorseStats.STYLE.getStatName(), HorseService.Style.NONE.getId())
                .setNbt_Int(HorseStats.ARMOR.getStatName(), -1)
                .setNbt_Int(HorseStats.ARMOR_COLOR.getStatName(), -1)

                .setNbt_Bool(HorseStats.SADDLE.getStatName(), true)
                .setNbt_Bool(HorseStats.SPAWNED.getStatName(), false)
                .addLore("", "§aKlikni pre spawnutie!")
                .setCustomItem(this)
                .setModelId(getModelID())
                .build();
    }

    @Override
    public int getModelID() {
        return 2;
    }

    @Override
    public String itemID() {
        return "saddle";
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

        int type = Nbt.getNbt_Int(itemStack, HorseStats.TYPE.getStatName(), -1);
        System.out.println("Type: " + type);
        Class<? extends AbstractHorse> clazz = Horse.class;
        if(type == 2) { // Zombie
            clazz = ZombieHorse.class;
        }
        if(type == 3) { // Skeleton
            clazz = SkeletonHorse.class;
        }

        location.getWorld().spawn(location, clazz, (horse) -> {
            if(!horseService.addPlayer(player, horse))
                horseService.removePlayer(player);

            horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(HorseService.HORSE_MAX_HEALTH);
            horse.setTamed(true);
            horse.setOwner(player);
            horse.setAdult();
            horse.addPassenger(player);
            horse.setAI(true);

            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
            if(horse instanceof Horse) {
                EnumSet<HorseStats> horseStats = EnumSet.of(
                        HorseStats.JUMP, HorseStats.SPEED, HorseStats.HEALTH,
                        HorseStats.COLOR, HorseStats.STYLE, HorseStats.ARMOR,
                        HorseStats.ARMOR_COLOR);

                for (HorseStats horseStat : horseStats)
                    horseService.applyStat(horse, itemStack, horseStat);
            }
            if(horse instanceof SkeletonHorse || horse instanceof ZombieHorse) {
                EnumSet<HorseStats> horseStats = EnumSet.of(
                        HorseStats.JUMP, HorseStats.SPEED, HorseStats.HEALTH);

                for (HorseStats horseStat : horseStats)
                    horseService.applyStat(horse, itemStack, horseStat);
            }

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
        AbstractHorse horse  = horseService.getPlayerHorse(player);
        if(horse == null)
            return;

        if(horse.getEntityId() == event.getRightClicked().getEntityId())
            horseService.removePlayer(player);
    }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) {}

}