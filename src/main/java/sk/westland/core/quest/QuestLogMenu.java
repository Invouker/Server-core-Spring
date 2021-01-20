package sk.westland.core.quest;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.inventory.ItemMenu;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.quest.storage.QuestProgressStorage;
import sk.westland.core.quest.storage.QuestProgressStorageOrderDependent;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.QuestService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QuestLogMenu extends ItemMenu {

    private PlayerService playerService;
    private QuestService questService;

    public QuestLogMenu(PlayerService playerService, QuestService questService) {
        super(Type.Chest6, "Tvoje Questy");
        this.playerService = playerService;
        this.questService = questService;
    }

    @Override
    protected void itemInit() { }

    @Override
    protected void onClick(@NotNull Player player, int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {
        if(item != null && item.getType() == Material.BARRIER) {
            closeAll();
        }
    }

    @Override
    protected void onOpen(@NotNull Player player) {

    }

    private static String X_CHAR = ChatColor.RED + "✖";
    private static String CHECK_CHAR = ChatColor.GREEN + "✔";

    private ItemStack createQuestItemInfo(WLPlayer wlPlayer, QuestProgressStorage progressStorage) {
        Quest quest = progressStorage.getQuest();

        ItemBuilder itemBuilder;

        if(progressStorage.getQuestState() == QuestState.Completed) {
            itemBuilder = new ItemBuilder(Material.BOOK);
            itemBuilder.setName(ChatColor.AQUA + quest.getTitle());
        } else {
            itemBuilder = new ItemBuilder(Material.WRITABLE_BOOK);
            itemBuilder.setName(ChatColor.YELLOW + quest.getTitle());
        }

        List<String> itemLore = new ArrayList<>();

        for(String descLine : quest.getDescriptionFormatted()) {
            itemLore.add(ChatColor.WHITE + descLine);
        }

        if(quest.hasAnyTaskTitle()) {
            itemLore.add("");
            itemLore.add(ChatColor.GREEN + "------ Postup ------");

            for (int i = 0; i < quest.getTaskCount(); i++) {
                QuestTask task = quest.getTask(i);

                if(task.getTitle() == null) continue;

                if(!quest.hasFlag(QuestFlag.OrderIndependent) && i != 0 && i > ((QuestProgressStorageOrderDependent)progressStorage).getActiveTaskId()) {
                    itemLore.add(ChatColor.GRAY + " ...>");
                    break;
                }

                String taskInfo;

                if(progressStorage.isTaskIdCompleted(i) || progressStorage.getQuestState() == QuestState.Completed) {
                    taskInfo = " " + CHECK_CHAR + ChatColor.WHITE + " " + task.getTitle();
                } else {
                    taskInfo = " " + X_CHAR + ChatColor.YELLOW + " " + task.getTitle();
                }

                itemLore.add(taskInfo);
            }
        }

        itemBuilder.setLore(itemLore);

        return itemBuilder.build();
    }

    @Override
    protected void onClose(@NotNull Player player) {

    }

    @Override
    @EventHandler
    public void onCloseEvent(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() != this)
            return;
        HumanEntity entity = event.getPlayer();
        if (!(entity instanceof Player))
            return;
        Player player = (Player) entity;

        removePlayer(player);
        onClose(player);
    }
}
