package sk.westland.world.blocks.type.multiblocks;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.springframework.stereotype.Component;
import sk.westland.core.blocks.multiblock.IMBRecipe;
import sk.westland.core.blocks.multiblock.MultiBlock;
import sk.westland.core.blocks.multiblock.MultiBlockType;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.utils.Utils;

import java.util.EnumSet;

@Component
public class Grinder extends MultiBlock implements Listener {

    public Grinder() {
        super(Material.WARPED_FENCE, MultiBlockType.Grinder,
                EnumSet.of(BlockFace.DOWN));
        setBlockMaterial(Material.CAULDRON, Material.DISPENSER, Material.WARPED_FENCE);

        addRecipe(Material.FLINT, Material.GRAVEL);
        addRecipe(Material.COBBLESTONE, Material.STONE);
        addRecipe(Material.SAND, Material.COBBLESTONE);
        addRecipe(new ItemBuilder(Material.REDSTONE).setAmount(9).build(), Material.REDSTONE_BLOCK);
    }

    @Override
    public void onMultiBlockActivation(Player player, Dispenser dispenser, IMBRecipe imbRecipe, PlayerInteractEvent event) {
        Utils.playSound(dispenser.getLocation(), Sound.BLOCK_GRINDSTONE_USE);
    }
}
