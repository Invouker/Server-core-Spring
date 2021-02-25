package sk.westland.core.jobs.rewards;

import org.bukkit.entity.Player;

public class JExpReward implements JIReward {

    private int exp;

    public JExpReward(int exp) {
        this.exp = exp;
    }

    @Override
    public JExpReward reward(Player player) {
        player.giveExp(exp);

        return this;
    }

    @Override
    public String render() {
        return "§d" + exp + " exp";
    }
}
