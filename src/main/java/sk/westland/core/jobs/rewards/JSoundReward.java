package sk.westland.core.jobs.rewards;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import sk.westland.core.utils.Utils;

public class JSoundReward implements JIReward {

    private Sound sound;
    private boolean broadcast = false;

    public JSoundReward(Sound sound) {
        this.sound = sound;
    }

    public JSoundReward(Sound sound, boolean broadcast) {
        this.sound = sound;
        this.broadcast = broadcast;
    }

    @Override
    public JIReward reward(Player player) {
        if(broadcast)
            Utils.playSound(player.getLocation(), sound);
        else
            Utils.playSound(player, sound);
        return this;
    }

    @Override
    public String render() {
        return null;
    }
}
