package sk.westland.world.inventories;

import net.minecraft.server.v1_16_R3.ItemSign;
import net.minecraft.server.v1_16_R3.RegistryReadOps;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.EPlayerTimeReward;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.inventory.ItemMenu;
import sk.westland.core.inventory.OwnerItemMenu;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.services.MoneyService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.Utils;
import sk.westland.world.items.Materials;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class DailyRewardInventory extends OwnerItemMenu {

    private final MoneyService moneyService;

    public DailyRewardInventory(WLPlayer wlPlayer, MoneyService moneyService) {
        super(wlPlayer, Type.Chest4, "Denná Odmena", "");
        this.moneyService = moneyService;
        itemInit();
    }

    @Override
    protected void itemInit() {
        ItemStack itemStack = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build();
        this.setItemsRange(0, 9, itemStack);
        this.setItemsRange(27, 9, itemStack);

        this.setItem(3, 1, getItemStackByTimeReward(EPlayerTimeReward.PremiumDaily));
        this.setItem(5, 1, getItemStackByTimeReward(EPlayerTimeReward.PremiumWeekly));
        this.setItem(2, 2, getItemStackByTimeReward(EPlayerTimeReward.Daily));
        this.setItem(4, 2, getItemStackByTimeReward(EPlayerTimeReward.Weekly));
        this.setItem(6, 2, getItemStackByTimeReward(EPlayerTimeReward.Monthly));
        //new ItemBuilder(Material.CHEST_MINECART).setName("").build()
        this.setItemCloseInventory();
    }

    private boolean isClaimed(EPlayerTimeReward timeReward) {
        return getWlPlayer().getRewardClaimedTime(timeReward) > System.currentTimeMillis();
    }

    private ItemStack getItemStackByTimeReward(EPlayerTimeReward ePlayerTimeReward) {
        return getItemStackByTimeReward(
                ePlayerTimeReward,
                getWlPlayer()
                        .getRewardClaimedTime(ePlayerTimeReward),
                isClaimed(ePlayerTimeReward));
    }

    private ItemStack getItemStackByTimeReward(EPlayerTimeReward ePlayerTimeReward, long time, boolean isClaimed) {
        EnumSet<EPlayerTimeReward> ePlayerTimeRewards = EnumSet.of(EPlayerTimeReward.PremiumDaily, EPlayerTimeReward.PremiumWeekly);
        return isClaimed ?
                new ItemBuilder(Material.MINECART)
                        .setName(ChatColor.AQUA + ePlayerTimeReward.getName())
                        .addEnchGlow(ePlayerTimeRewards.contains(ePlayerTimeReward))
                        .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                        .setLore("§r",
                                "§7Túto odmenu si môžeš vyzdvihnúť",
                                "§7vyzdvihnúť každý " + ePlayerTimeReward.getLore() + ".",
                                "",
                                "§fOdomkne sa " + convertTimeWithTimeZome(time),
                                "",
                                "§cOdmena vyzdvihnutá!")
                        .build()
        :
                new ItemBuilder(Material.CHEST_MINECART)
                        .setName(ChatColor.AQUA + ePlayerTimeReward.getName())
                        .addEnchGlow(ePlayerTimeRewards.contains(ePlayerTimeReward))
                        .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                        .setLore("§r",
                                "§7Túto odmenu si môžeš vyzdvihnúť",
                                "§7vyzdvihnúť každý " + ePlayerTimeReward.getLore() + ".",
                                "",
                                "§aKlikni pre vyzdvihnutie!")
                        .build();
    }

    private String convertTimeWithTimeZome(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd.MM. 'o' HH:mm");
        return format.format(date);
    }

    @Override
    protected void onClick(int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        EPlayerTimeReward ePlayerTimeReward = null;
        switch (slot) {
            case 12: {
                if(getPlayer().hasPermission("westland.reward.premium.daily"))
                ePlayerTimeReward = EPlayerTimeReward.PremiumDaily;
                break;
            }
            case 14: {
                if(getPlayer().hasPermission("westland.reward.premium.weekly"))
                ePlayerTimeReward = EPlayerTimeReward.PremiumWeekly;
                break;
            }
            case 20: {
                ePlayerTimeReward = EPlayerTimeReward.Daily;
                break;
            }
            case 22: {
                ePlayerTimeReward = EPlayerTimeReward.Weekly;
                break;
            }
            case 24: {
                ePlayerTimeReward = EPlayerTimeReward.Monthly;
                break;
            }
        }
        if(ePlayerTimeReward == null)
            return;

        if(isClaimed(ePlayerTimeReward)) {
            Utils.playSound(getPlayer(), Sound.UI_BUTTON_CLICK);
            return;
        }

        WLPlayer wlPlayer = getWlPlayer();
        List<ItemStack> itemStackList = new ArrayList<>();
        switch (ePlayerTimeReward) {
            case Daily: {
                moneyService.give(wlPlayer, MoneyType.Gems, 7);
                itemStackList.add(new ItemStack(Material.IRON_INGOT, 7));
                itemStackList.add(new ItemBuilder(Materials.Resources.COAL_DUST.getItem(), 10).build());

                if(Utils.BaseMath.getRandomInt(10) == 1)
                    itemStackList.add(new ItemBuilder(Materials.Resources.COPPER_DUST.getItem(), Utils.BaseMath.getRandomMinMaxInt(0,2)).build());

                if(Utils.BaseMath.getRandomInt(15) == 1)
                        itemStackList.add(new ItemBuilder(Materials.Resources.RAW_CARBON_FIBRE.getItem(),
                                Utils.BaseMath.getRandomMinMaxInt(0,1)).build());
                break;
            }
            case PremiumDaily: {
                moneyService.give(wlPlayer, MoneyType.Gems, 15);
                itemStackList.add(new ItemStack(Material.IRON_INGOT, 25));
                itemStackList.add(new ItemBuilder(Materials.Resources.COAL_DUST.getItem(), 25).build());

                if(Utils.BaseMath.getRandomInt(7) >= 4)
                    itemStackList.add(new ItemBuilder(Materials.Resources.COPPER_DUST.getItem(),
                            Utils.BaseMath.getRandomMinMaxInt(3, 5)).build());

                if(Utils.BaseMath.getRandomInt(7) >= 4)
                    itemStackList.add(new ItemBuilder(Materials.Resources.RAW_CARBON_FIBRE.getItem(),
                            Utils.BaseMath.getRandomMinMaxInt(1, 4)).build());
                break;
            }
            case Weekly: {
                moneyService.give(wlPlayer, MoneyType.Gems, 20);
                if(Utils.BaseMath.getRandomInt(7) == 5)
                    itemStackList.add(new ItemBuilder(Materials.Resources.RAW_CARBON_FIBRE.getItem(),
                            Utils.BaseMath.getRandomMinMaxInt(0,3)).build());

                itemStackList.add(new ItemStack(Material.DIAMOND, 5));

                if(Utils.BaseMath.getRandomInt(20) == 1)
                    moneyService.give(wlPlayer, MoneyType.Shard, 3);

                if(Utils.BaseMath.getRandomInt(20) == 1)
                    itemStackList.add(new ItemBuilder(Materials.Items.WORTH_WAND.getItem())
                            .setNbt_Int("WAND_DURABILITY", 5)
                            .setLoreLine(6, "§7Počet použití: §f" + 5).build());

                break;
            }
            case PremiumWeekly: {
                moneyService.give(wlPlayer, MoneyType.Gems, 30);
                if(Utils.BaseMath.getRandomInt(9) >= 3)
                    itemStackList.add(new ItemBuilder(Materials.Resources.RAW_CARBON_FIBRE.getItem(),
                            Utils.BaseMath.getRandomMinMaxInt(1, 8)).build());

                itemStackList.add(new ItemStack(Material.DIAMOND, 8));

                if(Utils.BaseMath.getRandomInt(20) >= 8)
                    moneyService.give(wlPlayer, MoneyType.Shard, 3);

                if(Utils.BaseMath.getRandomInt(20) >= 11)
                    itemStackList.add(new ItemBuilder(Materials.Items.WORTH_WAND.getItem())
                            .setNbt_Int("WAND_DURABILITY", 15)
                            .setLoreLine(6, "§7Počet použití: §f" + 15).build());

                break;
            }
            case Monthly: {
                moneyService.give(wlPlayer, MoneyType.Money, 1500);
                if(Utils.BaseMath.getRandomInt(3) == 1)
                    moneyService.give(wlPlayer, MoneyType.Shard, 3);

                moneyService.give(wlPlayer, MoneyType.Gems, 30);
                itemStackList.add(new ItemStack(Material.NETHERITE_INGOT, 2));

                if(Utils.BaseMath.getRandomInt(3) == 1)
                    itemStackList.add(new ItemBuilder(Materials.Items.WORTH_WAND.getItem())
                        .setNbt_Int("WAND_DURABILITY", 5)
                        .setLoreLine(6, "§7Počet použití: §f" + 5).build());
                break;
            }
        }

        getPlayer().getInventory().addItem(itemStackList.toArray(new ItemStack[0]));

        Utils.playSound(getPlayer(), Sound.UI_BUTTON_CLICK);
        Utils.playSound(getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP);

        getWlPlayer().setRewardClaimedTime(ePlayerTimeReward, (System.currentTimeMillis() +  (ePlayerTimeReward.getTime() * 1000)));
        itemInit();
    }

    @Override
    protected void onOpen(@NotNull Player player) {

    }

    @Override
    protected void onClose(@NotNull Player player) {

    }
}
