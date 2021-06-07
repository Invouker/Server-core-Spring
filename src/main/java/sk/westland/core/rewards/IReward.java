package sk.westland.core.rewards;

import org.bukkit.entity.Player;

public interface IReward<T extends IReward<T>> {

    T reward(Player player);

    String render();

}
