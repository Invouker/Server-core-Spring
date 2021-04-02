package sk.westland.world.inventories.entities;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
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
import sk.westland.core.inventory.NCCustomInventory;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.core.services.HorseService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.RunnableHelper;

import java.util.ArrayList;
import java.util.List;

public class HorseUpgradeInventory extends NCCustomInventory {

    private static final int[] UPGRADE_POSITION = new int[] { 12,13,14,15,16,    23 };
    private static final int SADDLE_ITEM = 19;
    private HorseService horseService;
    private ItemStack saddle = null;

    private boolean closeClick = true;

    public HorseUpgradeInventory(HorseService horseService) {
        super(Type.Chest5, "Horse Inventory Upgrade");
        this.horseService = horseService;
    }

    public HorseUpgradeInventory(HorseService horseService, ItemStack saddle) {
        super(Type.Chest5, "Horse Inventory Upgrade");
        this.horseService = horseService;
        this.saddle = saddle;
        getInventory().setItem(SADDLE_ITEM, saddle);
    }

    @Override
    protected void itemInit() {
        for (int i = 0; i < getInventory().getSize(); i++) {
            if(i == SADDLE_ITEM) continue;
            getInventory().setItem(i, GRAY_GLASS);
        }

        for (int i = 0; i < UPGRADE_POSITION.length; i++) {
            int pos = UPGRADE_POSITION[i];
            getInventory().setItem(pos, HorseUpgradeItem.values()[i].getItem());
        }


        setItemCloseInventory(4, 4);
    }

    private boolean isUpgradePosition(int i) {
        for (int x = 0; x < UPGRADE_POSITION.length; x++)
            if(i == UPGRADE_POSITION[x])
                return true;
        return false;
    }

    @Override
    protected void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        if(slot != SADDLE_ITEM) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
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
                if(getInventory().getItem(SADDLE_ITEM) == null) {
                    return;
                }
                saddle = getInventory().getItem(SADDLE_ITEM);

                int horseType = Nbt.getNbt_Int(saddle, HorseStats.TYPE.getStatName(), 1);
                if(horseType != HorseService.HorseType.HORSE.getId()) {
                    ChatInfo.ERROR.send(player, "Nemôžeš aplikovať tento upgrade na tento typ koňa!");
                    return;
                }

                HorseColorInventory horseColorInventory = new HorseColorInventory(horseService, saddle);
                horseColorInventory.open(player);
                closeClick = false;
                break;
            }
            case 16: {
                update(player, HorseStats.SPEED);
                break;
            }
            case 23: {
                if(getInventory().getItem(SADDLE_ITEM) == null) {
                    return;
                }
                saddle = getInventory().getItem(SADDLE_ITEM);

                int horseType = Nbt.getNbt_Int(saddle, HorseStats.TYPE.getStatName(), 1);
                if(horseType != HorseService.HorseType.HORSE.getId()) {
                    ChatInfo.ERROR.send(player, "Nemôžeš aplikovať tento upgrade na tento typ koňa!");
                    return;
                }

                HorseStyleInventory horseStyleInventory = new HorseStyleInventory(horseService, saddle);
                horseStyleInventory.open(player);
                closeClick = false;
                break;
            }
        }
    }

    private void update(Player player, HorseStats horseStats){
        saddle = getInventory().getItem(SADDLE_ITEM);
        if(saddle == null) {
            ChatInfo.WARNING.send(player, "Musíš dať sedlo ktoré chceš upgradnuť!");
            return;
        }

        int tier = Nbt.getNbt_Int(saddle, horseStats.getStatName(), -1);

        if(horseStats == HorseStats.ARMOR) {
            if(tier >= HorseArmour.getMaxTier()) {
                ChatInfo.ERROR.send(player, "Už máš maximálny zakúpený tier!");
                return;
            }
        }

        if(tier >= HorseTier.getMaxTier()) {
            ChatInfo.ERROR.send(player, "Už máš maximálny zakúpený tier!");
            return;
        }

        ChatInfo.SUCCESS.send(player, "Úspešne si si kúpil " + StringUtils.capitalise(horseStats.name().toLowerCase()) + " upgrade (§oTier: " + (tier + 1) + ") §ana koňa.");
        saddle = horseService.applyStats(saddle, horseStats, tier+1);
        getInventory().setItem(SADDLE_ITEM, saddle);
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
        HEALTH(ChatColor.of("#ff704d") + "Health Upgrade", Material.REDSTONE, new String[]{"", "Možnosť vylepšiť", "zdravie koňa", "Max tier: 6", "", "§eCena upgradu §f20$"}),
        ARMOR("Armor Upgrade", Material.IRON_HORSE_ARMOR, new String[]{"", "Možnosť vylepšiť na", "brnenie koňa", "", "§cMomentálne nedostupné", "§eCena upgradu §f25$"}),
        JUMP("Jump Upgrade", Material.IRON_BOOTS, new String[]{"", "Možnosť vylepšiť", "skok koňa", "Max tier: 6", "", "§eCena upgradu §f60$"}),
        COLOUR("Color Select", Material.BLACK_DYE, new String[]{"", "Možnosť si vybrať", "farbu koňa", "", "§eCena upgradu §f23$"}),
        SPEED("Speed Upgrade", Material.SUGAR, new String[]{"", "Možnosť vylepšiť", "rýchlosť koňa", "Max tier: 6", "", "§eCena upgradu §f50$"}),
        STYLE("Style Select", Material.BLAZE_POWDER, new String[]{"", "Možnosť si vybrať", "štýl koňa", "", "§eCena upgradu §f20$"}),
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
