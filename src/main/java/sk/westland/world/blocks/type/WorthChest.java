package sk.westland.world.blocks.type;
;
import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Worth.WorthItem;
import com.comphenix.protocol.reflect.cloning.BukkitCloner;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import sk.westland.core.WestLand;
import sk.westland.core.blocks.BlockLevel;
import sk.westland.core.blocks.BlockType;
import sk.westland.core.blocks.CustomBlock;
import sk.westland.core.database.data.BlockData;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.services.BlockService;
import sk.westland.world.items.Materials;

import java.util.UUID;

public class WorthChest extends CustomBlock {

    private static final BlockType BLOCK_TYPE = BlockType.WORTH_CHEST;

    public WorthChest(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockData blockData, BlockService blockService) {
        super(owner, ownerUUID, location, blockLevel, Materials.Items.WORTH_CHEST.getCustomItem(), blockData, blockService);
        this.blockType = BLOCK_TYPE;
        setBlockLevel(BlockLevel.RARE);
    }

    public WorthChest(String owner, UUID ownerUUID, Location location, BlockLevel blockLevel, BlockService blockService) {
        super(owner, ownerUUID, location, blockLevel, Materials.Items.WORTH_CHEST.getCustomItem(), new BlockData(owner, ownerUUID, location, blockLevel, BLOCK_TYPE), blockService);
        setBlockLevel(BlockLevel.RARE);
    }

    @Override
    public void onBlockLoad() {
        System.out.println("Loaded block");
    }

    @Override
    public void onBlockUpdate() {

        if(!(block.getState() instanceof Chest))
            return;

        Chest chest = ((Chest) block.getState());
        if(chest.getBlockInventory().getStorageContents().length <= 0)
            return;

        do {
            for (int i = 0; i < chest.getBlockInventory().getSize(); i++) {
                ItemStack itemStack = chest.getBlockInventory().getItem(i);

                if(itemStack == null || itemStack.getType() == Material.AIR)
                    return;

                /*WorthItem worth = CMI.getInstance().getWorthManager().getWorth(itemStack);
                if (worth == null)
                    continue;
                */
                Player player = Bukkit.getPlayer(getOwner());
                OfflinePlayer offlinePlayer;
                double amount = ShopGuiPlusApi.getItemStackPriceSell(itemStack);

                if(amount == -1)
                    continue;

                if(player == null) {
                    offlinePlayer = Bukkit.getOfflinePlayer(getOwnerUUID());
                    blockService.getMoneyService().getVaultService().getEconomy().has(offlinePlayer, amount);
                }else

                blockService.getMoneyService().give(player, MoneyType.Money, ShopGuiPlusApi.getItemStackPriceSell(itemStack));

                int pos = i;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        chest.getBlockInventory().setItem(pos, null);
                    }
                }.runTask(WestLand.getInstance());
            }
        }while (BlockLevel.isEqualsOrBetter(BlockLevel.BASIC));
    }

    private boolean blockedMaterial(Material mat, Material... blockedMaterial) {
        for (Material material : blockedMaterial) {
            if(mat == material)
                return true;
        }
        return false;
    }

    @Override
    public void onBlockTimeUpdate() {

    }

    @Override
    public void onBlockUnload() {

    }

    @Override
    public void onBlockInteract(PlayerInteractEvent event, BlockService blockService) {
    }

    @Override
    public void onRedstoneActivate(BlockRedstoneEvent event) {

    }
}
