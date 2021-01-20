package sk.westland.core.blocks;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.database.data.BlockData;
import sk.westland.core.items.CustomItem;
import sk.westland.core.services.BlockService;
import sk.westland.world.items.Materials;

import java.util.Map;
import java.util.UUID;

public abstract class CustomBlock implements Listener {

    protected String owner;
    protected UUID ownerUUID;
    protected Location location;
    protected BlockLevel blockLevel;
    protected BlockType blockType;
    protected BlockData blockData;
    protected long lastUpdated;
    protected ArmorStand armorStand;
    protected Block block;
    protected Map<Integer, ItemStack> items;
    protected static int id = 0;

    protected CustomItem customItem;

    protected boolean canBeActive = true;

    protected CustomBlock(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockType blockType, CustomItem customItem) {
        this(owner, ownerUUID, location, blockLevel, customItem, new BlockData(owner, ownerUUID, location, blockLevel, blockType));
    }

    protected CustomBlock(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, CustomItem customItem, BlockData blockData) {
        this.owner = owner;
        this.ownerUUID = ownerUUID;
        this.location = location;
        this.blockLevel = blockLevel;
        this.blockData = blockData;
        this.blockType = blockData.getBlockType();
        this.customItem = customItem;

        lastUpdated = System.currentTimeMillis();

        block = location.getWorld().getBlockAt(location);

        items = blockData.getItems();
        setupArmorStand();
        onBlockLoad();
    }

    public CustomBlock(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockData blockData, Materials.Items blockBreaker) {
    }

    private void setupArmorStand() {
        Location armorStandLoc = location.clone();

        armorStandLoc.add(.5, -.3, .5);
        armorStand = location.getWorld().spawn(armorStandLoc, ArmorStand.class);

        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setCustomName("[BLOCK]");
    }

    public void remove() {
        onBlockUnload();
    }

    public abstract void onBlockLoad();
    public abstract void onBlockUpdate();
    public abstract void onBlockTimeUpdate();
    public abstract void onBlockUnload();
    public abstract void onBlockInteract(PlayerInteractEvent event, BlockService blockService);
    public abstract void onRedstoneActivate(BlockRedstoneEvent event);

    public void blockUpdate() {
        if(!location.getChunk().isLoaded())
            return;

        onBlockTimeUpdate();
        if(lastUpdated + (1000L * blockLevel.getCooldown()) <= System.currentTimeMillis()) {
            if(canBeActive) onBlockUpdate();
            setLastUpdated(System.currentTimeMillis());
        }
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public BlockLevel getBlockLevel() {
        return blockLevel;
    }

    public void setBlockLevel(BlockLevel blockLevel) {
        this.blockLevel = blockLevel;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public void setBlockData(BlockData blockData) {
        this.blockData = blockData;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Map<Integer, ItemStack> getItems() { return items; }

    public void setItems(Map<Integer, ItemStack> items) { this.items = items; }

    public boolean isCanBeActive() {
        return canBeActive;
    }

    public void setCanBeActive(boolean canBeActive) {
        this.canBeActive = canBeActive;
    }

    public CustomItem getCustomItem() {
        return customItem;
    }
}
