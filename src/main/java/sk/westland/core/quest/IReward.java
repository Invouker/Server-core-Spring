package sk.westland.core.quest;

import org.jetbrains.annotations.NotNull;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.services.QuestService;

import java.util.Arrays;
import java.util.List;

public interface IReward {

    void apply(@NotNull QuestService questService, @NotNull WLPlayer player);

    static List<IReward> create(@NotNull IReward... rewards) {
        return Arrays.asList(rewards);
    }


}
