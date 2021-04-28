package sk.westland.world.inventories.entities;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Triple;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.codehaus.plexus.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.enums.HorseArmour;
import sk.westland.core.enums.HorseStats;
import sk.westland.core.enums.HorseTier;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.inventory.NCCustomInventory;
import sk.westland.core.inventory.NCItemShopMenu;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.core.services.HorseService;
import sk.westland.core.services.MoneyService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.RunnableHelper;

import java.util.ArrayList;
import java.util.List;

public class HorseUpgradeInventory extends NCItemShopMenu {

    private static final int[] UPGRADE_POSITION = new int[] { 12,13,14,15,16,    23 };
    private static final int SADDLE_ITEM = 19;
    private HorseService horseService;
    private ItemStack saddle = null;

    private boolean closeClick = true;


    public HorseUpgradeInventory(HorseService horseService, MoneyService moneyService, Player player) {
        super(Type.Chest5, "Horse Inventory Upgrade", moneyService, player, "");
        this.horseService = horseService;

        itemInit();
    }

    public HorseUpgradeInventory(HorseService horseService, ItemStack saddle, MoneyService moneyService, Player player) {
        super(Type.Chest5, "Horse Inventory Upgrade", moneyService, player, "");
        this.horseService = horseService;
        this.saddle = saddle;

        getInventory().setItem(SADDLE_ITEM, saddle);

        itemInit();
    }

    @Override
    protected void itemInit() {
        for (int i = 0; i < UPGRADE_POSITION.length; i++) {
            int pos = UPGRADE_POSITION[i];
            ItemBuilder itemStack = new ItemBuilder(HorseUpgradeItem.values()[i].getItem());
            setItem(pos, itemStack.build(), itemStack.build(), MoneyType.Money, 250);
        }

        setItemCloseInventory(4, 4);

        updateInventory();
        for (int i = 0; i < getInventory().getSize(); i++) {
            if(i == SADDLE_ITEM) continue;
            if(isUpgradePosition(i)) continue;
            getInventory().setItem(i, GRAY_GLASS);
        }
    }

    private boolean isUpgradePosition(int i) {
        for (int x = 0; x < UPGRADE_POSITION.length; x++)
            if(i == UPGRADE_POSITION[x])
                return true;
        return false;
    }

