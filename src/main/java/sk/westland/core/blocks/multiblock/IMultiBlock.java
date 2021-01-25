package sk.westland.core.blocks.multiblock;

import org.bukkit.Material;

public interface IMultiBlock {

    Material[] getBlockMaterial();
    Material getInteractableBlock();

}
