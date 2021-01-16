package sk.westland.world.quests;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.quest.IReward;
import sk.westland.core.quest.QuestFlag;
import sk.westland.core.quest.TaskFactory;
import sk.westland.core.quest.action.Actions;
import sk.westland.core.quest.action.event.TaskEnabledEvent;
import sk.westland.core.quest.action.type.GoToLocationAction;
import sk.westland.core.quest.action.type.InteractWithNPCAction;
import sk.westland.core.quest.action.type.OpenBookWithTextAction;
import sk.westland.core.quest.reward.ItemReward;
import sk.westland.core.services.QuestService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

@Component
public class TestQuest implements Listener {

    @Autowired
    private QuestService questService;

    @EventHandler
    private void onPluginEnable(PluginEnableEvent event) {
        questService.registerQuest("test", "Najdi Potulnika", "Najdi Potulníka a porozprávaj sa s ním!",
                TaskFactory.create()
                        .addTask("IDK, proste klikni", "Klikni sem, kokot",Actions.openBookWithText(new String[]{
                                "Sám neviem čo sem napísať, ale píšem :D 1. strana",
                                "Je to nuda, čo ti poviem, eagle je piča! 2. strana"
                        }))
                        .addTask("Najdi potulnika", "Najdi potulnika smerom neviem kam, kokot", Actions.interactWithNPC("§6Potulnik"), new ArrayList<IReward>(Arrays.asList(new ItemReward(Material.BAMBOO, 2), new ItemReward(Material.BAMBOO, 2))))
                        .addTask("Najdi prístav", "Choď najsť prístav, pičus",Actions.goToLocation(new Location(Bukkit.getWorld("worldspawn"), 0, 61, -173), 5))
                        .build(),
                EnumSet.of(QuestFlag.Repeatable, QuestFlag.RemoveOnDeath),
                new ItemReward(Material.BAMBOO, 3));
    }

    @EventHandler
    private void onNPC(WLPlayerInteractWithNPCEvent event) {
        if(event.getNPCName().endsWith("Martin"))
            questService.acceptQuest("test", event.getWLPlayer());
    }

}