    @Override
    protected void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        //super.onClick(player, slot, item, cursor, event);
        if(slot != SADDLE_ITEM) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }

        Triple<ItemStack, MoneyType, Integer> shopItem = itemMap.getOrDefault(item, null);

        if(shopItem == null)
            return;

        ItemStack shopItemStack = shopItem.getLeft();
        MoneyType moneyType = shopItem.getMiddle();
        int itemPrice = shopItem.getRight();

        if(shopItemStack != null)
        if(moneyService.canPay(player, moneyType, itemPrice)) {
            // Pay for the item
            moneyService.pay(player, moneyType, itemPrice);
        }

        switch (slot) {
            case 12: {
                update(player, HorseStats.HEALTH);

                break;
            }
            case 13: {
                ChatInfo.ERROR.send(player, "Tento item je momentálne nedostupný!");
                //update(player, HorseStats.ARMOR);
                break;
            }
            case 14: {
                update(player, HorseStats.JUMP);
                break;
            }

            case 15: {
                ChatInfo.ERROR.send(player, "Tento item je momentálne nedostupný!");
                /*
                if(getInventory().getItem(SADDLE_ITEM) == null) {
                    return;
                }
                saddle = getInventory().getItem(SADDLE_ITEM);

                int horseType = Nbt.getNbt_Int(saddle, HorseStats.TYPE.getStatName(), 1);
                if(horseType != HorseService.HorseType.HORSE.getId()) {
                    ChatInfo.ERROR.send(player, "Nemôžeš aplikovať tento upgrade na tento typ koňa!");
                    return;
                }

                HorseColorInventory horseColorInventory = new HorseColorInventory(horseService, saddle, moneyService, player);
                horseColorInventory.open(player);
                closeClick = false;
                break;

                 */
            }
            case 16: {
                update(player, HorseStats.SPEED);
                break;
            }
            case 23: {
                ChatInfo.ERROR.send(player, "Tento item je momentálne nedostupný!");
                /*
                if(getInventory().getItem(SADDLE_ITEM) == null) {
                    return;
                }
                saddle = getInventory().getItem(SADDLE_ITEM);

                int horseType = Nbt.getNbt_Int(saddle, HorseStats.TYPE.getStatName(), 1);
                if(horseType != HorseService.HorseType.HORSE.getId()) {
                    ChatInfo.ERROR.send(player, "Nemôžeš aplikovať tento upgrade na tento typ koňa!");
                    return;
                }

                HorseStyleInventory horseStyleInventory = new HorseStyleInventory(horseService, saddle, moneyService, player);
                horseStyleInventory.open(player);
                closeClick = false;
                break;*/
            }
            default:
                throw new IllegalStateException("Unexpected value: " + slot);
        }
    }

    private boolean update(Player player, HorseStats horseStats){
        saddle = getInventory().getItem(SADDLE_ITEM);
        if(saddle == null) {
            ChatInfo.WARNING.send(player, "Musíš dať sedlo ktoré chceš upgradnuť!");
            return false;
        }

        int tier = Nbt.getNbt_Int(saddle, horseStats.getStatName(), -1);

        if(horseStats == HorseStats.ARMOR) {
            if(tier >= HorseArmour.getMaxTier()) {
                ChatInfo.ERROR.send(player, "Už máš maximálny zakúpený tier!");
                return false;
            }
        }

        if(tier >= HorseTier.getMaxTier()) {
            ChatInfo.ERROR.send(player, "Už máš maximálny zakúpený tier!");
            return false;
        }

        ChatInfo.SUCCESS.send(player, "Úspešne si si kúpil " + StringUtils.capitalise(horseStats.name().toLowerCase()) + " upgrade (§oTier: " + (tier + 1) + ") §ana koňa.");
        saddle = horseService.applyStats(saddle, horseStats, tier+1);
        getInventory().setItem(SADDLE_ITEM, saddle);
        return true;
    }

    @Override
    protected void onOpen(@NotNull Player player) {
    }

    @Override
    protected void onClose(@NotNull Player player) {
        saddle = getInventory().getItem(SADDLE_ITEM);
        if(saddle == null)
            return;

        RunnableHelper.runTaskLater(() -> {
            if(closeClick)
                player.getInventory().addItem(saddle);
        },5L);

    }

    private enum HorseUpgradeItem {
        HEALTH(ChatColor.of("#ff704d") + "Health Upgrade", Material.REDSTONE, new String[]{"", "Možnosť vylepšiť", "zdravie koňa", "Max tier: 6", ""}),
        ARMOR(ChatColor.of("#99ccff") + "Armor Upgrade", Material.IRON_HORSE_ARMOR, new String[]{"", "Možnosť vylepšiť na", "brnenie koňa", "", "§cMomentálne nedostupné"}),
        JUMP(ChatColor.of("#ffffb3") + "Jump Upgrade", Material.IRON_BOOTS, new String[]{"", "Možnosť vylepšiť", "skok koňa", "Max tier: 6", ""}),
        COLOR(ChatColor.of("#bfff80") + "Color Select", Material.BLACK_DYE, new String[]{"", "Možnosť si vybrať", "farbu koňa", "",}),
        SPEED(ChatColor.of("#ccccff") + "Speed Upgrade", Material.SUGAR, new String[]{"", "Možnosť vylepšiť", "rýchlosť koňa", "Max tier: 6", ""}),
        STYLE(ChatColor.of("#d98cb3") + "Style Select", Material.BLAZE_POWDER, new String[]{"", "Možnosť si vybrať", "štýl koňa", ""}),
        //ARMOR_COLOUR("Armor Colour Select", Material.LEATHER_HORSE_ARMOR, new String[]{"", "Možno vybrať", "farbu armoru", "", "§eCena upgradu §f30$"})
        ;

        private String name;
        private Material material;
        private List<String> lore;

        HorseUpgradeItem(String name, Material material, String[] lore) {
            this.name = "§f" + name;
            this.material = material;
            List<String> coloredLore = new ArrayList<>();
            for(String string : lore)
                coloredLore.add("§7" + string);

            this.lore = coloredLore;
        }

        public String getName() {
            return name;
        }

        public Material getMaterial() {
            return material;
        }

        public List<String> getLore() {
            return lore;
        }

        public ItemStack getItem() {
            return new ItemBuilder(material).setName(name).setLore(lore).build();
        }
    }
}
