package sk.westland.core.quest.reward;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.quest.IReward;
import sk.westland.core.services.QuestService;

public class ItemReward implements IReward {

    private ItemStack itemStack;

    public ItemReward(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemReward(@NotNull Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    @Override
    public void apply(@NotNull QuestService questService, @NotNull WLPlayer player) {
        player.getInventory().addItem(this.itemStack);
    }
}
