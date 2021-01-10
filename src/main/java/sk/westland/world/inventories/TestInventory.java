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


    public TestInventory() {
        super(Type.Chest4, "test");

        this.getInventory().addItem(new ItemBuilder(Material.STICK).setName("§bEaglove Dildo").setLore("Pičo", "Aj tak", " si kokot", "a nič ti nepomôže").build());
    }

    @Override
    protected void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        System.out.println("Clicking on: " + item.toString() + " playerName: " + player.getName());

        if(slot == 0) {
            event.setCancelled(true);

            System.out.println("Si len piča!!");
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
