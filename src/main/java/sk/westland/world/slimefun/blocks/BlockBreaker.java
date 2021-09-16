package sk.westland.world.slimefun.blocks;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.enums.EBlockStorage;
import sk.westland.core.items.ItemBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.UUID;

public class BlockBreaker extends SimpleSlimefunItem<BlockTicker> implements EnergyNetComponent {

    private final int energyConsumption = 125;
    private long lifetime;
    private BlockMenu blockMenu;
    private final int PICKAXE_SLOT = 16;
    private final int STATUS_SLOT = 13;

    public BlockBreaker(ItemGroup itemGroup) {
        super(itemGroup, new SlimefunItemStack("BLOCK_BREAKER", Material.DISPENSER,
                "§eBlock Breaker",
                "§7", "§7Ničí bloky ktoré", "§7sa nachádzajú pred dispenserom!", "§7"), RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        new ItemStack(Material.COBBLESTONE), SlimefunItems.ALUMINUM_BRASS_INGOT, new ItemStack(Material.COBBLESTONE),
                        SlimefunItems.CARBON_CHUNK, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.CARBON_CHUNK,
                        new ItemStack(Material.COBBLESTONE), SlimefunItems.ALUMINUM_BRASS_INGOT, new ItemStack(Material.COBBLESTONE),
                });

        addItemHandler(onBlockPlace());
        addItemHandler(onBlockBreak());

        //addItemHandler(new VanillaInventoryDropHandler<>(Dispenser.class));


