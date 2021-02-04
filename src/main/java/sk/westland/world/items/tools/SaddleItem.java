package sk.westland.world.items.tools;

import net.minecraft.server.v1_16_R3.HorseColor;
import net.minecraft.server.v1_16_R3.HorseStyle;
import org.bukkit.*;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.enums.HorseStats;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;
import sk.westland.core.services.HorseService;

@Component
public class SaddleItem extends CustomItem implements Listener, Craftable {

    @Autowired
    private HorseService horseService;

    @Override
    public NamespacedKey getNamespacedKey(Plugin plugin) {
        return new NamespacedKey(plugin, "saddle");
    }

    @Override
    public CraftingRecipe getCraftingRecipe(Plugin plugin) {
        return new CraftingRecipe(getNamespacedKey(plugin), RecipeType.Block, getItem())
                .shape(" S ", "SSS", " S ")
                .setIngredient('S', Material.STONE);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.SADDLE)
                .setName("§bSedlo ty kokot")
                .setModelId(getModelID())
                .setNbt_Bool(HorseStats.SADDLE.getStatName(), true)
                .setNbt_Int(HorseStats.HORSE_ID.getStatName(), -1)
                .setNbt_Bool(HorseStats.SPAWNED.getStatName(), false)
                .setLore("", "§7Spawne ti kona !", "").build();
    }

    @Override
    public int getModelID() {
        return 2;
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        getCraftingRecipe(event.getWestLand()).register();
    }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) { }

    @Override
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        ItemBuilder itemBuilder = new ItemBuilder(itemStack);
        Player player = event.getPlayer();

        if(Nbt.getNbt_Bool(itemStack, HorseStats.SPAWNED.getStatName(), false))
            horseService.removePlayer(player);

        Location location = event.getPlayer().getLocation();
        location.getWorld().spawn(location, Horse.class, (horse) -> {
            if(!horseService.addPlayer(player, horse))
                horseService.removePlayer(player);

            horse.setTamed(true);
            horse.setOwner(player);
            horse.setAdult();
            horse.addPassenger(player);

            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));

            for(HorseStats horseStat : HorseStats.values())
                horseService.applyStat(horse, itemStack, horseStat);

            int id = horse.getEntityId();
            itemBuilder.setNbt_Int(HorseStats.HORSE_ID.getStatName(), id);
            itemBuilder.setNbt_Bool(HorseStats.SPAWNED.getStatName(), true);
            player.getInventory().setItemInMainHand(itemBuilder.build());
        });
    }


    @Override
    protected void onPlayerDamageWithItem(WLPlayerDamageEvent event) { }

    @Override
    protected void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event) { }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) {}
}