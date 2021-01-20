package sk.westland.core.database.data;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.blocks.BlockLevel;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.utils.converter.InventoryItemConverter;
import sk.westland.core.utils.converter.LocationConverter;
import sk.westland.core.utils.converter.UUIDConverter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Table(name = "wl_block_data")
@Entity
public class BlockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "text")
    @Convert(converter = UUIDConverter.class)
    private UUID ownerUUID;

    private String ownerName;

    @Column(columnDefinition = "text")
    @Convert(converter = LocationConverter.class)
    private Location blockLocation;

    @Enumerated(value = EnumType.STRING)
    private BlockLevel blockLevel = BlockLevel.UNCOMMON;

    @Enumerated(value = EnumType.STRING)
    private BlockType blockType;

    @Convert(converter = InventoryItemConverter.class)
    private Map<Integer, ItemStack> items = new HashMap<>();

    public BlockData() { }

    public BlockData(String ownerName, UUID ownerUUID, Location blockLocation, BlockLevel blockLevel, BlockType blockType) {
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
        this.blockLocation = blockLocation;
        this.blockLevel = blockLevel;
        this.blockType = blockType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Location getBlockLocation() {
        return blockLocation;
    }

    public void setBlockLocation(Location blockLocation) {
        this.blockLocation = blockLocation;
    }

    public BlockLevel getBlockLevel() {
        return blockLevel;
    }

    public void setBlockLevel(BlockLevel blockLevel) {
        this.blockLevel = blockLevel;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public void setItems(Map<Integer, ItemStack> items) {
        this.items = items;
    }
}
