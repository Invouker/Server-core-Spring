package sk.westland.core.quest.action.type;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.quest.Quest;
import sk.westland.core.quest.QuestTask;
import sk.westland.core.quest.action.ActionDataStorage;
import sk.westland.core.quest.action.ITaskAction;
import sk.westland.core.quest.action.TaskRequireEvent;

@TaskRequireEvent({WLPlayerInteractWithNPCEvent.class})
public class InteractWithNPCAction extends ITaskAction {

    private String npcName;

    public InteractWithNPCAction(String npcName) {
        this.npcName = npcName;
        setNameOfNpcForInteraction(npcName);
    }

    @Override
    public boolean evaluate(@NotNull Quest quest, @NotNull QuestTask task, @NotNull WLPlayer Player, @NotNull ActionDataStorage dataStorage, @Nullable Event bukkitEvent) {
        System.out.println("TEST Interacti With NPC ACTION");

        if(bukkitEvent == null) {
            return false;
        }

        if(!(bukkitEvent instanceof WLPlayerInteractWithNPCEvent)) {
            return false;
        }

        WLPlayerInteractWithNPCEvent event = (WLPlayerInteractWithNPCEvent) bukkitEvent;
        return this.npcName.endsWith(event.getNPCName());
    }
}
