package sk.westland.world.inventories;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.QuitMessages;
import sk.westland.core.inventory.OwnerItemMenu;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.utils.ChatInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeQuitMessageItemMenu extends OwnerItemMenu {

    List<ItemStack> items = new ArrayList<>();
    private final ItemBuilder item = new ItemBuilder(Material.RED_CONCRETE_POWDER).setName("§cNONE");
    private final ItemStack glassItem = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§a").build();

    public ChangeQuitMessageItemMenu(WLPlayer player) {
        super(player, Type.Chest5, "§8Zmena odpájacích správ");

        prepareItems();
        updateInventory();
    }

    private void updateInventory() {
        getInventory().clear();
        if(items.size() > 0)
            for (int i = 0; i < items.size(); i++) {
                if(i == getWlPlayer().getActiveQuitMessage()) {
                    ItemBuilder itemBuilder = new ItemBuilder(items.get(i));
                    itemBuilder.addEnchant(Enchantment.DAMAGE_ALL, 1)
                            .setMaterial(Material.GREEN_CONCRETE_POWDER)
                            .addItemFlag(ItemFlag.HIDE_ENCHANTS);

                    addItem(itemBuilder.build());
                    continue;
                }
                addItem(items.get(i));
            }
        setItemsRange(36, 9, glassItem);
        setItemCloseInventory();
    }

    private void prepareItems() {
        getInventory().clear();
        for(QuitMessages quitMessages : QuitMessages.values()) {
            String lore = quitMessages.formattedJoinMessageWithoutPrefix().replaceAll("%player%",  getPlayer().getName());
            items.add(item.setName(quitMessages.getName()).setLore(getItemLore(lore)).build().clone());
        }

    }

    private List<String> getItemLore(String joinMessage) {
        return Arrays.asList("§a", "§c" + joinMessage, "§a");
    }
    @Override
    protected void onClick(int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        event.setCancelled(true);

        if(!getPlayer().hasPermission("westland.message.quit")) {
            ChatInfo.ERROR.send(getWlPlayer(), "Zakúp si VIP pre využitie tejto výhody!");
            close(getPlayer());
            return;
        }

        if(slot < 30)
            getWlPlayer().setActiveQuitMessage(slot);

        updateInventory();
    }

    @Override
    protected void onOpen(@NotNull Player player) { }

    @Override
    protected void onClose(@NotNull Player player) { }

    @Override
    protected void itemInit() { }
}
