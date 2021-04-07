package sk.westland.world.items.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.blocks.BlockLevel;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;
import sk.westland.world.blocks.type.WorthChest;

@Component
public class WorthChestItem extends CustomItem implements Craftable, Listener {

    @Override
    public CraftingRecipe getCraftingRecipe() {
        return new CraftingRecipe(itemID(), RecipeType.Block, getItem())
                .shape("SSS", "AAA", "SSS")
                .setIngredient('A', Material.AIR)
                .setIngredient('S', Material.ANDESITE);
    }

    @Override
    public ItemStack getItem() {
        return customItemStack = new ItemBuilder(BlockType.WORTH_CHEST.getMaterial())
                .setName("§8Worth chest")
                .setModelId(getModelID())
                .setCustomItem(this)
                .setNbt_Int(WestLand.CUSTOM_BLOCK_NBT, BlockType.WORTH_CHEST.getId())
                .setLore("", "§7Automatický predáva blocky", "a itemy ktoré do nej vložité.", "").build();
    }

    @Override
    public int getModelID() {
        return 110;
    }

    @Override
    public String itemID() {
        return "worthChestItem";
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        recipeService.registerRecipe(getCraftingRecipe());
    }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        boolean place = blockService.registerNewBlock(new WorthChest(player.getName(), player.getUniqueId(), block.getLocation(), BlockLevel.UNCOMMON, blockService));
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