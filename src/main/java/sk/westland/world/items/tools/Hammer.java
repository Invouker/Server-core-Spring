package sk.westland.world.items.tools;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class Hammer extends CustomItem implements Listener, Craftable {

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
        return customItemStack = new ItemBuilder(Material.DIAMOND_PICKAXE)
                .setName("§bDiamond Hammer")
                .setModelId(getModelID())
                .setNbt_Int("radius", 3)
                .setCustomItem(this)
                .setLore("", "§7Ničí blocky v radiusu 3x3", "").build();
    }

    @Override
    public int getModelID() {
        return 1;
    }

    @Override
    public String itemID() {
        return "hammer";
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) {
        recipeService.registerRecipe(getCraftingRecipe());

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(WestLand.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent packetEvent) {



                if (packetEvent.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
                    PacketContainer packetContainer = packetEvent.getPacket();

                    packetEvent.getPlayer().setMetadata(METADATA_KEY, new FixedMetadataValue(event.getWestLand(), packetContainer.getDirections().read(0)) {

                    });
                }
            }
        });
    }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        EnumWrappers.Direction direction = EnumWrappers.Direction.valueOf((String) player.getMetadata(METADATA_KEY).get(0).value());

        if(direction == null)
            return;

        List<Block> blocks = getSquare(event.getBlock(), direction);
        for(Block block : blocks) {
            block.breakNaturally();
        }
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
