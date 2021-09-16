package sk.westland.world.slimefun.blocks;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.WestLand;
import sk.westland.core.enums.EBlockStorage;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.services.SlimefunService;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.UUID;

public class MobTeleporter extends SimpleSlimefunItem<BlockTicker> {

    private final int STATUS_SLOT = 13;
    private long uniqueTick;
    private final int minDistance = 1,
                        maxDistance = 5;

    public MobTeleporter(SlimefunService slimefunService) {
        super(slimefunService.getItemGroup(), new SlimefunItemStack("MOB_TELEPORTER",
                        new ItemBuilder(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
                                .setName("§8Mob teleporter").setLore("", "§7Teleportuje mobov na konkrétnu pozíciu", "§7potréba kliknúť s Block Locatorom,", "§7pre uloženie pozicie do bloku!", "").build()),
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                        SlimefunItems.ADVANCED_CIRCUIT_BOARD,    SlimefunItems.ELECTRO_MAGNET,   SlimefunItems.CARBON_CHUNK,
                        SlimefunItems.LIGHTNING_RUNE,   new ItemStack(Material.NETHERITE_BLOCK),    SlimefunItems.LIGHTNING_RUNE,
                        SlimefunItems.CARBON,    SlimefunItems.ANDROID_MEMORY_CORE,   SlimefunItems.BATTERY
                });


        new BlockMenuPreset(getId(), "§8Mob teleporter") {

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
                                        "§7Pozícia uložená na: §8" + (x != -1 ? ( "§7X:§8" + x + "§7, Y:§8" + y + "§7, Z:§8" + z ) : "§8Žiadna"),
                                        "§7Vzdialenosť teleportéra: §8" + BlockStorage.getLocationInfo(blockLocation, EBlockStorage.DISTANCE.getName()) + " §7blokov",
                                        "")
                        .build());
                menu.addMenuClickHandler(10, (p, slot, item, action) -> {
                    newInstance(menu, block);
                    return false;
                });

                menu.replaceExistingItem(16, new ItemBuilder(Material.PAPER)
                        .setName("§aVzdialenosť teleportéra: " + BlockStorage.getLocationInfo(blockLocation, EBlockStorage.DISTANCE.getName()))
                        .setLore("",
                                "§7Minimálna/Maximálna vzdialenosť je §8" + minDistance + "§7/§8" + maxDistance + " §7blokov",
                                "",
                                "§7Pre pridanie stlač §8RMB",
                                "§7Pre odobranie stlač §8LMB",
                                "")
                        .build());
                menu.addMenuClickHandler(16, new AdvancedMenuClickHandler() {
                    @Override
                    public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
                        return false;
                    }

                    @Override
                    public boolean onClick(InventoryClickEvent event, Player layer, int slot, ItemStack cursor, ClickAction action) {
                       if(event.isRightClick()) {
                            int distance = Integer.parseInt(BlockStorage.getLocationInfo(blockLocation, EBlockStorage.DISTANCE.getName()));
                            if((distance+1) <= maxDistance) {
                                BlockStorage.addBlockInfo(block, EBlockStorage.DISTANCE.getName(), "" + (distance + 1));

                            }
                        }
                        if(event.isLeftClick()) {
                            int distance = Integer.parseInt(BlockStorage.getLocationInfo(blockLocation, EBlockStorage.DISTANCE.getName()));
                            if((distance-1) >= minDistance) {
                                BlockStorage.addBlockInfo(block, EBlockStorage.DISTANCE.getName(), "" + (distance - 1));

                            }
                        }

                        newInstance(menu, block);
                        return false;
                    }
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

    @Override
    public void preRegister() {
        super.preRegister();
        addItemHandler(onBlockPlace());
    }

    private BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@NotNull BlockPlaceEvent e) {
                Block block = e.getBlock();

                BlockStorage.addBlockInfo(block, EBlockStorage.OWNER.getName(), e.getPlayer().getUniqueId().toString());
                BlockStorage.addBlockInfo(block, EBlockStorage.ACTIVE.getName(), EBlockStorage.DISABLED.getName());
                BlockStorage.addBlockInfo(block, EBlockStorage.DISTANCE.getName(), "1");
            }
        };
    }

    @NotNull
    @Override
    public BlockTicker getItemHandler() {
        return new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return false;
            }

            @Override
            public void tick(Block block, SlimefunItem item, Config data) {

                try {
                    MobTeleporter.this.tick(block, item, data);
                }catch (NoClassDefFoundError ignored) {
                    try {
                        Class.forName("sk.westland.world.slimefun.blocks.MobTeleporter");
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

    private void tick(Block block, SlimefunItem item, Config data) {
        if (uniqueTick % 10 != 0) {
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

        if(BlockStorage.getLocationInfo(blockLocation, "x") == null)
            return;

        double x = Double.parseDouble(BlockStorage.getLocationInfo(blockLocation, "x"));
        double y = Double.parseDouble(BlockStorage.getLocationInfo(blockLocation, "y"))+1;
        double z = Double.parseDouble(BlockStorage.getLocationInfo(blockLocation, "z"));
        String worldName = BlockStorage.getLocationInfo(blockLocation, "world");
        World world = Bukkit.getWorld(worldName);

        if(world == null) {
            return;
        }

        Location bLoc = new Location(world, x, y, z);

        if(!world.getName().equals(blockLocation.getWorld().getName()))
            return;

        if(!bLoc.isChunkLoaded()) {
            return;
        }

        if(bLoc.distance(block.getLocation()) > 30)
            return;

        int distance = Integer.parseInt(BlockStorage.getLocationInfo(blockLocation, EBlockStorage.DISTANCE.getName()));
        if(hasPermission(block, bLoc)) {
            Bukkit.getScheduler().runTask(WestLand.getInstance(), () -> {
                Collection<Entity> entities =  blockLocation.getNearbyEntities(distance,1,distance);

                if(entities.size() <= 0) {
                    return;
                }

                entities.forEach((entity) -> {
                    switch(entity.getType()) {
                        case ENDER_DRAGON, WITHER, WITHER_SKELETON, VILLAGER, SHULKER, SHULKER_BULLET -> {
                            return;
                        }
                    }

                    entity.teleportAsync(bLoc);
                });

            });

        }
    }

    @ParametersAreNonnullByDefault
    private boolean hasPermission(Block block, Location targetLoc) {
        String owner = BlockStorage.getLocationInfo(block.getLocation(), EBlockStorage.OWNER.getName());
        if (owner == null) {
            /*
             * If no owner was set, then we will fallback to the previous behaviour:
             * Allowing block placers to bypass protection, newly placed Mob Teleporter
             * will respect protection plugins.
             */
            return true;
        }

        // Get the corresponding OfflinePlayer
        OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(owner));
        return Slimefun.getProtectionManager().hasPermission(player, targetLoc, Interaction.INTERACT_ENTITY);
    }
}
