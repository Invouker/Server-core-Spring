package sk.westland.core.quest.reward;

import sk.westland.core.quest.IReward;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.services.QuestService;
import org.jetbrains.annotations.NotNull;

public class QuestReward implements IReward {

    private String quest_id;

    public QuestReward(String quest_id) {
        this.quest_id = quest_id;
    }

    @Override
    public void apply(@NotNull QuestService questService, @NotNull WLPlayer player) {
        questService.acceptQuest(quest_id, player);
    }
}
