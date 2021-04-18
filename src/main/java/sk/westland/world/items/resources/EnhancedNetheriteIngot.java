package sk.westland.world.items.resources;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.items.*;

public class EnhancedNetheriteIngot extends CustomResourceItem {

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(Material.NETHERITE_INGOT)
                .setName("§rEnhanced Netherite Ingot")
                .addEnchant(Enchantment.MULTISHOT, 1)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .setModelId(getModelID())
                .build();
    }

    @Override
    public int getModelID() {
        return 0;
    }

}
