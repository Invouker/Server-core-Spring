package sk.westland.world.items.blocks;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;

@Component
public class BlockUpgradeItem extends CustomItem implements Craftable, Listener {

    @Override
    public NamespacedKey getNamespacedKey(Plugin plugin) {
        return new NamespacedKey(plugin, "blockPlacer");
    }

    @Override
    public CraftingRecipe getCraftingRecipe(Plugin plugin) {
        return new CraftingRecipe(getNamespacedKey(plugin), RecipeType.Block, getItem())
                .shape(" S ", " S ", " S ")
                .setIngredient('S', Material.STONE);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.DISPENSER)
                .setName("§fBlock placer")
                .setModelId(getModelID())
                .setNbt_Int(WestLand.CUSTOM_BLOCK_NBT, BlockType.BLOCK_PLACER.getId())
                .setLore("", "§7Automatický pokladá bloky", "§7ktoré sa do neho dajú!", "").build();
    }

    @Override
    public int getModelID() {
        return 110;
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        getCraftingRecipe(event.getWestLand()).register();
    }

    @Override
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) { }

    @Override
    protected void onPlayerDamageWithItem(WLPlayerDamageEvent event) { }

    @Override
    protected void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event) { }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) { }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) { }
}