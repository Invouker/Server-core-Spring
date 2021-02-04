package sk.westland.core.quest.action.type;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.quest.Quest;
import sk.westland.core.quest.QuestTask;
import sk.westland.core.quest.action.ActionDataStorage;
import sk.westland.core.quest.action.ITaskAction;
import sk.westland.core.quest.action.TaskRequireEvent;

@TaskRequireEvent({WLPlayerInteractWithNPCEvent.class})
public class GiveItemToNpcAction extends ITaskAction {

    private ItemStack itemStack;

    private InteractWithNPCAction interactWithNPCAction;

    public GiveItemToNpcAction(String npcName, Material itemMaterial, int itemCount, String displayName) {
        this.itemStack = new ItemStack(itemMaterial, itemCount);
        ItemMeta itemMeta = this.itemStack.getItemMeta();

        if(itemMeta != null) {
            itemMeta.setDisplayName(displayName);
            this.itemStack.setItemMeta(itemMeta);
        }

        this.interactWithNPCAction = new InteractWithNPCAction(npcName);
        setNameOfNpcForInteraction(npcName);
    }

    public GiveItemToNpcAction(String npcName, Material itemMaterial, int itemCount) {
        this.itemStack = new ItemStack(itemMaterial, itemCount);
        ItemMeta itemMeta = this.itemStack.getItemMeta();

        this.interactWithNPCAction = new InteractWithNPCAction(npcName);
        setNameOfNpcForInteraction(npcName);
    }

    public GiveItemToNpcAction(String npcName, ItemStack itemStack) {
        this.itemStack = itemStack.clone();
        this.interactWithNPCAction = new InteractWithNPCAction(npcName);
        setNameOfNpcForInteraction(npcName);
    }

    @Override
    public boolean evaluate(@NotNull Quest quest, @NotNull QuestTask task, @NotNull WLPlayer player, @NotNull ActionDataStorage dataStorage, @Nullable Event bukkitEvent) {
        boolean hasItem = false;

        Inventory playerInventory = player.getInventory();

        boolean interactedWithNpc = interactWithNPCAction.evaluate(quest, task, player, dataStorage, bukkitEvent);

        if(interactedWithNpc) {
            for (int i = 0; i < playerInventory.getSize(); i++) {
                ItemStack itemInInv = playerInventory.getItem(i);

                if(itemInInv == null) {
                    continue;
                }

                if(itemInInv.getType() == this.itemStack.getType() && itemInInv.getAmount() >= this.itemStack.getAmount()) {
                    hasItem = true;

                    if(itemInInv.getAmount() == this.itemStack.getAmount()) {
                        playerInventory.setItem(i, null);
                    } else {
                        itemInInv.setAmount(itemInInv.getAmount() - this.itemStack.getAmount());
                    }

                    break;
                }
            }
        }

        if(interactedWithNpc && !hasItem) {
            String itemName = this.itemStack.getType().name();
            if(this.itemStack.getItemMeta().hasDisplayName())
                itemName = this.itemStack.getItemMeta().getDisplayName();

            if(itemName.contains("."))
                itemName = ChatColor.stripColor(itemName);

            ChatInfo.WARNING.send(player, "Nemáš " + itemName);
        }

        return interactedWithNpc && hasItem;
    }
}
