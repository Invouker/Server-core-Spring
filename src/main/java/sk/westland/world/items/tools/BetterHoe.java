package sk.westland.world.items.tools;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;
import sk.westland.core.utils.Utils;

@Component
public class BetterHoe extends CustomItem implements Listener, Craftable {

    @Override
    public NamespacedKey getNamespacedKey(Plugin plugin) {
        return new NamespacedKey(plugin, "betterHoe");
    }

    @Override
    public CraftingRecipe getCraftingRecipe(Plugin plugin) {
        return new CraftingRecipe(getNamespacedKey(plugin), RecipeType.Block, getItem())
                .shape(" S ", " S ", " S ")
                .setIngredient('S', Material.COBBLESTONE);
    }

    @Override
    public ItemStack getItem() {
        return customItemStack = new ItemBuilder(Material.NETHERITE_HOE)
                .setName("§bBetter Hoe")
                .setCustomItem(this)
                .setModelId(getModelID())
                .setNbt_Int("radius", 3)
                .setLore("", "§7Zbiera úrodu v radiusu 3x3", "").build();
    }

    @Override
    public int getModelID() {
        return 0;
    }

    @Override
    public String itemID() {
        return "betterHoe";
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        getCraftingRecipe(WestLand.getInstance()).register();
    }

    @Override
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) {
        switch (event.getAction()) {
            case PHYSICAL:
            case LEFT_CLICK_AIR:
            case RIGHT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                return;
        }

        Player player = event.getPlayer();

        Block block = event.getClickedBlock();
        int radius = Nbt.getNbt_Int(event.getItem(), "radius", 1);

        int minX = block.getX() - radius, minZ = block.getZ() - radius;
        int maxX = block.getX() + radius, maxZ = block.getZ() + radius;

        int y = block.getY();
        World world = block.getLocation().getWorld();

        int count = 0;

        for (int x = Math.min(minX, maxX); x < Math.max(minX, maxX); x++) {
            for (int z = Math.min(minZ, maxZ); z < Math.max(minZ, maxZ); z++) {
                Location location = new Location(world, x, y, z);
                Block seedBlock = world.getBlockAt(location);

                if (seedBlock.getType() != block.getType())
                    continue;

                boolean success = seed(seedBlock);
                if (success)
                    count++;
            }
        }

        if (event.getItem().hasItemMeta() && !event.getItem().getItemMeta().isUnbreakable() && count > 0) {
            ItemStack damagedItem = new ItemBuilder(event.getItem()).applyDurability((short) 1).build();
            player.getInventory().setItemInMainHand(damagedItem);
            Utils.playSound(player.getLocation(), Sound.ITEM_HOE_TILL, 4);

            Utils.playArmAnimation(player);
        }
        switch(block.getType()) {
            case WHEAT: {
                for(int x = 0; x < count; x++) {
                    player.getInventory().addItem(new ItemBuilder(Material.WHEAT_SEEDS).setAmount(Random.nextInt(2)).build());
                    player.getInventory().addItem(new ItemBuilder(Material.WHEAT).setAmount(Math.max(Random.nextInt(2), 1)).build());
                }
                break;
            }
            case POTATOES: {
                for(int x = 0; x < count; x++) {
                    player.getInventory().addItem(new ItemBuilder(Material.POTATO).setAmount(Random.nextInt(2)).build());
                }
                break;
            }
            case BEETROOTS: {
                for(int x = 0; x < count; x++) {
                    player.getInventory().addItem(new ItemBuilder(Material.BEETROOT_SEEDS).setAmount(Random.nextInt(2)).build());
                    player.getInventory().addItem(new ItemBuilder(Material.BEETROOT).setAmount(Math.max(Random.nextInt(2), 1)).build());
                }
                break;
            }
            case CARROTS: {
                for(int x = 0; x < count; x++) {
                    player.getInventory().addItem(new ItemBuilder(Material.CARROT).setAmount(Math.max(Random.nextInt(2), 1)).build());
                }
                break;
            }

            case SWEET_BERRY_BUSH: {
                for(int x = 0; x < count; x++) {
                    player.getInventory().addItem(new ItemBuilder(Material.SWEET_BERRIES).setAmount(Random.nextInt(2)).build());
                }
                break;
            }
        }
    }

    public boolean seed(Block seedBlock) {
        BlockData data = seedBlock.getBlockData();
        if (!(data instanceof Ageable))
            return false;

        Ageable ag = (Ageable) data;
        if(ag.getAge() != ag.getMaximumAge())
            return false;

        seedBlock.getDrops().clear();
        ag.setAge(0);
        seedBlock.setBlockData(data);
        return true;
    }

    @Override
    protected void onPlayerDamageWithItem(WLPlayerDamageEvent event) {

    }

    @Override
    protected void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event) {

    }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) {

    }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) {

    }
}
