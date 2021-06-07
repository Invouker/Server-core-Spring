package sk.westland.world.items.tools.talismans;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.Craftable;
import sk.westland.core.items.CraftingRecipe;
import sk.westland.core.items.CustomItem;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.utils.ChatInfo;

@Component
public class FortuneTalisman extends CustomItem implements Craftable {

    @Override
    public CraftingRecipe getCraftingRecipe() {
        return null;
    }

    @Override
    public ItemStack getItem() {
        return
                new ItemBuilder(Material.PAPER, "Fortune Talisman")
                        .setCustomItem(this)
                        .build();
    }

    @Override
    public int getModelID() {
        return 0;
    }

    @Override
    public String itemID() {
        return "fortune_talisman";
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {

    }

    @Override
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) {

    }

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

    @Override
    protected void onPlayerBlockBreakWithAllItems(BlockBreakEvent event) {
        super.onPlayerBlockBreakWithAllItems(event);

        if(!event.getPlayer().getInventory().contains(getItem()))
            return;

        ChatInfo.SUCCESS.send(event.getPlayer(), "Máš talizman, šanca na drop je vyššia!");
    }
}
