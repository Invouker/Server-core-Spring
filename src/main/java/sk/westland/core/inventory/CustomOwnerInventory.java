package sk.westland.core.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.player.WLPlayer;

public abstract class CustomOwnerInventory extends ItemMenu{

    protected WLPlayer wlPlayer;

    public CustomOwnerInventory(WLPlayer wlPlayer,  Type type,  String title) {
        super(type, title);
        this.wlPlayer = wlPlayer;
    }

    @Override
    protected void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {

        if(player.getName().equals(wlPlayer.getName()))
            this.onClick(slot, item, cursor, event);
    }

    protected abstract void onClick(int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event);

        public WLPlayer getWlPlayer() {
        return wlPlayer;
    }

    public void setWlPlayer(WLPlayer wlPlayer) {
        this.wlPlayer = wlPlayer;
    }

}
