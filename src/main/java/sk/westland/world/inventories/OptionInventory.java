package sk.westland.world.inventories;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.database.player.PlayerOptions;
import sk.westland.core.inventory.ItemMenu;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.services.PlayerService;

public class OptionInventory extends ItemMenu {

    private PlayerService playerService;

    public OptionInventory(PlayerService playerService) {
        super(Type.Chest4, "Nastavenia");
        this.playerService = playerService;
    }

    private ItemStack generateItem(String name, boolean status) {
        if(status) {
            return new ItemBuilder(Material.RED_CONCRETE_POWDER).setName("§r" + ChatColor.of("#e62900")  +"§l"+ name)
                    .setLore("", ChatColor.of("#57e600") + "Zapnúť zobrazovanie", "").build();
        }
        return new ItemBuilder(Material.LIME_CONCRETE_POWDER).setName("§r" + ChatColor.of("#57e600") +"§l"+ name)
                .setLore("", ChatColor.of("#e62900")  + "Vypnúť zobrazovanie", "").addEnchant(Enchantment.DAMAGE_ALL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).build();
    }

    @Override
    protected void itemInit() {
        setItemsRange(27, 9, GRAY_GLASS);
        setItemCloseInventory(4, 3);
    }

    @Override
    protected void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        PlayerOptions playerOptions = wlPlayer.getPlayerOptions();
        switch(slot) {
            case 0: {
                playerOptions.setShowJoinMessage(!playerOptions.isShowJoinMessage());
                break;
            }
            case 1: {
                playerOptions.setShowQuitMessage(!playerOptions.isShowQuitMessage());
                break;
            }
            case 2: {
                playerOptions.setShowDeathMessage(!playerOptions.isShowDeathMessage());
                break;
            }
        }

        initItems(player);
    }

    @Override
    protected void onOpen(@NotNull Player player) {
        initItems(player);
    }

    @Override
    protected void onClose(@NotNull Player player) {

    }

    private void initItems(Player player) {
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        PlayerOptions playerOptions = wlPlayer.getPlayerOptions();
        setItem(0,0, generateItem("Pripájanie hráčov", playerOptions.isShowJoinMessage()));
        setItem(1,0, generateItem("Odpajánie hráčov", playerOptions.isShowQuitMessage()));
        setItem(2,0, generateItem("Smrti hráčov", playerOptions.isShowDeathMessage()));
    }
}
