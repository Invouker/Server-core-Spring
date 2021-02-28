package sk.westland.core.items;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Autowired;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.services.ItemInteractionService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.RecipeService;

import java.util.Random;

public abstract class CustomResourceItem  {

    public static final java.util.Random Random = new Random();

    @Autowired
    protected PlayerService playerService;

    @Autowired
    protected ItemInteractionService itemInteractionService;

    @Autowired
    protected RecipeService recipeService;

    public abstract ItemStack getItem();

    public abstract int getModelID();

    protected abstract void onPluginEnable(PluginEnableEvent event);

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPluginInit(PluginEnableEvent event) {
        this.onPluginEnable(event);
    }

}
