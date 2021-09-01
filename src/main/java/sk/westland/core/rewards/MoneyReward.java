package sk.westland.core.rewards;

import org.bukkit.entity.Player;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.services.MoneyService;

public class MoneyReward implements IReward<MoneyReward> {

    private final MoneyType moneyType;
    private final double moneyValue;
    private final MoneyService moneyService;

    public MoneyReward(double moneyValue, MoneyService moneyService) {
        this.moneyType = MoneyType.Money;
        this.moneyValue = moneyValue;
        this.moneyService = moneyService;
    }

    public MoneyReward(MoneyType moneyType, double moneyValue, MoneyService moneyService) {
        this.moneyType = moneyType;
        this.moneyValue = moneyValue;
        this.moneyService = moneyService;
    }

    @Override
    public MoneyReward reward(Player player) {
        moneyService.give(moneyService.getPlayerService().getWLPlayer(player), moneyType, moneyValue);
        return this;
    }

    @Override
    public String render() {
        switch (moneyType) {
            case Money: return moneyValue + "$";
            case Shard: return moneyValue + "x Shard";
            case Gems: return moneyValue + "x Gem";
        }
        return null;
    }
}
