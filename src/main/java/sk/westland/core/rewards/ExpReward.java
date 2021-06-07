package sk.westland.core.rewards;

import org.bukkit.entity.Player;

public class ExpReward implements IReward<ExpReward> {

    private final int exp;

    public ExpReward(int exp) {
        this.exp = exp;
    }

    @Override
    public ExpReward reward(Player player) {
        player.giveExp(exp);

        return this;
    }

    @Override
    public String render() {
        return exp + " exp";
    }
}
