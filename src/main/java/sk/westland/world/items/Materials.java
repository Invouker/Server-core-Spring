package sk.westland.world.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.items.Craftable;
import sk.westland.core.items.CustomItem;
import sk.westland.core.items.CustomResourceItem;
import sk.westland.world.items.blocks.*;
import sk.westland.world.items.resources.*;
import sk.westland.world.items.spawners.MagicSpawner;
import sk.westland.world.items.tools.*;

public class Materials {

    public enum Items {
        //Craftable                                               // MODEL ID
        HAMMER(new Hammer(), null),
        BETTER_HOE(new BetterHoe(), null),              //DIAMOND PICKAXE 1
        BETTER_PICKAXE(new BetterPickaxe(), null),
        BETTER_SHOVEL(new BetterShovel(), null),

        // Blocks
        BLOCK_PLACER(new BlockPlacerItem(), null),
        BLOCK_BREAKER(new BlockBreakerItem(), null),
        WORTH_CHEST(new WorthChestItem(), null),
        MOB_GRINDER(new BlockMobGrinderItem(), null),
        CHUNK_COLLECTOR(new BlockChunkCollectorItem(), null),

        SADDLE_ITEM(null, new SaddleItem()),            // SADDLE 2
        MAGIC_SPAWNER(null, new MagicSpawner()),
        WORTH_WAND(null, new WorthWand());

        private Craftable craftable;
        private CustomItem customItem;

        Items(Craftable craftable, CustomItem customItem) {
            this.craftable = craftable;
            this.customItem = customItem;
        }

        public CustomItem getCustomItem() {
            if(!isCraftable())
                return customItem;
            else return (CustomItem) craftable;
        }

        public Craftable getCraftable() {
            return craftable;
        }

        public boolean isCraftable() {
            if(craftable == null)
                return false;

            return true;
        }

        public ItemStack getItem() {
            if(!isCraftable())
                return customItem.getItem();
            else return ((CustomItem) craftable).getItem();
        }

        public Material getType() {
            if(!isCraftable())
                return customItem.getItem().getType();
            else return ((CustomItem) craftable).getItem().getType();
        }

        public static Items findItemName(String name){
            for(Items item : Items.values()) {
                if(item.name().toLowerCase().equals(name.toLowerCase()))
                    return item;
            }
            return null;
        }
    }

    public enum Resources {
                                                            // MODEL ID // MATERIAL
        ENHANCED_NETHERITE_INGOT(new EnhancedNetheriteIngot()), // DEFAULT - ENCHANTED
        COPPER_DUST(new CopperDust()),                      // 2    PAPER
        COPPER_INGOT(new CopperIngot()),                    // 3    PAPER
        IRON_ROD(new IronRod()),                            // 4    PAPER
        RAW_CARBON_FIBRE(new RawCarbonFibre()),             // 5    PAPER
        COMPRESSED_CARBON(new CompressedCarbon()),          // 6    PAPER
        DARK_DIAMOND(new DarkDiamond()) ,               // 7    PAPER
        COAL_DUST(new CoalDust())                  // 8    PAPER
        ;

        private CustomResourceItem customResourceItem;

        Resources(CustomResourceItem customResourceItem) {
            this.customResourceItem = customResourceItem;
        }

        public CustomResourceItem getCustomResourceItem() {
            return customResourceItem;
        }

        public Craftable getCraftable() {
            return (Craftable) customResourceItem;
        }

        public ItemStack getItem() {
            return customResourceItem.getItem();
        }

        public static Resources findItemName(String name){
            for(Resources item : Resources.values()) {
                if(item.name().toLowerCase().equals(name.toLowerCase()))
                    return item;
            }
            return null;
        }
    }

}