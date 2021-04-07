package sk.westland.world.items.tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;
import sk.westland.world.items.Materials;

@Component
public class BetterPickaxe extends CustomItem implements Listener, Craftable {

    @Override
    public CraftingRecipe getCraftingRecipe() {
        return new CraftingRecipe(itemID(), RecipeType.Item, getItem(), CraftingType.ShapedRecipe)
               .shape( "ISI",
                       "ARA",
                       "ARA")
               .setIngredient('S', Material.NETHER_STAR)
               .setIngredient('A', Material.AIR)
               .setIngredient('I', Material.NETHERITE_INGOT)
               .setIngredient('R', recipeService.item(Materials.Resources.IRON_ROD.getItem()));
    }

    @Override
    public ItemStack getItem() {
        return customItemStack = new ItemBuilder(Material.NETHERITE_PICKAXE)
                .setName("§bBetter Pickaxe")
                .setModelId(getModelID())
                .setCustomItem(this)
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
    public String itemID() {
        return "betterPickaxe";
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        recipeService.registerRecipe(getCraftingRecipe());
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
        Location location = event.getBlock().getLocation();
        World world = location.getWorld();

        if(world == null)
            return;

        switch(event.getBlock().getType()) {
            case IRON_ORE: {
                world.dropItemNaturally(location, Materials.Resources.COPPER_DUST.getItem());
                break;
            }

            case REDSTONE_ORE: {
                break;
            }
        }
    }
}
