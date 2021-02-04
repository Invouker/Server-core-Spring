package sk.westland.core.quest.reward;

import org.jetbrains.annotations.NotNull;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.quest.IReward;
import sk.westland.core.services.QuestService;

public class MoneyReward implements IReward {
    @Override
    public void apply(@NotNull QuestService questService, @NotNull WLPlayer player) {

    }
/*
    private MoneyType moneyType;
    private int amount;

    public MoneyReward(MoneyType moneyType, int amount) {
        this.moneyType = moneyType;
        this.amount = amount;
    }

    @Override
    public void apply(@NotNull QuestService questService, @NotNull WLPlayer player) {
        player.giveMoney(moneyType, amount, MoneyReceivedEvent.Reason.Quest);
        ChatInfo.SUCCESS.send(player, ComponentBuilder.text("Získal jsi " + amount + " ").extra(ComponentBuilder.translate(moneyType.getLocalizationKey()).build()).build());
    }*/
}
