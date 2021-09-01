package sk.westland.world.items.tools;

import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;
import sk.westland.core.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Component
public class BetterShovel extends CustomItem implements Listener, Craftable {

    private final String METADATA_KEY = "last_dig_direction";

    @Override
    public CraftingRecipe getCraftingRecipe() {
        return new CraftingRecipe(itemID(), RecipeType.Block, getItem())
                .shape("ASA", "SSS", "AAS")
                .setIngredient('A', Material.AIR)
                .setIngredient('S', Material.STONE);
    }

    @Override
    public ItemStack getItem() {
        return customItemStack = new ItemBuilder(Material.DIAMOND_SHOVEL)
                .setName("§eExcavator shovel")
                .setModelId(getModelID())
                .setNbt_Int("radius", 3)
                .setCustomItem(this)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .setLore("", "§7Ničí blocky v radiuse 3x3", "").build();
    }

    @Override
    public int getModelID() {
        return 1;
    }

    @Override
    public String itemID() {
        return "excavatorShovel";
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        recipeService.registerRecipe(getCraftingRecipe());

    }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        EnumWrappers.Direction direction = EnumWrappers.Direction.valueOf(String.valueOf(player.getMetadata(METADATA_KEY).get(0).value()));
        // Nastavenie metadát prebieha v  in Hammer.class

        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
        List<Block> blocks = getSquare(event.getBlock(), direction);
        for(Block block : blocks) {
            block.breakNaturally(itemStack, true);
        }

        int level = itemStack.getEnchantmentLevel(Enchantment.DURABILITY);
        short durability = 4;
        switch (level) {
            case 0: {
                durability = 4;
                break;
            }
            case 1: {
                if(Utils.BaseMath.isInPercent(80)) // 20%
                    break;
                durability = 3;
                break;
            }
            case 2: {
                if(Utils.BaseMath.isInPercent(70)) // 30%
                    break;
                durability = 2;
                break;
            }
            case 3: {
                if(Utils.BaseMath.isInPercent(60)) // 40%
                    break;
                durability = 1;
                break;
            }
        }
        player.getInventory().setItemInMainHand(new ItemBuilder(itemStack).applyDurability(durability).build());
    }

    @Override
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) {

    }

    @Override
    protected void onPlayerDamageWithItem(WLPlayerDamageEvent event) {

    }

    @Override
    protected void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event) {

    }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) {}

    private List<Block> getBlocks(Location base, int changeX, int changeY, int changeZ) {
        List<Block> blocks = new ArrayList<>();
        for (int x = base.getBlockX() - changeX; x <= base.getBlockX() + changeX; x++) {
            for (int y = base.getBlockY() - changeY; y <= base.getBlockY() + changeY; y++) {
                for (int z = base.getBlockZ() - changeZ; z <= base.getBlockZ() + changeZ; z++) {
                    Location loc = new Location(base.getWorld(), x, y, z);
                    Block b = loc.getBlock();
                    Material mat = b.getType();
                    if (!blockedMaterial(mat,
                            Material.AIR, Material.BEDROCK, Material.BEACON,
                            Material.DISPENSER, Material.DROPPER, Material.PISTON,
                            Material.STICKY_PISTON, Material.REDSTONE_LAMP,
                            Material.HOPPER, Material.REDSTONE_WIRE, Material.REDSTONE_TORCH,
                            Material.OBSERVER, Material.END_GATEWAY,
                            Material.END_PORTAL, Material.END_PORTAL_FRAME, Material.ENDER_CHEST,
                            Material.NETHER_PORTAL))
                        blocks.add(b);
                }
            }
        }
        return blocks;
    }

    private boolean blockedMaterial(Material mat, Material... blockedMaterial) {
        for (Material material : blockedMaterial) {
            if(mat == material)
                return true;
        }
        return false;
    }

    private List<Block> getSquare(Block b, EnumWrappers.Direction face) {
        List<Block> blocks = new ArrayList<>();
        switch (face) {
            case EAST:
            case WEST:
                blocks.addAll(getBlocks(b.getLocation(), 0, 1, 1));
                break;
            case NORTH:
            case SOUTH:
                blocks.addAll(getBlocks(b.getLocation(), 1, 1, 0));
                break;
            case UP:
            default:
                blocks.addAll(getBlocks(b.getLocation(), 1, 0, 1));
                break;
        }
        return blocks;
    }
}
