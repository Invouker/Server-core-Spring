package sk.westland.world.items.blocks.multiblocks;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.blocks.multiblock.IMBRecipe;
import sk.westland.core.blocks.multiblock.MultiBlock;
import sk.westland.core.blocks.multiblock.MultiBlockType;
import sk.westland.world.items.Materials;

import java.util.EnumSet;

@Component
public class Grinder extends MultiBlock implements Listener {

    public Grinder() {
        super(Material.WARPED_FENCE, MultiBlockType.Grinder, EnumSet.of(BlockFace.DOWN));
        setBlockMaterial(Material.CAULDRON, Material.DISPENSER, Material.WARPED_FENCE);

        addRecipe(Materials.Items.BETTER_HOE.getItem(),
                new ItemStack(Material.WHEAT_SEEDS), new ItemStack(Material.WHEAT));
    }


    @Override
    public void onMultiBlockActivation(Player player, Dispenser dispenser, IMBRecipe imbRecipe, PlayerInteractEvent event) {
    }
}
