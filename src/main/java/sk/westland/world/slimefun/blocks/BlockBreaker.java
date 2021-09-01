package sk.westland.world.slimefun.blocks;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.VanillaInventoryDropHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.UUID;

public class BlockBreaker extends SimpleSlimefunItem<BlockTicker> implements EnergyNetComponent {

    private int energyConsumption = 125;
    private long lifetime;

    public BlockBreaker(Category category) {
        super(category, new SlimefunItemStack("BLOCK_BREAKER", Material.DISPENSER,
                "§eBlock Breaker",
                "§7", "§7Ničí bloky ktoré", "§7sa nachádzajú pred dispenserom!", "§7"), RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[]{
                        new ItemStack(Material.COBBLESTONE), SlimefunItems.ALUMINUM_BRASS_INGOT, new ItemStack(Material.COBBLESTONE),
                        SlimefunItems.CARBON_CHUNK, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.CARBON_CHUNK,
                        new ItemStack(Material.COBBLESTONE), SlimefunItems.ALUMINUM_BRASS_INGOT, new ItemStack(Material.COBBLESTONE),
                });

        addItemHandler(onBlockPlace());
        //addItemHandler(onBlockDispense());
        addItemHandler(onBlockBreak());

        addItemHandler(new VanillaInventoryDropHandler<>(Dispenser.class));
        ItemUseHandler itemUseHandler = this::onItemUseRightClick;
        addItemHandler(itemUseHandler);


        new BlockMenuPreset(getId(), "§7Block Breaker") {

            @Override
            public void init() {
                for (int i = 0; i < 9; i++) {
                    addItem(i, new CustomItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ""));
                }
            }

            @Override
            public void newInstance(@NotNull BlockMenu menu, @NotNull Block b) {
                super.newInstance(menu, b);
            }

            @Override
            public boolean canOpen(@NotNull Block b, @NotNull Player p) {
                return BlockStorage.getLocationInfo(b.getLocation(), EBlockStorage.OWNER.getName()).equals(p.getName());
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }
        };
    }

    private ItemUseHandler onItemUseRightClick(PlayerRightClickEvent playerRightClickEvent) {
        return event -> {
            event.getClickedBlock().ifPresent(block -> {
                if(BlockStorage.hasBlockInfo(block))
                    playerRightClickEvent.cancel();
            });
        };
    }

/*
*     @Contract(pure = true)
    private @NotNull BlockDispenseHandler onBlockDispense() {
        return (e, dispenser, facedBlock, machine) -> {
            System.out.println("Block DIspense");
            e.setCancelled(true);

            if (!hasPermission(dispenser, facedBlock))
                return;

            System.out.println("!permission");

            if (!facedBlock.isEmpty() && dispenser.getInventory().getViewers().isEmpty()) {
                System.out.println("Schedule break");
                scheduleBreak(facedBlock, dispenser.getInventory(), e.getItem(), () -> {
                    System.out.println("Scheduled block break");
                    removeCharge(e.getBlock().getLocation(), energyConsumption);
                });
            }
        };
    }
* */

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
        return SlimefunPlugin.getProtectionManager().hasPermission(player, target, ProtectableAction.BREAK_BLOCK);
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
                BlockStorage.addBlockInfo(block, EBlockStorage.ACTIVE.getName(), "false");
            }
        };
    }

    private BlockBreakHandler onBlockBreak() {
        return new BlockBreakHandler(false, true) {
            @Override
            public void onPlayerBreak(BlockBreakEvent event, ItemStack item, List<ItemStack> drops) {
                System.out.println("has Block Info");

                Block block = event.getBlock();
                if(BlockStorage.hasBlockInfo(block)) {
                    BlockStorage.clearBlockInfo(block);
                    System.out.println("Delete block info");
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
        SlimefunPlugin.runSync(() -> {
            System.out.println("scheduleBreak");
            System.out.println("Block: " + block == null ? " NULL" : block.getType());
            // Make sure the Block has not been occupied yet
            if (!block.isEmpty()) {
                // Only remove 1 item.

                block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
                block.breakNaturally();
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
        if (lifetime % 20 != 0) {
            return;
        }

        Location blockLocation = block.getLocation();
        if (BlockStorage.getLocationInfo(blockLocation, "enabled").equals("false")) {
            return;
        }

        if (getCharge(blockLocation) < getEnergyConsumption()) {
            return;
        }

        removeCharge(blockLocation, energyConsumption);
        Dispenser dispenser;
        Directional directional;

        if(!(block.getState() instanceof Dispenser))
            return;

        dispenser = (Dispenser) block.getState();
        if(!(dispenser instanceof Directional))
            return;

        directional = (Directional) dispenser;

        Block targetBlock = block.getRelative(directional.getFacing());
        if(!hasPermission(dispenser, targetBlock))
            return;

        scheduleBreak(targetBlock, () -> {
            System.out.println("Breaked :)");
        });
    }
}
