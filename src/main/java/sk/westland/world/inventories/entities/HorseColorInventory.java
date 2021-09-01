package sk.westland.world.inventories.entities;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.world.entity.animal.horse.HorseColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.enums.HorseStats;
import sk.westland.core.inventory.NCCustomInventory;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.core.services.HorseService;
import sk.westland.core.services.MoneyService;
import sk.westland.core.services.RunnableService;

public class HorseColorInventory extends NCCustomInventory {

    private static final int SADDLE_ITEM = 24;

    private HorseService horseService;
    private ItemStack saddle;

    private RunnableService runnableService;
    private MoneyService moneyService;
    private Player player;

    public HorseColorInventory(HorseService horseService, ItemStack saddle, MoneyService moneyService, RunnableService runnableService, Player player) {
        super(Type.Chest5, "Horse Color Upgrade", null);
        this.horseService = horseService;
        this.runnableService = runnableService;
        this.saddle = saddle;

        this.moneyService = moneyService;
        this.player = player;
        getInventory().setItem(SADDLE_ITEM, saddle);
        itemInit();
    }

    @Override
    protected void itemInit() {
        for (int i = 0; i < getInventory().getSize(); i++) {
            if(i == SADDLE_ITEM) continue;
            getInventory().setItem(i, GRAY_GLASS);
        }
        updateUpgades();

        setItemCloseInventory(4, 4); // TODO: EDIT TO: "GO TO PREVIOUS PAGE"
    }

    @Override
    protected void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        ItemStack saddleItem = getInventory().getItem(SADDLE_ITEM);
        switch (slot) {
            case 10: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.COLOR.getStatName(), HorseColor.a.a());
                break;
            }
            case 11: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.COLOR.getStatName(), HorseColor.b.a());
                break;
            }
            case 12: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.COLOR.getStatName(), HorseColor.c.a());
                break;
            }
            case 19: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.COLOR.getStatName(), HorseColor.d.a());
                break;
            }
            case 20: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.COLOR.getStatName(), HorseColor.e.a());
                break;
            }
            case 21: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.COLOR.getStatName(), HorseColor.f.a());
                break;
            }
            case 29: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.COLOR.getStatName(), HorseColor.g.a());
                break;
            }
            default:
                break;
        }

        int id = Nbt.getNbt_Int(saddle, HorseStats.COLOR.getStatName(), -1);
        saddle = horseService.applyStats(saddle, HorseStats.COLOR, id);
        getInventory().setItem(SADDLE_ITEM, saddle);
        updateUpgades();
    }

    @Override
    protected void onOpen(@NotNull Player player) {
        //HorseService.HorseInventory horseInventory = horseService.getInventoryData(player);

    }

    @Override
    protected void onClose(@NotNull Player player) {
        runnableService.runTask(() -> {
            HorseUpgradeInventory horseUpgradeInventory = new HorseUpgradeInventory(horseService, saddle, moneyService,runnableService, player);
            horseUpgradeInventory.open(player);
        });
    }

    private void updateUpgades() {
        int id = Nbt.getNbt_Int(saddle, HorseStats.COLOR.getStatName(), -1);
        for(Colors colors : Colors.values()) {
            if(colors.getId() == id) {
                getInventory().setItem(colors.getSlot(),
                        new ItemBuilder(colors.getItemStack())
                                .addEnchant(Enchantment.DAMAGE_ALL, 1)
                                .hideAllFlags()
                                .build());
            } else
                getInventory().setItem(colors.getSlot(), colors.getItemStack());
        }
    }

    private enum Colors {

        WHITE(0,10, new ItemBuilder(Material.FLINT, "§fWhite").build()),
        CREAMY(1,11, new ItemBuilder(Material.PINK_DYE, ChatColor.of("#fffdd0") + "Creamy").build()),
        CHESTNUT(2,12, new ItemBuilder(Material.BROWN_DYE,  ChatColor.of("#954535") + "Chestnut").build()),
        BROWN(3,19, new ItemBuilder(Material.BROWN_DYE, ChatColor.of("#8b4513") + "Brown").build()),
        BLACK(4,20, new ItemBuilder(Material.BLACK_DYE, ChatColor.of("#808080") + "Black").build()),
        GRAY(5,21, new ItemBuilder(Material.GRAY_DYE, ChatColor.of("#808080") + "Gray").build()),
        DARK_BROWN(6, 29, new ItemBuilder(Material.BROWN_DYE, ChatColor.of("#654321") + "Dark Brown").build())
        ;

        private int id;
        private int slot;
        private ItemStack itemStack;

        Colors(int id, int slot, ItemStack itemStack) {
            this.id = id;
            this.slot = slot;
            this.itemStack = itemStack;
        }

        public int getId() {
            return id;
        }

        public int getSlot() {
            return slot;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }
    }
}
