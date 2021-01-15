package sk.westland.world.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.items.Craftable;
import sk.westland.core.items.CustomItem;
import sk.westland.core.items.CustomResourceItem;
import sk.westland.world.items.resources.IronRod;
import sk.westland.world.items.tools.Crowbar;

public class Materials {

    public enum Items {
        //Craftable                                               // MODEL ID
        CROWBAR(new Crowbar(), null);

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
        IRON_ROD(new IronRod());        // 1201     // paper

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
    }

}