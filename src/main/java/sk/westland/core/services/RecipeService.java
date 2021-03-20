package sk.westland.core.services;


import net.minecraft.server.v1_16_R3.IRecipe;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.WestLand;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerJoinEvent;
import sk.westland.core.items.CraftingRecipe;
import sk.westland.world.items.Materials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService implements Listener {

    @Autowired
    private PlayerService playerService;

    private Map<String, CraftingRecipe> craftingRecipes = new HashMap<>();
    private List<FurnaceRecipe> furnaceRecipes = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPluginInit(PluginEnableEvent event) {
       Bukkit.getScheduler().runTaskLater(event.getWestLand(), ()->{
           if (!(craftingRecipes == null && craftingRecipes.isEmpty()))
               craftingRecipes.forEach((name, craftingRecipe) -> craftingRecipe.register());

           if (!(furnaceRecipes == null && furnaceRecipes.isEmpty()))
               furnaceRecipes.forEach((furnaceRecipe) ->  {
                   NamespacedKey namespacedKey = furnaceRecipe.getKey();
                   if(Bukkit.getRecipe(namespacedKey) != null) {
                        Bukkit.removeRecipe(namespacedKey);
                   }

                   Bukkit.addRecipe(furnaceRecipe);
               }

              );
       }, 40l);
    }

    public Map<String, CraftingRecipe> getCraftingRecipes() {
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

        if (wlPlayer.getCraftingRecipes() == null)
            return;

        wlPlayer.getCraftingRecipes().forEach((nameKey) ->
                getCraftingRecipes().forEach((name, craftingRecipe) -> {
                    if (craftingRecipe.getNamespacedKey().getKey().equals(nameKey))
                        wlPlayer.getPlayer().discoverRecipe(craftingRecipe.getNamespacedKey());
        }));
    }

    //Furnace recipes

    public void addFurnaceRecipe(String key, Materials.Resources input, Materials.Resources output, float exp, int cookingTime) {
        this.addFurnaceRecipe(key, input.getItem(),  output.getItem(), exp, cookingTime);
    }

    public void addFurnaceRecipe(String key, ItemStack input, ItemStack output, float exp, int cookingTime) {
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(new NamespacedKey(WestLand.getInstance(), key), input, item(output), exp, cookingTime*20);
        furnaceRecipes.add(furnaceRecipe);
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


