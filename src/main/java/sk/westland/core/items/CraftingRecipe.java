package sk.westland.core.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import sk.westland.core.WestLand;

import java.util.Objects;

public class CraftingRecipe {

    private final NamespacedKey namespacedKey;
    private final RecipeType recipeType;

    private ShapedRecipe shapedRecipe = null;
    private ShapelessRecipe shapelessRecipe = null;
    private CraftingType craftingType;

    public CraftingRecipe(String key, RecipeType recipeType, ItemStack result, CraftingType craftingType) {
       this(key, recipeType, result);
       this.craftingType = craftingType;
    }

    public CraftingRecipe(String key, RecipeType recipeType, CustomResourceItem customResourceItem) {
        this(key, recipeType, customResourceItem.getItem());
    }

    public CraftingRecipe(String key, RecipeType recipeType, ItemStack result) {
        namespacedKey = new NamespacedKey(WestLand.getInstance(), key);
        this.recipeType = recipeType;

        if(craftingType == null)
            craftingType = CraftingType.ShapedRecipe;

        switch(craftingType) {
            case ShapedRecipe: {
                shapedRecipe = new ShapedRecipe(this.namespacedKey, result);
                break;
            }
            case ShapelessRecipe: {
                shapelessRecipe = new ShapelessRecipe(namespacedKey, result);
                break;
            }
        }
    }

    public CraftingRecipe shape(String... lines) {
        switch(craftingType) {
            case ShapedRecipe: {
                shapedRecipe.shape(lines);
                break;
            }
            case ShapelessRecipe: {
                throw new IllegalArgumentException("Shapeless recipe shouldn't have a shape!");
            }
        }

        return this;
    }

    public CraftingRecipe setIngredient(char key, RecipeChoice recipeChoice) {
        switch(Objects.requireNonNull(craftingType)) {
            case ShapedRecipe: {
                shapedRecipe.setIngredient(key, recipeChoice);
                break;
            }
            case ShapelessRecipe: {
                throw new IllegalArgumentException("Shapeless recipe type can use only addIngredient");
            }
        }

        return this;
    }

    public CraftingRecipe addIngredient(RecipeChoice recipeChoice) {
       if(craftingType != CraftingType.ShapelessRecipe)
           throw new IllegalArgumentException("Only shapeless recipe type can use addIngredient");

        shapelessRecipe.addIngredient(recipeChoice);
        return this;
    }

    public CraftingRecipe setIngredient(char key, Material material) {
        switch(Objects.requireNonNull(craftingType)) {
            case ShapedRecipe: {
                shapedRecipe.setIngredient(key, material);
                break;
            }
            case ShapelessRecipe: {
                throw new IllegalArgumentException("Only shapeless recipe type can use addIngredient");
            }
        }

        return this;
    }

    public RecipeType getRecipeType() {
        return recipeType;
    }

    public void register(){

        if(shapedRecipe == null && shapelessRecipe == null)
            throw new NullPointerException("Shaped/Shapeless recipe is null: " + namespacedKey.getKey());

       switch(craftingType) {
            case ShapedRecipe: {
                if(shapedRecipe != null && Bukkit.getRecipe(shapedRecipe.getKey()) != null)
                    Bukkit.removeRecipe(shapedRecipe.getKey());

                Bukkit.addRecipe(shapedRecipe);
                break;
            }
            case ShapelessRecipe: {
                if(shapelessRecipe != null && Bukkit.getRecipe(shapelessRecipe.getKey()) != null)
                    Bukkit.removeRecipe(shapelessRecipe.getKey());

                Bukkit.addRecipe(shapelessRecipe);
                break;
            }
        }
    }

    public NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }
}
