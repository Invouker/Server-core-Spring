package sk.westland.core.jobs.rewards;

import org.bukkit.entity.Player;

public interface JIReward<T extends JIReward<T>> {

    T reward(Player player);

    String render();

}
