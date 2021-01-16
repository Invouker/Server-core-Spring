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
public class GiveMoneyToNPCAction extends ITaskAction {
    @Override
    public boolean evaluate(@NotNull Quest quest, @NotNull QuestTask task, @NotNull WLPlayer player, @NotNull ActionDataStorage dataStorage, @Nullable Event bukkitEvent) {
        return false;
    }
/*
    private MoneyType moneyType;
    private int amount;

    private InteractWithNPCAction interactWithNPCAction;

    public GiveMoneyToNPCAction(String npcName, MoneyType moneyType, int amount) {
        this.moneyType = moneyType;
        this.amount = amount;
        this.interactWithNPCAction = new InteractWithNPCAction(npcName);
        setNameOfNpcForInteraction(npcName);
    }

    @Override
    public boolean evaluate(@NotNull Quest quest, @NotNull QuestTask task, @NotNull WLPlayer player, @NotNull ActionDataStorage dataStorage, @Nullable Event bukkitEvent) {
        if(!interactWithNPCAction.evaluate(quest, task, player, dataStorage, bukkitEvent, mbEvent))
            return false;

        Transaction transaction = player.newTransaction(this.moneyType, this.amount);

        if(transaction.pay())
        {
            CustomEventService.call(MoneySentEvent.class, new MoneySentEvent(player, this.moneyType, this.amount, MoneySentEvent.Reason.Command));
            return true;
        }
        else
        {
            ChatInfo.WARNING.send(player,"Nemáš dostatek peněz! Potřebuješ " + this.amount + " " + TranslationService.translate(this.moneyType.getChat(), player).toLegacyText());
            return false;
        }
    }*/
}
