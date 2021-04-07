package sk.westland.world.items.spawners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerDamageEvent;
import sk.westland.core.items.CustomItem;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.utils.Utils;

@Component
public class MagicSpawner extends CustomItem implements Listener {

    @Override
    public ItemStack getItem() {
        return
                new ItemBuilder(Material.SPAWNER)
                        .setName("§dMagický spawner")
                        .setCustomItem(this)
                        .setLore(
                                "",
                                "§7Kliknutím obdržíš jeden z",
                                "§7následovných spawnerov:",
                                "",
                                "§7- §f1x Wither Skeleton spawner",
                                "§7- §f1x Evoker spawner",
                                "§7- §f1x Villager spawner",
                                "§7- §f2x Enderman spawner",
                                "§7- §f3x Slime spawner",
                                "§7- §f1x Iron Golem spawner",
                                "",
                                "§aKlikni pre otvorenie!"
                        )
                .build();
    }

    @Override
    public int getModelID() {
        return 0;
    }

    @Override
    public String itemID() {
        return "magicSpawner1";
    }

    @Override
    protected void onPluginEnable(PluginEnableEvent event) { }

    @Override
    protected void onPlayerInteractWithItem(PlayerInteractEvent event) {
        int random = Utils.BaseMath.getRandomInt(5);
        Player player = event.getPlayer();
        itemInteractionService.consumeItemInMainHand(player);
        Utils.playSound(player, Sound.ENTITY_PUFFER_FISH_HURT);
        switch (random) {
            default:
            case 0: {
                Utils.addPlayerSpawner(player, EntityType.WITHER_SKELETON);
                break;
            }
            case 1: {
                Utils.addPlayerSpawner(player, EntityType.EVOKER);
                break;
            }
            case 2: {
                Utils.addPlayerSpawner(player, EntityType.VILLAGER);
                break;
            }
            case 3: {
                Utils.addPlayerSpawner(player, EntityType.ENDERMAN, 2);
                break;
            }
            case 4: {
                Utils.addPlayerSpawner(player, EntityType.SLIME, 3);
                break;
            }
            case 5: {
                Utils.addPlayerSpawner(player, EntityType.IRON_GOLEM);
                break;
            }
        }
    }

    @Override
    protected void onPlayerDamageWithItem(WLPlayerDamageEvent event) {

    }

    @Override
    protected void onPlayerInteractAtEntityWithItem(PlayerInteractAtEntityEvent event) {

    }

    @Override
    protected void onPlayerBlockPlace(BlockPlaceEvent event) {

    }

    @Override
    protected void onPlayerBlockBreak(BlockBreakEvent event) {

    }
}
