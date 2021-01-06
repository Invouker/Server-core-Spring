package sk.wildwest.core;

import sk.wildwest.core.inventory.CustomInventory;
import sk.wildwest.core.player.WWPlayer;

public class CustomOwnerInventory extends CustomInventory {

    public CustomOwnerInventory() {
        super("test", CustomInventorySize.SIZE_9);
    }


    @Override
    public void onClick(WWPlayer player) {
        
    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }
}
