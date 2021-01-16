package sk.westland.core.quest.action.type;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.quest.Quest;
import sk.westland.core.quest.QuestTask;
import sk.westland.core.quest.action.ActionDataStorage;
import sk.westland.core.quest.action.ITaskAction;
import sk.westland.core.quest.action.TaskRequireEvent;
import sk.westland.core.quest.action.event.TaskEnabledEvent;
import sk.westland.core.utils.BookFactory;
import sk.westland.core.utils.BookPageFactory;

@TaskRequireEvent({TaskEnabledEvent.class})
public class OpenBookWithTextAction extends ITaskAction {

    private String[] pages;

    public OpenBookWithTextAction(String[] pages) {
        this.pages = pages;
    }

    @Override
    public boolean evaluate(@NotNull Quest quest, @NotNull QuestTask task, @NotNull WLPlayer player, @NotNull ActionDataStorage dataStorage, @Nullable Event bukkitEvent) {
        return true;
    }

    @Override
    public void whenSwitched(@NotNull Quest quest, @NotNull QuestTask task, @NotNull WLPlayer player, @NotNull ActionDataStorage dataStorage) {
        BookFactory bookFactory = new BookFactory();
        bookFactory.title("_");
        bookFactory.author("MineBreak");

        for(String page : this.pages) {
            bookFactory.addPage(BookPageFactory.create().appendText(page));
        }

        bookFactory.open(player);
    }
}
