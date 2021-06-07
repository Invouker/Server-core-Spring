package sk.westland.world.blocks.type.multiblocks;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.springframework.stereotype.Component;
import sk.westland.core.App;
import sk.westland.core.blocks.multiblock.IMBRecipe;
import sk.westland.core.blocks.multiblock.MultiBlock;
import sk.westland.core.blocks.multiblock.MultiBlockType;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.services.UtilsService;
import sk.westland.world.items.Materials;

import java.util.EnumSet;
import java.util.List;

@Component
public class EnhancedCrafter extends MultiBlock implements Listener {

    public EnhancedCrafter() {
        super(Material.CRAFTING_TABLE, MultiBlockType.Enhanced_Crafter,
                EnumSet.of(BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH));

        setBlockMaterial(Material.BOOKSHELF, Material.DISPENSER, Material.CRAFTING_TABLE);

        addRecipe(new ItemBuilder(Materials.Resources.ENHANCED_NETHERITE_INGOT.getItem(), 1).build(), Material.NETHERITE_INGOT, Material.DIAMOND, Material.GUNPOWDER);
    }

    @Override
    public void onMultiBlockActivation(Player player, List<Block> blocks, IMBRecipe imbRecipe, PlayerInteractEvent event) {
        App.getService(UtilsService.class).playSound(blocks.get(0).getLocation(), Sound.ENTITY_PLAYER_BURP);
        event.setCancelled(true);
    }
}
