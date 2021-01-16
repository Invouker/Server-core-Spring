package sk.westland.world.commands.suggestion;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.services.QuestService;

import java.util.Iterator;

@Component
public class QuestIdSuggestion implements Iterable<String> {

    @Autowired
    private QuestService questService;

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return questService.getQuestIds().iterator();
    }
}
