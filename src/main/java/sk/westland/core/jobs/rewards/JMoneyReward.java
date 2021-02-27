package sk.westland.core.jobs.rewards;

import org.bukkit.entity.Player;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.services.MoneyService;

public class JMoneyReward implements JIReward {

    private MoneyType moneyType;
    private double moneyValue;
    private MoneyService moneyService;

    public JMoneyReward(MoneyType moneyType, double moneyValue, MoneyService moneyService) {
        this.moneyType = moneyType;
        this.moneyValue = moneyValue;
        this.moneyService = moneyService;
    }

    @Override
    public JMoneyReward reward(Player player) {
        moneyService.set(moneyService.getPlayerService().getWLPlayer(player), moneyType, moneyValue);
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
