package sk.westland.core.items;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Autowired;
import sk.westland.core.database.data.BlockDataRepository;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.services.BlockService;
import sk.westland.core.services.ItemInteractionService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.RecipeService;

import java.util.Random;

public abstract class CustomItem {

    public static final java.util.Random Random = new Random();
    private String localizedName;
    @Autowired
    protected PlayerService playerService;

    @Autowired
    protected ItemInteractionService itemInteractionService;

    @Autowired
    protected RecipeService recipeService;

    @Autowired
    protected BlockService blockService;

    @Autowired
    protected BlockDataRepository blockDataRepository;

    public abstract ItemStack getItem();
    public abstract int getModelID();

    protected abstract void onPluginEnable(PluginEnableEvent event);
    protected abstract void onPlayerInteractWithItem(PlayerInteractEvent event);
    protected abstract void onPlayerDamageWithItem(WLPlayerDamageEvent event);
    protected abstract void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event);
    protected abstract void onPlayerBlockPlace(BlockPlaceEvent event);
    protected abstract void onPlayerBlockBreak(BlockBreakEvent event);

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPluginInit(PluginEnableEvent event) {
        this.onPluginEnable(event);

        localizedName = ChatColor.stripColor(getItem().getItemMeta().getDisplayName());
        itemInteractionService.registerItem(localizedName,
                new InteractionItem(getModelID(), getItem(),
                        this::onPlayerInteractWithItem,
                        this::onPlayerDamageWithItem,
                        this::onPlayerInteractAtEntityWithItem,
                        this::onPlayerBlockPlace,
                        this::onPlayerBlockBreak
                ));
    }

    @Override
    public String toString() {
        return "CustomItem{" +
                "localizedName='" + localizedName + '\'' +
                "itemStack='" + getItem().toString() + '\'' +
                "modelID='" + getModelID() + '\'' +
                '}';
    }
}
