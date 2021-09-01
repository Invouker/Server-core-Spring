package sk.westland.world.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.enums.EEventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupEventInventory implements InventoryProvider {

    public SmartInventory smartInventory = SmartInventory.builder()
            .id("eventInventory")
            .title("Vytvorenie eventu")
            .type(InventoryType.CHEST)
            .size(3,9)
            .provider(this)
            .build();

    private Map<Player, EventData> dataMap = new HashMap<>();

    public SetupEventInventory(Player player) {
        smartInventory.open(player);
    }

    // EVENT TYPE
    // MIN PLAYERS // paper
    // MAX PLAYERS

    // REWARD PLACE (
    // 1 miesto - 5 ludi (netherite helmet);
    // 2 miesta - 10 ludi (golden helmet);
    // 3 miesta - 15 ludi (iron helmet)
    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(0,0, ClickableItem.of(new ItemStack(Material.ANVIL), (item) -> {this.smartInventory.close(player);}));
        contents.set(0,0, ClickableItem.of(new ItemStack(Material.PAPER), (item) -> {this.smartInventory.close(player);}));
        contents.set(0,0, ClickableItem.of(new ItemStack(Material.PAPER), (item) -> {this.smartInventory.close(player);}));
        contents.set(0,0, ClickableItem.of(new ItemStack(Material.LECTERN), (item) -> {this.smartInventory.close(player);}));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    static class RewardEvent {

        private int rewardPlaces = 1;
        private Map<Integer, List<ItemStack>> itemReward;

        public RewardEvent() {
            itemReward = new HashMap<>();
            itemReward.put(0, new ArrayList<>());
        }

        public RewardEvent(int rewardPlaces, Map<Integer, List<ItemStack>> itemReward) {
            this.rewardPlaces = rewardPlaces;
            this.itemReward = itemReward;
        }

        public int getRewardPlaces() {
            return rewardPlaces;
        }

        public void setRewardPlaces(int rewardPlaces) {
            this.rewardPlaces = rewardPlaces;
        }

        public Map<Integer, List<ItemStack>> getItemReward() {
            return itemReward;
        }

        public void setItemReward(Map<Integer, List<ItemStack>> itemReward) {
            this.itemReward = itemReward;
        }
    }

    static class EventData {
        private EEventType eEventType;
        private int minPlayers;
        private int maxPlayers;
        private RewardEvent rewardEvent;

        public EEventType getEventType() {
            return eEventType;
        }

        public void setEventType(EEventType eEventType) {
            this.eEventType = eEventType;
        }

        public int getMinPlayers() {
            return minPlayers;
        }

        public void setMinPlayers(int minPlayerss) {
            this.minPlayers = minPlayerss;
        }

        public int getMaxPlayers() {
            return maxPlayers;
        }

        public void setMaxPlayers(int maxPlayers) {
            this.maxPlayers = maxPlayers;
        }

        public RewardEvent getRewardEvent() {
            return rewardEvent;
        }

        public void setRewardEvent(RewardEvent rewardEvent) {
            this.rewardEvent = rewardEvent;
        }
    }

}
