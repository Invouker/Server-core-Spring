package sk.westland.world.inventories.entities;

import net.minecraft.world.entity.animal.horse.HorseStyle;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.WestLand;
import sk.westland.core.enums.HorseStats;
import sk.westland.core.inventory.NCCustomInventory;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.core.services.HorseService;
import sk.westland.core.services.MoneyService;
import sk.westland.core.services.RunnableService;

public class  HorseStyleInventory extends NCCustomInventory {

    private static final int SADDLE_ITEM = 10;

    private final RunnableService runnableService;
    private final HorseService horseService;
    private ItemStack saddle;

    private final  MoneyService moneyService;
    private final Player player;

    public HorseStyleInventory(HorseService horseService, ItemStack saddle, MoneyService moneyService, RunnableService runnableService, Player player) {
        super(Type.Chest4, "Horse Style Upgrade", "c");
        this.runnableService = runnableService;
        this.horseService = horseService;
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

        setItemCloseInventory(4, 3); // TODO: EDIT TO: "GO TO PREVIOUS PAGE"
    }

    @Override
    protected void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        event.setCancelled(true);
        event.setResult(Event.Result.DENY);


        ItemStack saddleItem = getInventory().getItem(SADDLE_ITEM);
        switch (slot) {
            case 12: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.STYLE.getStatName(), HorseStyle.a.a());
                break;
            }
            case 13: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.STYLE.getStatName(), HorseStyle.b.a());
                break;
            }
            case 14: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.STYLE.getStatName(), HorseStyle.c.a());
                break;
            }
            case 15: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.STYLE.getStatName(), HorseStyle.d.a());
                break;
            }
            case 16: {
                saddle = Nbt.setNbt_Int(saddleItem, HorseStats.STYLE.getStatName(), HorseStyle.e.a());
                break;
            }
            default:
                break;
        }

        int id = Nbt.getNbt_Int(saddle, HorseStats.STYLE.getStatName(), -1);
        saddle = horseService.applyStats(saddle,HorseStats.STYLE, id);
        getInventory().setItem(SADDLE_ITEM, saddle);
        updateUpgades();
    }

    @Override
    protected void onOpen(@NotNull Player player) {
        //HorseService.HorseInventory horseInventory = horseService.getInventoryData(player);

    }

    @Override
    protected void onClose(@NotNull Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                HorseUpgradeInventory horseUpgradeInventory = new HorseUpgradeInventory(horseService, saddle, moneyService, runnableService, player);
                horseUpgradeInventory.open(player);
            }
        }.runTask(WestLand.getInstance());
    }

    private void updateUpgades() {
        int id = Nbt.getNbt_Int(saddle, HorseStats.STYLE.getStatName(), -1);
        for(Styles styles : Styles.values()) {
            if(styles.getSlot()-12 == id) {
                getInventory().setItem(styles.getSlot(),
                        new ItemBuilder(styles.getItemStack())
                                .addEnchant(Enchantment.DAMAGE_ALL, 1)
                                .hideAllFlags()
                                .build());
            } else
                getInventory().setItem(styles.getSlot(), styles.getItemStack());
        }
    }

    private enum Styles {
        REMOVE_STYLE(12, new ItemBuilder(Material.FLINT, "Remove Style").build()),
        WHITE_STYLE(13, new ItemBuilder(Material.WHITE_DYE, "White Style").build()),
        WHITE_FIELD(14, new ItemBuilder(Material.LIGHT_GRAY_DYE, "White field").build()),
        WHITE_DOTS(15, new ItemBuilder(Material.GRAY_DYE, "White dots").build()),
        BLACK_DOTS(16, new ItemBuilder(Material.BLACK_DYE, "Black dots").build())
        ;

        private int slot;
        private ItemStack itemStack;

        Styles(int slot, ItemStack itemStack) {
            this.slot = slot;
            this.itemStack = itemStack;
        }

        public int getSlot() {
            return slot;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }
    }
}
