package sk.westland.world.blocks.type.multiblocks;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.blocks.multiblock.IMBRecipe;
import sk.westland.core.blocks.multiblock.MultiBlock;
import sk.westland.core.blocks.multiblock.MultiBlockType;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.utils.Utils;
import sk.westland.world.items.Materials;

import java.util.EnumSet;
import java.util.List;

@Component
public class Compressor extends MultiBlock implements Listener {

    public Compressor() {
        super(Material.DARK_OAK_FENCE, MultiBlockType.Compressor,
                EnumSet.of(BlockFace.DOWN));

        setBlockMaterial(Material.CAULDRON, Material.BLACK_STAINED_GLASS, Material.DISPENSER, Material.DARK_OAK_FENCE);

        addRecipe(Materials.Resources.RAW_CARBON_FIBRE.getItem(), new ItemBuilder(Materials.Resources.COAL_DUST.getItem(), 64).build());
    }


    @Override
    public void onMultiBlockActivation(Player player, List<Block> blocks, IMBRecipe imbRecipe, PlayerInteractEvent event) {
        Utils.playSound(blocks.get(0).getLocation(), Sound.ENTITY_PUFFER_FISH_STING);
    }
}
