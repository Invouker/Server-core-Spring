package sk.westland.world.items.blocks;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.blocks.BlockLevel;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;
import sk.westland.world.blocks.type.BlockBreaker;

@Component
public class BlockBreakerItem extends CustomItem implements Craftable, Listener {

    @Override
    public NamespacedKey getNamespacedKey(Plugin plugin) {
        return new NamespacedKey(plugin, "blockBreaker");
    }

    @Override
    public CraftingRecipe getCraftingRecipe(Plugin plugin) {
        return new CraftingRecipe(getNamespacedKey(plugin), RecipeType.Block, getItem())
                .shape("SSS", "   ", "SSS")
                .setIngredient('S', Material.STONE);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(BlockType.BLOCK_BREAKER.getMaterial())
                .setName("§fBlock breaker")
                .setModelId(getModelID())
                .setNbt_Int(WestLand.CUSTOM_BLOCK_NBT, BlockType.BLOCK_BREAKER.getId())
                .setLore("", "§7Automatický ničí bloky", "").build();
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
    protected void onPlayerBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        boolean place = blockService.registerNewBlock(new BlockBreaker(player.getName(), player.getUniqueId(), block.getLocation(), BlockLevel.UNCOMMON, blockService));
        event.setBuild(place);
        event.setCancelled(!place);
    }

    @Override
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) { }

    @Override
    protected void onPlayerDamageWithItem(WLPlayerDamageEvent event) { }

    @Override
    protected void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event) { }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) { }
}
