package sk.westland.core.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.entity.player.WLPlayer;

public abstract class OwnerItemMenu extends ItemMenu {

    private final WLPlayer wlPlayer;
    private final Player player;

    public OwnerItemMenu(WLPlayer wlPlayer, Type type, String title) {
        super(type, title);
        this.wlPlayer = wlPlayer;
        this.player = wlPlayer.getPlayer();
    }

    public OwnerItemMenu(WLPlayer wlPlayer, Type type, String title, String noInit) {
        super(type, title, "");
        this.wlPlayer = wlPlayer;
        this.player = wlPlayer.getPlayer();
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
    public Player getPlayer() { return player; }

}
