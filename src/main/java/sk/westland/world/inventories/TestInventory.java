package sk.westland.world.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.inventory.ItemMenu;
import sk.westland.core.inventory.ItemShopMenu;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.player.WLPlayer;

public class TestInventory extends ItemMenu {

    private ItemStack item = new ItemBuilder(Material.STICK).setName("§b ")
            .setLore("Pičo", "Aj tak", " si kokot", "a nič ti nepomôže").build();

    private static int ID = 0;

    public TestInventory() {
        super(Type.Chest4, "test");


    }

    @Override
    protected void itemInit() {
        this.getInventory().setItem(5, new ItemBuilder(item).setName("Close inventory").build());
        this.getInventory().setItem(0, new ItemBuilder(item).setName("§bEaglove Dildo " + ID).build());
    }

    @Override
    protected void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        if(slot == 0) {
            event.setCancelled(true);

            this.getInventory().setItem(0, new ItemBuilder(item).setName("§bEaglove Dildo " + ID).build());
            ID++;
        }

        if(slot == 5) {
            close(player);
        }
    }

    @Override
    protected void onOpen(@NotNull Player player) {

    }

    @Override
    protected void onClose(@NotNull Player player) {

    }
}
