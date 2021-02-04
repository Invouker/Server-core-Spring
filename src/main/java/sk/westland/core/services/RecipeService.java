package sk.westland.core.services;



import net.minecraft.server.v1_16_R3.IRecipe;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerJoinEvent;
import sk.westland.core.items.CraftingRecipe;
import sk.westland.core.entity.player.WLPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService implements Listener {

    @Autowired
    private PlayerService playerService;

    private static Map<String, CraftingRecipe> craftingRecipes = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPluginInit(PluginEnableEvent event) {
       Bukkit.getScheduler().runTaskLater(event.getWestLand(), ()->{
           if (craftingRecipes == null)
               return;

           craftingRecipes.forEach((name, craftingRecipe) -> craftingRecipe.register());
       }, 40l);
    }

    public static Map<String, CraftingRecipe> getCraftingRecipes() {
        return craftingRecipes;
    }

    public RecipeChoice item(ItemStack itemStack) {
        return new RecipeChoice.ExactChoice(itemStack);
    }

    public RecipeChoice items(List<ItemStack> itemStacks) {
        return new RecipeChoice.ExactChoice(itemStacks);
    }

    public RecipeChoice items(ItemStack... itemStacks) {
        return new RecipeChoice.ExactChoice(itemStacks);
    }

    // Register before the server start...
    public void registerRecipe(CraftingRecipe craftingRecipe) {
        craftingRecipes.putIfAbsent(craftingRecipe.getNamespacedKey().getKey(), craftingRecipe);
    }

    private void clearRecipes(Player player) {
        for (Map.Entry<Recipes<?>, Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>>> entry : ((CraftServer) Bukkit.getServer()).getServer().getCraftingManager().recipes.entrySet()) {
            for (IRecipe<?> iRecipe : entry.getValue().values()) {
                Recipe recipe = iRecipe.toBukkitRecipe();

                if (recipe instanceof Keyed) {
                    if (((Keyed) recipe).getKey().getNamespace().equalsIgnoreCase("minecraft"))
                        continue;
                    player.undiscoverRecipe(((Keyed) recipe).getKey());
                }
            }
        }
    }

    private void discoverRecipe(Player player) {
        for (Map.Entry<Recipes<?>, Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>>> entry : ((CraftServer) Bukkit.getServer()).getServer().getCraftingManager().recipes.entrySet()) {
            for (IRecipe<?> iRecipe : entry.getValue().values()) {
                Recipe recipe = iRecipe.toBukkitRecipe();

                if (recipe instanceof Keyed) {

                    if (craftingRecipes == null)
                        continue;

                    CraftingRecipe unlockingRecipe = craftingRecipes.get(((Keyed) recipe).getKey().getKey());
                    if (unlockingRecipe == null || unlockingRecipe.getNamespacedKey() == null)
                        continue;

                    player.sendMessage("unlocked recipe: " + unlockingRecipe.getNamespacedKey().getKey());
                    player.discoverRecipe(((Keyed) recipe).getKey());
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerJoin(PlayerJoinEvent event) {
        clearRecipes(event.getPlayer());
    }


    @EventHandler
    private void onPlayerRoleSelect(WLPlayerJoinEvent event) {
        WLPlayer wlPlayer = event.getWlPlayer();
        clearRecipes(wlPlayer.getPlayer());

        if (wlPlayer.getUserData().getCraftingRecipe() == null)
            return;

        wlPlayer.getUserData().getCraftingRecipe().stream().forEach((nameKey) -> {
            RecipeService.getCraftingRecipes().forEach((name, craftingRecipe) -> {
                if (craftingRecipe.getNamespacedKey().getKey().equals(nameKey)) {
                    wlPlayer.getPlayer().discoverRecipe(craftingRecipe.getNamespacedKey());
                }
            });
        });
    }
}
/*
    private void onPlayerRoleLeft(PlayerLeftRoleEvent event) {
        Player player = event.getBukkitPlayer();
        WLPlayer WLPlayer = event.getPlayer();
        clearRecipes(player);

        if (!WLPlayer.isAdmin()) {
            WLPlayer.setMetaXPLevel(player.getLevel());
            WLPlayer.setMetaEXP(player.getExp());
        }

        player.setExp(0);
        player.setLevel(0);
    }

         /* Bukkit.recipeIterator().forEachRemaining((recipe -> {
            Bukkit.getRecipesFor(recipe.getResult()).forEach((b)-> {
                if(b instanceof ShapelessRecipe) {
                    ShapelessRecipe c = (ShapelessRecipe) b;
                    Log.info("Shapless: " + c.getKey().getNamespace() + ":" + c.getKey().getKey());
                }

                if(b instanceof ShapedRecipe) {
                    ShapedRecipe c = (ShapedRecipe) b;
                    Log.info("Shapless: " + c.getKey().getNamespace() + ":" + c.getKey().getKey());
                }
                Log.info("E: " + b.getResult().toString());
            });
        }));*/

/*
        ((CraftServer) Bukkit.getServer()).getServer().getCraftingManager().recipes.forEach((a, b) -> {
            Log.info("A: " + a);
            b.forEach((c, d) -> {
                Log.info("C: " + c.getNamespace()+":"+c.getKey(), "D: " +d.getKey() + ", " + d.getRecipeSerializer().toString() + ", " + d.getResult().toString());
            });
        });*/
    //discoverRecipe(event.getBukkitPlayer(), event.getType());


