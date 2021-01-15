package sk.westland.core.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Objects;

public class CraftingRecipe {

    private NamespacedKey namespacedKey;
    private ItemStack result;
    private RecipeType recipeType;

    private ShapedRecipe shapedRecipe = null;
    private ShapelessRecipe shapelessRecipe = null;
    private CraftingType craftingType = CraftingType.ShapedRecipe;

    public CraftingRecipe(NamespacedKey key, RecipeType recipeType, ItemStack result, CraftingType craftingType) {
       this(key, recipeType, result);
       this.craftingType = craftingType;
    }

    public CraftingRecipe(NamespacedKey key, RecipeType recipeType, ItemStack result) {
        namespacedKey = key;

        this.result = result;
        this.recipeType = recipeType;

        switch(craftingType) {
            default:
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
                throw new IllegalArgumentException("Only shapeless recipe type can use addIngredient");
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
                shapelessRecipe.addIngredient(material);
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

        System.out.println("REGISTRING RECIPE: " + recipeType + ", " + namespacedKey.toString());
        switch(craftingType) {
            case ShapedRecipe: {
                Bukkit.addRecipe(shapedRecipe);
                break;
            }
            case ShapelessRecipe: {
                Bukkit.addRecipe(shapelessRecipe);
                break;
            }
        }
    }

    public NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }

    public ShapedRecipe getShapedRecipe() {
        return shapedRecipe;
    }

    public ShapelessRecipe getShapelessRecipe() {
        return this.shapelessRecipe;
    }
}
