package sk.westland.world.slimefun;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.core.services.SlimefunService;
import sk.westland.core.utils.ChatInfo;

public class BlockLocator extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {


    public BlockLocator(SlimefunService slimefunService) {
        super(slimefunService.getItemGroup(), new SlimefunItemStack("BLOCK_LOCATOR",
                        new ItemBuilder(Material.PAPER)
                                .setName("§8Block Locator").setLore("", "§7Uloží pozíciu a následne ju možno použiť", "§7v Mob Teleportery.", "").build()),
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                        new ItemStack(Material.PAPER),    SlimefunItems.ELECTRO_MAGNET,   new ItemStack(Material.PAPER),
                        new ItemStack(Material.PAPER),   SlimefunItems.BASIC_CIRCUIT_BOARD,    new ItemStack(Material.PAPER),
                        new ItemStack(Material.PAPER),    SlimefunItems.ANDROID_MEMORY_CORE,   new ItemStack(Material.PAPER)
                });
    }

    @NotNull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {

            if(e.getHand() != EquipmentSlot.HAND)
                return;

            e.cancel();
            if (e.getClickedBlock().isPresent()) {

                e.getSlimefunItem().ifPresent(item -> {
                    ItemStack itemStack = item.getItem();

                    Block targetBlock = e.getClickedBlock().get();
                    if(targetBlock.getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE &&
                            BlockStorage.hasBlockInfo(targetBlock)) {

                        String locationName = Nbt.getNbt_String(e.getPlayer().getInventory().getItemInMainHand(), "location");
                        String[] locationParse = locationName.split(",");

                        BlockStorage.addBlockInfo(targetBlock, "world", locationParse[0]);
                        BlockStorage.addBlockInfo(targetBlock, "x", locationParse[1]);
                        BlockStorage.addBlockInfo(targetBlock, "y", locationParse[2]);
                        BlockStorage.addBlockInfo(targetBlock, "z", locationParse[3]);

                        ChatInfo.SUCCESS.send(e.getPlayer(), "Úspešne si načital súradnice do bloku!");
                        return;
                    }

                    Location targetLocation = targetBlock.getLocation();
                    StringBuilder stringLoc = new StringBuilder();
                    stringLoc
                            .append(targetLocation.getWorld().getName())
                            .append(",")
                            .append(targetLocation.getBlockX() + .5)
                            .append(",")
                            .append(targetLocation.getBlockY())
                            .append(",")
                            .append(targetLocation.getBlockZ() + .5);

                    ItemStack nitemStack = Nbt.setNbt_String(itemStack, "location", stringLoc.toString());
                    e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            new TextComponent("§7Lokácia: " + (targetLocation.getBlockX()+.5) + ", " +
                    targetLocation.getBlockY() + ", " + (targetLocation.getBlockZ()+.5)));

                    e.getPlayer().getInventory().setItemInMainHand(nitemStack);

                    ChatInfo.SUCCESS.send(e.getPlayer(), "Úspešne si uložil súradnice!");

                });
                //SlimefunPlugin.getGPSNetwork().createWaypoint(e.getPlayer(), b.getLocation());
            }
        };
    }

}
