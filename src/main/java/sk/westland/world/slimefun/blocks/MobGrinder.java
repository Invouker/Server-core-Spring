package sk.westland.world.slimefun.blocks;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.enums.EBlockStorage;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.services.SlimefunService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MobGrinder extends SimpleSlimefunItem<BlockTicker> {

    private static final int STATUS_SLOT = 13;
    private long uniqueTick;

    public MobGrinder(SlimefunService slimefunService) {
        super(slimefunService.getItemGroup(), new SlimefunItemStack("MOB_GRINDER",
                        new ItemBuilder(Material.DROPPER)
                                .setName("§8Mob grinder").setLore("", "§7Zabíja mobov ktorý stoja", "§7pred blokom", "").build()),
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                        SlimefunItems.ADVANCED_CIRCUIT_BOARD,    SlimefunItems.ELECTRO_MAGNET,   SlimefunItems.CARBON_CHUNK,
                        SlimefunItems.LIGHTNING_RUNE,   new ItemStack(Material.NETHERITE_BLOCK),    SlimefunItems.LIGHTNING_RUNE,
                        SlimefunItems.CARBON,    SlimefunItems.ANDROID_MEMORY_CORE,   SlimefunItems.BATTERY
                });

        addItemHandler(onBlockBreak(), onBlockPlace());


        new BlockMenuPreset(getId(), "§8Mob grinder") {

            @Override
            public void init() {
                for (int i = 0; i < 27; i++) {
                   /* if(i == PICKAXE_SLOT) {
                        addItem(i, new ItemStack(Material.AIR), (p, slot, item, action) -> true);
                    } else
                    */
                    addItem(i, ChestMenuUtils.getBackground(), (p, slot, item, action) -> false);
                }
            }

            @Override
            public void newInstance(@NotNull BlockMenu menu, @NotNull Block block) {
                super.newInstance(menu, block);

                Location blockLocation = block.getLocation();
                double  x= -1,
                        y= -1,
                        z= -1;
                if(BlockStorage.getLocationInfo(blockLocation, "x") != null) {
                    x = Double.parseDouble(BlockStorage.getLocationInfo(blockLocation, "x"));
                    y = Double.parseDouble(BlockStorage.getLocationInfo(blockLocation, "y"))+1;
                    z = Double.parseDouble(BlockStorage.getLocationInfo(blockLocation, "z"));
                }

                String blockStatus = BlockStorage.getLocationInfo(block.getLocation(), EBlockStorage.ACTIVE.getName());
                menu.replaceExistingItem(10, new ItemBuilder(Material.PAPER)
                        .setName("§bInformácie")
                        .setLore("",
                                "§7Stav: " + (blockStatus.equals(EBlockStorage.DISABLED.getName()) ? "§cVypnutý" : "§aZapnutý"),
                                "")
                        .build());
                menu.addMenuClickHandler(10, (p, slot, item, action) -> {
                    newInstance(menu, block);
                    return false;
                });

                if(!BlockStorage.hasBlockInfo(block) ||
                        BlockStorage.getLocationInfo(block.getLocation(),
                                EBlockStorage.ACTIVE.getName()) == null ||
                        blockStatus.equals(EBlockStorage.DISABLED.getName())) {
                    menu.replaceExistingItem(STATUS_SLOT, new ItemBuilder(Material.RED_CONCRETE).setName("§aZapnúť").build());
                    menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
                        BlockStorage.addBlockInfo(block, EBlockStorage.ACTIVE.getName(),EBlockStorage.ENABLED.getName());
                        newInstance(menu, block);
                        return false;
                    });
                } else {
                    menu.replaceExistingItem(STATUS_SLOT, new ItemBuilder(Material.GREEN_CONCRETE).setName("§cVypnúť").build());
                    menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
                        BlockStorage.addBlockInfo(block, EBlockStorage.ACTIVE.getName(), EBlockStorage.DISABLED.getName());
                        newInstance(menu, block);
                        return false;
                    });
                }
            }

            @Override
            public boolean canOpen(@NotNull Block block, @NotNull Player player) {
                return BlockStorage.getLocationInfo(block.getLocation(), EBlockStorage.OWNER.getName()).equals(player.getUniqueId().toString()) || player.hasPermission("westland.menu.mobteleporter");
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return new int[0];
            }
        };
    }

    @NotNull
    @Override
    public BlockTicker getItemHandler() {
        return new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block block, SlimefunItem item, Config data) {
                try {
                    MobGrinder.this.tick(block, item, data);
                } catch (NoClassDefFoundError ignored) {
                    try {
                        Class.forName("sk.westland.world.slimefun.blocks.MobGrinder");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void uniqueTick() {
                super.uniqueTick();
                uniqueTick++;
            }
        };
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

    private void tick(Block block, SlimefunItem item, Config data) {
        if (uniqueTick % 12 != 5) {
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

        Dropper dropper;
        Directional directional;

        if(!(block.getState() instanceof Dropper)) {
            return;
        }

        dropper = (Dropper) block.getState();
        if(!(dropper.getBlockData() instanceof Directional)) {
            return;
        }

        directional = (Directional) dropper.getBlockData();

        BlockFace blockFace = directional.getFacing();
        Block targetBlock = block.getRelative(blockFace);

        scheduleKill(block, targetBlock, () -> {});
    }

    private void scheduleKill(Block dropper, Block targetBlock, Runnable runnable) {
        Collection<LivingEntity> collection = targetBlock.getLocation().getNearbyLivingEntities(1.5, 1);
        List<EntityType> entityTypes =
                Arrays.asList(EntityType.WITHER, EntityType.WITHER_SKULL, EntityType.DRAGON_FIREBALL, EntityType.WITHER_SKELETON,
                        EntityType.ENDER_DRAGON, EntityType.ENDER_CRYSTAL, EntityType.ARMOR_STAND, EntityType.DROPPED_ITEM,
                        EntityType.MINECART, EntityType.MINECART_CHEST, EntityType.MINECART_COMMAND, EntityType.MINECART_HOPPER,
                        EntityType.MINECART_FURNACE, EntityType.MINECART_MOB_SPAWNER, EntityType.MINECART_TNT, EntityType.EXPERIENCE_ORB,
                        EntityType.GLOW_ITEM_FRAME, EntityType.ITEM_FRAME, EntityType.ENDER_SIGNAL, EntityType.ENDER_PEARL, EntityType.THROWN_EXP_BOTTLE,
                        EntityType.SMALL_FIREBALL,EntityType.FIREBALL, EntityType.FIREWORK, EntityType.GIANT);

        collection.stream().filter((entity) -> !entityTypes.contains(entity.getType())).limit(5)
                .forEach(livingEntity -> livingEntity.setHealth(0));
        runnable.run();
    }

}


