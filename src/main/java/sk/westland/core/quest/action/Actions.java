package sk.westland.core.quest.action;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.quest.action.type.*;

public class Actions {

    public static GoToLocationAction goToLocation(Location location, double range) {
        return new GoToLocationAction(location, range);
    }

    public static GoToLocationAction goToLocation(Location location) {
        return new GoToLocationAction(location, 1.5);
    }

    public static InteractWithNPCAction interactWithNPC(String npcName) {
        return new InteractWithNPCAction(npcName);
    }

    public static GiveItemToNpcAction giveItemToNpc(String npcName, Material itemMaterial, int itemCount, String displayName) {
        return new GiveItemToNpcAction(npcName, itemMaterial, itemCount, displayName);
    }

    public static GiveItemToNpcAction giveItemToNpc(String npcName, Material itemMaterial, int itemCount) {
        return new GiveItemToNpcAction(npcName, itemMaterial, itemCount);
    }

    public static GiveItemToNpcAction giveItemToNpc(String npcName, ItemStack itemStack) {
        return new GiveItemToNpcAction(npcName, itemStack);
    }

    public static OpenBookWithTextAction openBookWithText(String[] pages) {
        return new OpenBookWithTextAction(pages);
    }

    /*
    public static GiveMoneyToNPCAction giveMoneyToNPC(String npcName, MoneyType moneyType, int amount) {
        return new GiveMoneyToNPCAction(npcName, moneyType, amount);
    }

    public static TradeWithNPCAction tradeWithNPC(String npcName, String bookText, Trade trade) {
        return new TradeWithNPCAction(npcName, bookText, trade);
    }
*/
}
