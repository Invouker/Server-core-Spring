package sk.westland.world.items.tools;

import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.Utils;
import sk.westland.world.items.Materials;

@Component
public class WorthWand extends CustomItem implements Craftable, Listener {

    private final String WAND_NBT_KEY = "WAND_DURABILITY";

    @Override
    public CraftingRecipe getCraftingRecipe() {
        return new CraftingRecipe(itemID(), RecipeType.Item, getItem())
                .shape("ABB", "BAB", "BBA")
                .setIngredient('B', recipeService.item(Materials.Resources.COPPER_DUST.getItem()))
                .setIngredient('A', recipeService.item(Materials.Resources.IRON_ROD.getItem()));
    }

    @Override
    public ItemStack getItem() {
        int WAND_DURABILITY = 50;
        return customItemStack = new ItemBuilder(Material.STICK)
                .setModelId(getModelID())
                .setName("§eSell Wand")
                .setCustomItem(this)
                .setNbt_Int(WAND_NBT_KEY, WAND_DURABILITY)
                .setLore(
                        "",
                        "§7Wandka na predávanie veci z chestky",
                        "§7kliknutím pravým tlačítkom na",
                        "§7chestu predáte v nej itemy ktoré",
                        "§7sa dajú predať v klasickom obchode!",
                        "",
                        "§7Počet použití: §f" + WAND_DURABILITY,
                        "")
                .build();
    }

    private ItemStack setWandDurability(ItemStack itemStack, int durability) {
        return new ItemBuilder(itemStack)
                .setLoreLine(6,  "§7Počet použití: §f" + durability)
                .setNbt_Int(WAND_NBT_KEY, durability)
                .build();
    }

    @Override
    public int getModelID() {
        return 5;
    }

    @Override
    public String itemID() {
        return "sellWand";
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        recipeService.registerRecipe(getCraftingRecipe());
    }

    @Override
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) {
        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK:
            case PHYSICAL:
            case LEFT_CLICK_AIR:
            case RIGHT_CLICK_AIR:
                return;
        }

        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        assert block != null;

        if (block.getType() != Material.CHEST)
            return;

        Chest chest = (Chest) block.getState();
        Inventory inventory = chest.getInventory();
        int sellPrice = 0;

        for (int i = 0; i < chest.getInventory().getContents().length; i++) {
            ItemStack itemStack = chest.getInventory().getItem(i);
            if(itemStack == null)
                continue;

            double amount = ShopGuiPlusApi.getItemStackPriceSell(itemStack);

            if(amount == -1)
                continue;

            blockService.getMoneyService().give(player, MoneyType.Money, amount);
            sellPrice += amount;
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                 inventory.setItem(finalI, null);
                }
            }.runTask(WestLand.getInstance());
        }

        ChatInfo.SUCCESS.send(player, "Predal si itemy v hodnote §6" + sellPrice+ "$");

        {
            ItemStack itemMainHand = player.getInventory().getItemInMainHand();
            int durability = Nbt.getNbt_Int(itemMainHand, WAND_NBT_KEY);

            ItemStack sellWand = setWandDurability(itemMainHand, Nbt.getNbt_Int(itemMainHand, WAND_NBT_KEY) - 1);
            if(durability > 1)
                player.getInventory().setItemInMainHand(sellWand);
            else {
                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                player.updateInventory();
                itemInteractionService.playSound(player, Sound.ENTITY_ITEM_BREAK);
            }
        }

        if(sellPrice == 0)
            Utils.playSound(block.getLocation(), Sound.ENTITY_BAT_DEATH);
        else
            Utils.playSound(block.getLocation(), Sound.ENTITY_PUFFER_FISH_STING);

        event.setCancelled(true);
    }

    @Override
    protected void onPlayerDamageWithItem(WLPlayerDamageEvent event) { }

    @Override
    protected void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event) { }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) { }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) { }
}
