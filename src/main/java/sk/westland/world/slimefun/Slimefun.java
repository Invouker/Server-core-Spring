package sk.westland.world.slimefun;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.exceptions.IdConflictException;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.ServerDisableEvent;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.services.SlimefunService;
import sk.westland.world.slimefun.blocks.BlockBreaker;
import sk.westland.world.slimefun.blocks.MobGrinder;
import sk.westland.world.slimefun.blocks.MobTeleporter;

@Component
public class Slimefun implements Listener {

    @Autowired
    private SlimefunService slimefunService;

    private SlimefunAddon slimefunAddon;

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPluginEnable(PluginEnableEvent event) {
        slimefunAddon = slimefunService.getSlimefunAddon();

        NamespacedKey namespacedKey = new NamespacedKey(slimefunService.getInstance(), "westland_category");
        ItemStack customItem = new ItemBuilder(Material.DISPENSER, "§eWestLand")
                .setLore("",
                        "§7Westland Category",
                        "",
                        "§7Developed by XpresS",
                        "§7Bugs report to: Staff-Team",
                        "")
                .build();

        ItemGroup category = new ItemGroup(namespacedKey, customItem);
        if(!category.isRegistered())
            category.register(slimefunAddon);


        System.out.println("ItemGroup registred: " + category.getUnlocalizedName());

        slimefunService.setCategory(category);
        BlockLocator blockLocator = new BlockLocator(slimefunService);
        MobTeleporter mobTeleporter = new MobTeleporter(slimefunService);
        MobGrinder mobGrinder = new MobGrinder(slimefunService);
        Research research = new Research(new NamespacedKey(WestLand.getInstance(), "MOB_TELEPORTER"), 127, "Mob Teleporter", 45);

        BlockBreaker blockBreaker = new BlockBreaker(category);
        blockBreaker.setHidden(true);

        try {
            blockBreaker.register(slimefunAddon);
            mobGrinder.register(slimefunAddon);
            blockLocator.register(slimefunAddon);
            mobTeleporter.register(slimefunAddon);
        }catch (IdConflictException ex) {
            System.out.println("[WestLand] Slimefun items/blocks are already registred, please restart a server!");
            return;
        }
        research.addItems(blockLocator, mobTeleporter);

        //category.add(blockBreaker);
        category.add(blockLocator);
        category.add(mobTeleporter);
        category.add(mobGrinder);
    }


    @EventHandler(ignoreCancelled = true)
    public void onServerDisable(ServerDisableEvent event) {
        System.out.println("disable event");
    }


}
