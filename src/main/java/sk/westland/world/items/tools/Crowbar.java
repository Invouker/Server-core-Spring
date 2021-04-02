package sk.westland.world.items.tools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;
import sk.westland.world.items.Materials;

@Component
public class Crowbar extends CustomItem implements Craftable, Listener {

    @Override
    public NamespacedKey getNamespacedKey(Plugin plugin) {
        return new NamespacedKey(plugin, "crowbar");
    }

    @Override
    public CraftingRecipe getCraftingRecipe(Plugin plugin) {
        return new CraftingRecipe(getNamespacedKey(plugin), RecipeType.Item, getItem())
                .shape("ABB", "AAB", "BBA")
                .setIngredient('B', recipeService.item(Materials.Resources.COPPER_DUST.getItem()))
                .setIngredient('A', recipeService.item(Materials.Resources.IRON_ROD.getItem()));
    }

    @Override
    public ItemStack getItem() {
        return customItemStack = new ItemBuilder(Material.STICK)
                .setModelId(getModelID())
                .setName("Crowbar")
                .setCustomItem(this)
                .addEnchant(Enchantment.DAMAGE_ALL, 3)
                .build();
    }

    @Override
    public int getModelID() {
        return 0;
    }

    @Override
    public String itemID() {
        return "crowbar";
    }


    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        recipeService.registerRecipe(getCraftingRecipe(WestLand.getInstance()));
    }

    @Override
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) {
        event.getPlayer().sendMessage("INTERAKCIA? " + event.toString());
    }

    @Override
    protected void onPlayerDamageWithItem(WLPlayerDamageEvent event) {
        event.getPlayer().sendMessage("DAMAGE? " + event.toString());
        event.setDamage(3);
    }

    @Override
    protected void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event) {
        event.getPlayer().sendMessage("INTERAKCIA S ENTITOU? " + event.toString());
    }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) { }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) {

    }
}