        new BlockMenuPreset(getId(), "§8Block Breaker") {

            @Override
            public void init() {
                for (int i = 0; i < 27; i++) {
                   /* if(i == PICKAXE_SLOT) {
                        addItem(i, new ItemStack(Material.AIR), (p, slot, item, action) -> true);
                    } else
                    */
                    addItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, "§r").build(), (p, slot, item, action) -> false);
                }
            }

            @Override
            public void newInstance(@NotNull BlockMenu menu, @NotNull Block block) {
                super.newInstance(menu, block);
                if(!BlockStorage.hasBlockInfo(block) ||
                        BlockStorage.getLocationInfo(block.getLocation(),
                                EBlockStorage.ACTIVE.getName()) == null ||
                        BlockStorage.getLocationInfo(block.getLocation(),
                                EBlockStorage.ACTIVE.getName()).equals(EBlockStorage.DISABLED.getName())) {
                    menu.replaceExistingItem(STATUS_SLOT, new ItemBuilder(Material.RED_CONCRETE).setName("Zapnúť").build());
                    menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
                        BlockStorage.addBlockInfo(block, EBlockStorage.ACTIVE.getName(),EBlockStorage.ENABLED.getName());
                        blockMenu = menu;
                        newInstance(menu, block);
                        return false;
                    });
                } else {
                    menu.replaceExistingItem(STATUS_SLOT, new ItemBuilder(Material.GREEN_CONCRETE).setName("Vypnúť").build());
                    menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
                        BlockStorage.addBlockInfo(block, EBlockStorage.ACTIVE.getName(), EBlockStorage.DISABLED.getName());
                        blockMenu = menu;
                        newInstance(menu, block);
                        return false;
                    });
                }
            }

            @Override
            public boolean canOpen(@NotNull Block block, @NotNull Player player) {
                return BlockStorage.getLocationInfo(block.getLocation(), EBlockStorage.OWNER.getName()).equals(player.getUniqueId().toString());
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }
        };
    }

    @ParametersAreNonnullByDefault
    private boolean hasPermission(Dispenser dispenser, Block target) {
        String owner = BlockStorage.getLocationInfo(dispenser.getLocation(), EBlockStorage.OWNER.getName());
        if (owner == null) {
            /*
             * If no owner was set, then we will fallback to the previous behaviour:
             * Allowing block placers to bypass protection, newly placed Block placers
             * will respect protection plugins.
             */
            return true;
        }

        // Get the corresponding OfflinePlayer
        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(owner));
        return Slimefun.getProtectionManager().hasPermission(player, target, Interaction.BREAK_BLOCK);
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    private BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@NotNull BlockPlaceEvent e) {
                Block block = e.getBlock();

                BlockStorage.addBlockInfo(block, EBlockStorage.OWNER.getName(), e.getPlayer().getUniqueId().toString());
                BlockStorage.addBlockInfo(block, EBlockStorage.ACTIVE.getName(), EBlockStorage.DISABLED.getName());
            }
        };
    }

    private BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(BlockBreakEvent event, ItemStack item, List<ItemStack> drops) {

                Block block = event.getBlock();
                if(BlockStorage.hasBlockInfo(block)) {
                    BlockStorage.clearBlockInfo(block);
                    drops.clear();
                }
            }
        };
    }

    @NotNull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return 2048;
    }

    @ParametersAreNonnullByDefault
    private void scheduleBreak(Block block, Runnable runnable) {
        // We need to delay this due to Dispenser-Inventory synchronization issues in Spigot.
        Slimefun.runSync(() -> {

            if(BlockStorage.hasBlockInfo(block))
                return;

            // Make sure the Block has not been occupied yet
            if (!block.isEmpty()) {

                switch(block.getType()) {
                    case END_PORTAL, BEDROCK, CHEST, ENDER_CHEST, JUKEBOX, NOTE_BLOCK, BEACON, ENCHANTING_TABLE,
                            CONDUIT, TRAPPED_CHEST, RESPAWN_ANCHOR, OBSIDIAN, CRYING_OBSIDIAN, BREWING_STAND,
                            DROPPER, HOPPER, DISPENSER, PISTON, STICKY_PISTON, NETHER_PORTAL, END_PORTAL_FRAME,
                            JIGSAW, BARRIER, COMMAND_BLOCK, CHAIN_COMMAND_BLOCK, REPEATING_COMMAND_BLOCK,
                            STRUCTURE_BLOCK, STRUCTURE_VOID -> {
                        return;
                    }
                }

                if(block.getType().toString().endsWith("carpet") || block.getType().toString().endsWith("shulker_box") || block.getType().toString().endsWith("bed"))
                    return;

               /*
                ItemStack pickaxe = blockMenu.getItemInSlot(PICKAXE_SLOT);

                if(pickaxe == null || pickaxe.getType() == Material.AIR)
                    return;

                System.out.println("pickaxe.getType().toString().toLowerCase(): " + pickaxe.getType().toString().toLowerCase());
                if(!pickaxe.getType().toString().toLowerCase().endsWith("pickaxe"))
                    return;
*/
                block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
                block.breakNaturally();
                runnable.run();
            }
        }, 2L);
    }


    @NotNull
    @Override
    public BlockTicker getItemHandler() {
        return new BlockTicker() {
            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                BlockBreaker.this.tick(b, item, data);
            }

            @Override
            public void uniqueTick() {
                super.uniqueTick();
                lifetime++;
            }

            @Override
            public boolean isSynchronized() {
                return true;
            }
        };
    }

    private void tick(Block block, SlimefunItem item, Config data) {
        if (lifetime % 8 != 0) {
            return;
        }

        Location blockLocation = block.getLocation();
        if (!BlockStorage.hasBlockInfo(blockLocation) || BlockStorage.getLocationInfo(blockLocation, EBlockStorage.ACTIVE.getName()) == null) {
            BlockStorage.addBlockInfo(block, EBlockStorage.ACTIVE.getName(), EBlockStorage.DISABLED.getName());
            return;
        }

        if(BlockStorage.getLocationInfo(blockLocation, EBlockStorage.ACTIVE.getName()).equals(EBlockStorage.DISABLED.getName())) {
            return;
        }

        if (getCharge(blockLocation) < getEnergyConsumption()) {
            //return;
        } //TODO: REENABLE THIS

        removeCharge(blockLocation, getEnergyConsumption());
        Dispenser dispenser;
        Directional directional;

        if(!(block.getState() instanceof Dispenser)) {
            return;
        }

        dispenser = (Dispenser) block.getState();
        if(!(dispenser.getBlockData() instanceof Directional)) {
            return;
        }

        directional = (Directional) dispenser.getBlockData();

        Block targetBlock = block.getRelative(directional.getFacing());
        if(!hasPermission(dispenser, targetBlock)) {
            return;
        }

        scheduleBreak(targetBlock, () -> {});
    }
}
