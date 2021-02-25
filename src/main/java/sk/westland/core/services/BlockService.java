package sk.westland.core.services;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.blocks.BlockLevel;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.blocks.CustomBlock;
import sk.westland.world.blocks.type.BlockBreaker;
import sk.westland.world.blocks.type.BlockPlacer;
import sk.westland.core.database.data.BlockData;
import sk.westland.core.database.data.BlockDataRepository;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.utils.RunnableDelay;

import java.util.*;

@Service
public class BlockService implements Listener {

    @Autowired
    private BlockDataRepository blockDataRepository;

    private HashMap<Location, CustomBlock> blockHashMap = new HashMap<>();

    private int LOADED_BLOCKS = 0;

    @EventHandler(priority = EventPriority.HIGH)
    private void onPluginEnable(PluginEnableEvent event) {
        List<BlockData> blockDataList = blockDataRepository.findAll();

        Bukkit.getScheduler().runTaskTimer(event.getWestLand(), this::blockUpdate, RunnableDelay.DELAY(), 10l); // Must be before check size of blockDataList
        Bukkit.getScheduler().runTaskTimerAsynchronously(event.getWestLand(), this::saveBlocksData, RunnableDelay.DELAY(), 20*30l);

        if(blockDataList.isEmpty())
            return;

        for(BlockData blockData : blockDataList) {
            if(blockData.getOwnerName() == null || blockData.getOwnerUUID() == null || blockData.getBlockLocation() == null) {
                blockDataRepository.delete(blockData);
                return;
            }

            if(blockData.getBlockLevel() == null)
                blockData.setBlockLevel(BlockLevel.UNCOMMON);

            CustomBlock block = null;
            LOADED_BLOCKS++;
            switch(blockData.getBlockType()) {
                case BLOCK_PLACER: {
                    //(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockData blockData, BlockService blockService)
                    block =
                            new BlockPlacer(

                            blockData.getOwnerName(),

                            blockData.getOwnerUUID(),

                            blockData.getBlockLocation(),

                            blockData.getBlockLevel(),

                            blockData,

                            this);
                    break;
                }
                case BLOCK_BREAKER: {
                    block = new BlockBreaker(blockData.getOwnerName(), blockData.getOwnerUUID(), blockData.getBlockLocation(), blockData.getBlockLevel(), blockData, this);
                    break;
                }
            }
            if(block == null)
                return;

            blockHashMap.put(blockData.getBlockLocation(), block);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onBlockUnload(PluginDisableEvent event) {
        saveBlocksData();
        blockHashMap.forEach((loc, block) -> block.onBlockUnload());
    }
    private List<Location> unregisterBlocks = new ArrayList<>();
    private void blockUpdate() {
        for(Map.Entry<Location, CustomBlock> entry : blockHashMap.entrySet()) {
            entry.getValue().blockUpdate();
        }
    }

    public BlockData saveBlockData(BlockData blockData) {
        return blockDataRepository.save(blockData);
    }

    public List<BlockData> saveBlocksData() {
        List<BlockData> blockDataList = new ArrayList<>();
        blockHashMap.forEach((location, block) -> {
            blockDataList.add(block.getBlockData());
        });

        return blockDataRepository.saveAll(blockDataList);
    }

    public boolean registerNewBlockPlacer(String name, UUID uuid, Location location, BlockLevel blockLevel) {
        return this.registerNewBlock(new BlockPlacer(name, uuid, location, blockLevel, this));
    }

    public boolean registerNewBlock(CustomBlock block) {
        if(blockHashMap.containsKey(block.getLocation()))
            return false;
        blockHashMap.put(block.getLocation(), block);
        saveBlockData(block.getBlockData());
        return true;
    }

    public CustomBlock blockUnregister(Location location) {
        if(!blockHashMap.containsKey(location))
            return null;

        CustomBlock customBlock = blockHashMap.get(location);
        for(Map.Entry<Integer, ItemStack> entry : customBlock.getItems().entrySet()) {
            if(entry.getValue() != null && entry.getValue().getType() != Material.AIR)
                location.getWorld().dropItem(location, entry.getValue());
        }

        customBlock.onBlockUnload();

        blockDataRepository.delete(customBlock.getBlockData());
        blockHashMap.remove(location);

        return customBlock;
    }

    public boolean isCustomBlock(String world, int x, int y, int z) {
        return isCustomBlock(new Location(Bukkit.getWorld(world), x, y, z));
    }

    public boolean isCustomBlock(Location location) {
       return blockHashMap.containsKey(location);
    }

    public int getLOADED_BLOCKS() {
        return LOADED_BLOCKS;
    }

    @EventHandler
    private void onInteractWithBlock(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Block block = event.getClickedBlock();
        if(block == null)
            return;

        Location location = block.getLocation();

        if(!validBlockType(block.getType()))
            return;

        if(blockHashMap.containsKey(location))
            blockHashMap.get(location).onBlockInteract(event, this);
    }

    public boolean validBlockType(Material material) {
        for(BlockType blockType : BlockType.values()) {
            if(blockType.getMaterial() == material)
                return true;
        }

        return false;
    }


    @EventHandler(priority = EventPriority.HIGH)
    private void onBlockRedstone(BlockRedstoneEvent event) {
        Block block = null;
        EnumSet<BlockFace> checkedBlockFace = EnumSet.of(
                BlockFace.DOWN,
                BlockFace.UP,
                BlockFace.WEST,
                BlockFace.SOUTH,
                BlockFace.NORTH,
                BlockFace.EAST);

        for (int i = 0; i < checkedBlockFace.size(); i++) {
            BlockFace blockFace = (BlockFace) checkedBlockFace.toArray()[i];

            if (event.getBlock().getRelative(blockFace).getType() == Material.DISPENSER) {
                block = event.getBlock().getRelative(blockFace);
                break;
            }

            if(i == checkedBlockFace.size())
                return;
        }

        for(Map.Entry<Location, CustomBlock> entry : blockHashMap.entrySet()) {
            CustomBlock customBlock = entry.getValue();

            if(block == null)
                return;

            if(block.getType() != Material.DISPENSER)
                return;

            if(event.getOldCurrent() > event.getNewCurrent())
                return;

            customBlock.onRedstoneActivate(event);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onInit(BlockBreakEvent event) {
        if(event.getBlock().getType() != Material.DISPENSER || event.getBlock().getType() != Material.DROPPER)
            return;

        Block block = event.getBlock();
        CustomBlock customBlock = this.blockUnregister(block.getLocation());
        if(customBlock != null)
            block.setType(Material.AIR);

        Location location = customBlock.getLocation();
        if(location.getChunk().isLoaded() && event.getPlayer().getGameMode() == GameMode.SURVIVAL)
            location.getWorld().dropItem(location, customBlock.getCustomItem().getItem());

        event.setCancelled(true);
    }

    public BlockDataRepository getBlockDataRepository() {
        return blockDataRepository;
    }
}

