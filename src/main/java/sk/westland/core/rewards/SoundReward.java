package sk.westland.core.rewards;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import sk.westland.core.utils.Utils;

public class SoundReward implements IReward<SoundReward> {

    private Sound sound;
    private boolean broadcast = false;

    public SoundReward(Sound sound) {
        this.sound = sound;
    }

    public SoundReward(Sound sound, boolean broadcast) {
        this.sound = sound;
        this.broadcast = broadcast;
    }

    @Override
    public SoundReward reward(Player player) {
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
