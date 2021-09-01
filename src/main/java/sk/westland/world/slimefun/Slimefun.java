package sk.westland.world.slimefun;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.ServerDisableEvent;
import sk.westland.core.services.SlimefunService;
import sk.westland.world.slimefun.blocks.BlockBreaker;

import java.util.Arrays;

@Component
public class Slimefun implements Listener {

    @Autowired
    private SlimefunService slimefunService;

    private SlimefunAddon slimefunAddon;

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPluginEnable(PluginEnableEvent event) {
        slimefunAddon = slimefunService.getSlimefunAddon();

        NamespacedKey namespacedKey = new NamespacedKey(slimefunService.getInstance(), "westland_category");
        CustomItem customItem = new CustomItem(Material.DISPENSER, (meta) -> {
            meta.setDisplayName("WestLand");
            meta.setLore(Arrays.asList("", "§rWestLand category", ""));
        });

        Category category = new Category(namespacedKey, customItem);
        if(!category.isRegistered())
            category.register(slimefunAddon);


        System.out.println("Category registred: " + category.getUnlocalizedName());

        slimefunService.setCategory(category);

        BlockBreaker blockBreaker = new BlockBreaker(category);
        blockBreaker.setHidden(false);
        if(SlimefunItem.getByID(blockBreaker.getId()) == null)
            blockBreaker.register(slimefunAddon);

        HotWand hotWand = new HotWand(slimefunService);
        hotWand.setHidden(false);
        if(SlimefunItem.getByID(hotWand.getId()) == null)
            hotWand.register(slimefunAddon);

        category.add(hotWand);
        category.add(blockBreaker);
        System.out.println("From category: ");
        category.getItems().forEach((a) -> System.out.println(a.getItem().getI18NDisplayName()));


    }


    @EventHandler(ignoreCancelled = true)
    public void onServerDisable(ServerDisableEvent event) {
        System.out.println("disable event");
    }


}
