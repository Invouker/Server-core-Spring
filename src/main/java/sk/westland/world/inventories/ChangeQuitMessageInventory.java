package sk.westland.world.inventories;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.inventory.CustomOwnerInventory;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.services.MessageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeQuitMessageInventory extends CustomOwnerInventory {

    List<ItemStack> items = new ArrayList<>();
    private ItemBuilder item = new ItemBuilder(Material.RED_CONCRETE_POWDER).setName("§cNONE");
    private ItemStack glassItem = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§a").build();

    private MessageService messageService;

    public ChangeQuitMessageInventory(WLPlayer player, MessageService messageService) {
        super(player, Type.Chest5, "§7Zmena odpájacích správ");

        this.messageService = messageService;

        updateInventory();
    }

    private void updateInventory() {
        getInventory().clear();
        if(items.size() > 0)
            for (int i = 0; i < items.size(); i++) {
                if(i == wlPlayer.getActiveQuitMessage()) {
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
        setItem(4, 4, CLOSE_INVENTORY_ITEM);
    }

    @Override
    protected void itemInit() {
        getInventory().clear();
        for(MessageService.QuitMessage quitMessage : MessageService.QuitMessage.values()) {
            String lore = quitMessage.formattedJoinMessageWithoutPrefix().replaceAll("%player%",  wlPlayer.getName());
            items.add(item.setName(quitMessage.getName()).setLore(getItemLore(lore)).build().clone());
        }
    }

    private List<String> getItemLore(String joinMessage) {
        return Arrays.asList("§a", "§c" + joinMessage, "§a");
    }

    @Override
    protected void onClick(int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        event.setCancelled(true);

        if(!wlPlayer.hasPermission("westland.message.quit")) {
            ChatInfo.ERROR.send(wlPlayer, "Zakúp si VIP pre využitie tejto výhody!");
            close(wlPlayer);
            return;
        }

        if(slot < 30)
            wlPlayer.setActiveQuitMessage(slot);

        updateInventory();

        if(slot == 40)
            close(wlPlayer);

    }

    @Override
    protected void onOpen(@NotNull Player player) { }

    @Override
    protected void onClose(@NotNull Player player) { }
}
