package sk.westland.core.services;


import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.springframework.beans.factory.annotation.Autowired;
import sk.westland.core.WestLand;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerJoinEvent;
import sk.westland.core.items.CraftingRecipe;
import sk.westland.world.items.Materials;

import java.util.*;

public class RecipeService implements Listener, BeanWire {

    @Autowired
    private PlayerService playerService;

    private static final Set<CraftingRecipe> craftingRecipes = new HashSet<>();
    private final List<FurnaceRecipe> furnaceRecipes = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPluginInit(PluginEnableEvent event) {
       Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), () -> {
           Bukkit.getLogger().info("[WestLand] Registring WestLand recipes!");

           removeRecipeByType(Material.HOPPER);

           if (!craftingRecipes.isEmpty())
               craftingRecipes.forEach(CraftingRecipe::register);

           if (!furnaceRecipes.isEmpty())
               furnaceRecipes.forEach((furnaceRecipe) ->  {
                   NamespacedKey namespacedKey = furnaceRecipe.getKey();
                   if(Bukkit.getRecipe(namespacedKey) != null) {
                        Bukkit.removeRecipe(namespacedKey);
                   }

                   Bukkit.addRecipe(furnaceRecipe);
               });
       }, 20*2L);
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
        craftingRecipes.add(craftingRecipe);
    }

    private void clearRecipes(Player player) {
        for (Map.Entry<Recipes<?>, Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>>> entry :
                ((CraftServer) Bukkit.getServer()).getServer().getCraftingManager().c.entrySet()) {
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

    private void removeRecipeByType(Material material) {
        Iterator<Recipe> iter = Bukkit.getServer().recipeIterator();
        while (iter.hasNext()) {
            Recipe r = iter.next();
            // May not be safe to depend on == here for recipe comparison
            // Probably safer to compare the recipe result (an ItemStack)

            if (r.getResult().getType() == material) {
                iter.remove();
            }
        }
    }

    private void discoverRecipe(Player player) {
        for (Map.Entry<Recipes<?>, Object2ObjectLinkedOpenHashMap<MinecraftKey, IRecipe<?>>> entry :
                ((CraftServer) Bukkit.getServer()).getServer().getCraftingManager().c.entrySet()) {
            for (IRecipe<?> iRecipe : entry.getValue().values()) {
                Recipe recipe = iRecipe.toBukkitRecipe();

                if (recipe instanceof Keyed) {

                    if (craftingRecipes == null)
                        continue;


                    CraftingRecipe unlockingRecipe = null;//craftingRecipes.get(((Keyed) recipe).getKey().getKey());
                    if (unlockingRecipe == null || unlockingRecipe.getNamespacedKey() == null)
                        continue;

                    player.sendMessage("unlocked recipe: " + unlockingRecipe.getNamespacedKey().getKey());
                    player.discoverRecipe(((Keyed) recipe).getKey());
                }
            }
        }
    }

    @EventHandler
    private void onWLPlayerJoin(WLPlayerJoinEvent event) {
        WLPlayer wlPlayer = event.getWlPlayer();
        clearRecipes(wlPlayer.getPlayer());

        if (wlPlayer.getCraftingRecipes() == null)
            return;

        wlPlayer.getCraftingRecipes().forEach((nameKey) ->
                craftingRecipes.forEach((craftingRecipe) -> {
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


