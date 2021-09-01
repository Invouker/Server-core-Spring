package sk.westland.world.slimefun;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.services.SlimefunService;


public class HotWand extends SlimefunItem implements NotPlaceable {

    public HotWand(SlimefunService slimefunService) {
        super(slimefunService.getCategory(), new SlimefunItemStack("HOT_WAND",
                        new ItemBuilder(Material.STICK)
                                .setName("§8Horúca palička").setLore("", "§8Hot wand", "§7This thing doesn't","§7 make sense", "").build()),
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

                new ItemStack(Material.DIAMOND),    null,   new ItemStack(Material.DIAMOND),
                                    null,   SlimefunItems.CARBONADO,    null,
                new ItemStack(Material.DIAMOND),    null,   new ItemStack(Material.DIAMOND)

                });

    }

    @Override
    public void preRegister() {
        ItemUseHandler itemUseHandler = this::onItemUseRightClick;
        addItemHandler(itemUseHandler);
    }


    private void onItemUseRightClick(@NotNull PlayerRightClickEvent event) {
        event.getPlayer().setFireTicks(0);
        event.getPlayer().setVisualFire(false);
    }

}
