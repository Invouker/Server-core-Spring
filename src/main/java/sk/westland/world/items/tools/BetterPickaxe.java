package sk.westland.world.items.tools;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;
import sk.westland.world.items.Materials;

@Component
public class BetterPickaxe extends CustomItem implements Listener, Craftable {

    @Override
    public NamespacedKey getNamespacedKey(Plugin plugin) {
        return new NamespacedKey(plugin, "betterPickaxe");
    }

    @Override
    public CraftingRecipe getCraftingRecipe(Plugin plugin) {
        return new CraftingRecipe(getNamespacedKey(plugin), RecipeType.Item, getItem(), CraftingType.ShapedRecipe)
                .shape( "ISI",
                        " R ",
                        " R ")
                .setIngredient('S', Material.NETHER_STAR)
                .setIngredient('I', Material.NETHERITE_INGOT)
                .setIngredient('R', recipeService.item(Materials.Resources.IRON_ROD.getItem()));
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.NETHERITE_PICKAXE)
                .setName("§bBetter Pickaxe")
                .setModelId(getModelID())

                .setLore("",
                        "§7Na ťaženie lepších",
                        "§7rúd z orečok",
                        "").build();
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
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) { }

    @Override
    protected void onPlayerDamageWithItem(WLPlayerDamageEvent event) {
    }

    @Override
    protected void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event) {
    }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) {
    }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) {

    }
}
