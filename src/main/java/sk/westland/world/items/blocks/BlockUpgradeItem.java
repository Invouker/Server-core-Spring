package sk.westland.world.items.blocks;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;

@Component
public class BlockUpgradeItem extends CustomItem implements Craftable, Listener {

    @Override
    public CraftingRecipe getCraftingRecipe() {

        return new CraftingRecipe(itemID(), RecipeType.Block, getItem())
                .shape("SAS", "ASA", "SAS")
                .setIngredient('A', Material.AIR)
                .setIngredient('S', Material.STONE);
    }

    @Override
    public ItemStack getItem() {
        return customItemStack = new ItemBuilder(Material.DISPENSER)
                .setName("§fBlock placer")
                .setModelId(getModelID())
                .setCustomItem(this)
                .setNbt_Int(WestLand.CUSTOM_BLOCK_NBT, BlockType.BLOCK_PLACER.getId())
                .setLore("", "§7Automatický pokladá bloky", "§7ktoré sa do neho dajú!", "").build();
    }

    @Override
    public int getModelID() {
        return 110;
    }

    @Override
    public String itemID() {
        return "blockUpgradeItem";
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        recipeService.registerRecipe(getCraftingRecipe());
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