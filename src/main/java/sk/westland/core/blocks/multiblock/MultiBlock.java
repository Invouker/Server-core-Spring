package sk.westland.core.blocks.multiblock;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.App;
import sk.westland.core.services.BlockService;
import sk.westland.core.services.RunnableService;
import sk.westland.core.services.UtilsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public abstract class MultiBlock implements IMultiBlock {


    private BlockService blockService;


    private RunnableService runnableService;

    private Material[] blockMaterial;
    private Material interactableMaterial;
    private List<IMBRecipe> imbRecipes;
    private MultiBlockType multiBlockType;
    private EnumSet<BlockFace> blockFaceEnumSet;

    public MultiBlock(Material interactableMaterial, MultiBlockType multiBlockType, EnumSet<BlockFace> blockFaceEnumSet) {
        this.interactableMaterial = interactableMaterial;
        this.multiBlockType = multiBlockType;
        this.blockFaceEnumSet = blockFaceEnumSet;
    }

    public MultiBlock(Material interactableMaterial, MultiBlockType multiBlockType, BlockFace... blockFace) {
        this.interactableMaterial = interactableMaterial;
        this.multiBlockType = multiBlockType;
        addBlockFaceEnumSet(blockFace);
    }

    public Material[] getBlockMaterial() {
        return blockMaterial;
    }

    public void setBlockMaterial(Material... blockMaterial) {
        this.blockMaterial = blockMaterial;
    }

    public Material getInteractableMaterial() {
        return interactableMaterial;
    }

    public List<IMBRecipe> getImbRecipes() {
        return imbRecipes;
    }


    @Override
    public Material getInteractableBlock() {
        return interactableMaterial;
    }

    protected void addRecipe(Material result, ItemStack... ingredients) {
        this.addRecipe(new ItemStack(result), ingredients);
    }

    protected void addRecipe(ItemStack result, Material... ingredients) {
        ItemStack[] itemStacks = new ItemStack[ingredients.length];
        for (int i = 0; i < ingredients.length; i++) {
            itemStacks[i] = new ItemStack(ingredients[i]);
        }
        this.addRecipe(result, itemStacks);
    }

    protected void addRecipe(Material result, Material... ingredients) {
        ItemStack[] itemStacks = new ItemStack[ingredients.length];
        for (int i = 0; i < ingredients.length; i++) {
            itemStacks[i] = new ItemStack(ingredients[i]);
        }
        this.addRecipe(new ItemStack(result), itemStacks);
    }

    protected void addRecipe(ItemStack result, ItemStack... ingredients) {
        IMBRecipe recipe = new IMBRecipe() {
            @Override
            public ItemStack getResult() {
                return result;
            }

            @Override
            public List<ItemStack> getItems() {
                return Arrays.asList(ingredients);
            }
        };

        if(getImbRecipes() == null)
            imbRecipes = new ArrayList<>();

        this.getImbRecipes().add(recipe);
    }

    public void addBlockFaceEnumSet(BlockFace... blockFaces) {
        for(BlockFace blockFace : blockFaces)
            addBlockFaceEnumSet(blockFace);
    }

    public void addBlockFaceEnumSet(BlockFace blockFace) {
        EnumSet.of(blockFace);
    }

    public EnumSet<BlockFace> getBlockFaceEnumSet() {
        return blockFaceEnumSet;
    }

    public void setBlockFaceEnumSet(EnumSet<BlockFace> blockFaceEnumSet) {
        this.blockFaceEnumSet = blockFaceEnumSet;
    }

    private boolean checkConstruction(Block block) {
        if(blockFaceEnumSet == null)
            blockFaceEnumSet = EnumSet.of(BlockFace.DOWN);

        for (BlockFace blockFace : blockFaceEnumSet) {
            for (int i = 0; i < blockMaterial.length; i++) {
                Block checkedBlock = block.getRelative(blockFace, blockMaterial.length-i-1);
                if(checkedBlock.getType() == blockMaterial[i])
                    if(i == blockMaterial.length-1)
                        return true;
            }
        }
        return false;
    }

    private List<Block> getMultiBlockConstruct(Block block) {
        List<Block> constructBlocks = new ArrayList<>();
        if(blockFaceEnumSet == null)
            blockFaceEnumSet = EnumSet.of(BlockFace.DOWN);

        for (BlockFace blockFace : blockFaceEnumSet) {
            for (int i = 0; i < blockMaterial.length; i++) {
                Block checkedBlock = block.getRelative(blockFace, blockMaterial.length-i-1);
                constructBlocks.add(checkedBlock);
            }
        }

        return constructBlocks;
    }

    public abstract void onMultiBlockActivation(Player player, List<Block> blocks, IMBRecipe imbRecipe, PlayerInteractEvent event);

    private void onPlayerInteract(PlayerInteractEvent event) {

        if(event == null)
            return;

        if(event.getAction() == null)
            return;

        if(event.getHand() != EquipmentSlot.HAND)
            return;

        switch (event.getAction()) {
            case RIGHT_CLICK_AIR:
            case PHYSICAL:
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                return;
        }

        if(!event.hasBlock())
            return;

        Block block = event.getClickedBlock();

        if(block != null && block.getType() != interactableMaterial)
            return;

        if(blockService.isCustomBlock(block.getLocation()))
            return;

        if(!checkConstruction(block))
            return;

        App.getService(UtilsService.class).playArmAnimation(event.getPlayer());

        List<Block> blocks = getMultiBlockConstruct(event.getClickedBlock());

        Dispenser dispenser = null;

        for (Block blockIterate : blocks) {
            if(blockIterate.getType() == Material.DISPENSER) {
                dispenser = (Dispenser) blockIterate.getState();
            }
        }

        if(dispenser == null)
            return;

        IMBRecipe imbRecipe = getRecipe(Arrays.asList(dispenser.getInventory().getContents()));
        takeItems(imbRecipe, dispenser.getInventory());
        onMultiBlockActivation(event.getPlayer(), getMultiBlockConstruct(block), imbRecipe, event);
    }

    protected void takeItems(IMBRecipe imbRecipe, Inventory inventory) {
        if(imbRecipe == null)
            return;

        if(imbRecipe.getItems().isEmpty() || imbRecipe.getResult() == null)
            return;

        if(inventory == null)
            return;

        if(inventory.getContents().length <= 0)
            return;

        main: for(ItemStack itemStack : imbRecipe.getItems()) {
            for(ItemStack inventoryItem : inventory.getContents()) {
                if(inventoryItem != null && itemStack != null  && inventoryItem.isSimilar(itemStack)) {
                    int firstEmpty = inventory.firstEmpty();
                    if(firstEmpty == -1)
                        return;

                    inventoryItem.setAmount(inventoryItem.getAmount() - itemStack.getAmount());
                    continue main;
                }
            }
        }

        runnableService.runTask(()->inventory.addItem(imbRecipe.getResult()));
    }

    protected IMBRecipe getRecipe(List<ItemStack> inventory) {
        for(IMBRecipe imbRecipe : imbRecipes) {
            int i = 0;
            for(ItemStack recipeItem : imbRecipe.getItems()) {
                for(ItemStack inventoryItem : inventory) {
                    if(recipeItem.isSimilar(inventoryItem))
                        i++;
                }
            }

            if(i >= imbRecipe.getItems().size())
                return imbRecipe;

        }
        return null;
    }

}
