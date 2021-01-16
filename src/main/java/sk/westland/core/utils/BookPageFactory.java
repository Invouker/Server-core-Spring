package sk.westland.core.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import sk.westland.core.ComponentBuilder;

import java.util.ArrayList;
import java.util.List;

public class BookPageFactory {

    private List<BaseComponent> components = new ArrayList<>();

    private BookPageFactory parent;
    private boolean isOption;
    private boolean condition;

    public BookPageFactory(BookPageFactory parent, boolean isOption, boolean condition) {
        this.parent = parent;
        this.isOption = isOption;
        this.condition = condition;
    }

    public static BookPageFactory create() {
        return new BookPageFactory(null, false, false);
    }

    @Deprecated
    public BookPageFactory newLineText(String text) {
        if(text == null) {
            text = "";
        }
        return this.appendText("\n" + text);
    }

    @Deprecated
    public BookPageFactory newLineText() {
        return this.appendText("\n");
    }
    public BookPageFactory newLine() {
        return this.append(new TextComponent("\n"));
    }

    public BookPageFactory multipleNewLines(int count) {
        if(count <= 0)
            return this;

        StringBuilder sb = new StringBuilder();
        for(; count > 0; count--)
            sb.append('\n');
        return this.appendText(sb.toString());
    }

    @Deprecated
    public BookPageFactory appendText(String text) {
        if(text == null) {
            text = "";
        }
        this.components.add(new TextComponent(text));
        return this;
    }

    public BookPageFactory newLine(BaseComponent component) {
        newLine();
        append(component);
        return this;
    }

    public BookPageFactory append(BaseComponent component) {
        this.components.add(component);
        return this;
    }

    public BookPageFactory optional(boolean condition) {
        return new BookPageFactory(this, true, condition);
    }

    public BookPageFactory else_() {
        return elseif(!this.condition);
    }

    public BookPageFactory elseif(boolean condition) {
        return new BookPageFactory(end(), true, condition);
    }

    public BookPageFactory end() {
        if(this.condition) {
            this.parent.components.addAll(this.components);
        }
        return this.parent;
    }

    @Deprecated
    public BookPageFactory appendAction(String text, ClickEvent.Action action, String actionValue, String tooltip) {
        this.components.add(
                ComponentBuilder
                        .text(text)
                        .click(new ClickEvent(action, actionValue))
                        .hover(
                                new HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT,
                                    new BaseComponent[] { ComponentBuilder.text(tooltip).build() }
                                )
                        )
                        .build()
        );
        return this;
    }

    @Deprecated
    public BookPageFactory newLineAction(String text, ClickEvent.Action action, String actionValue, String tooltip) {
        return this.appendAction("\n" + text, action, actionValue, tooltip);
    }

    public BookPageFactory appendAction(BaseComponent component, ClickEvent.Action action, String actionValue, BaseComponent tooltip) {
        this.components.add(
                new ComponentBuilder(component)
                        .click(new ClickEvent(action, actionValue))
                        .hover(
                                new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        new BaseComponent[] { tooltip }
                                )
                        )
                        .build()
        );
        return this;
    }

    public BookPageFactory newLineAction(BaseComponent component, ClickEvent.Action action, String actionValue, BaseComponent tooltip) {
        return newLine().appendAction(component, action, actionValue, tooltip);
    }

    public BookPageFactory appendAction(BaseComponent component, ClickEvent.Action action, String actionValue) {
        this.components.add(
                new ComponentBuilder(component)
                        .click(new ClickEvent(action, actionValue))
                        .build()
        );
        return this;
    }

    public BookPageFactory newLineAction(BaseComponent component, ClickEvent.Action action, String actionValue) {
        return newLine().appendAction(component, action, actionValue);
    }

    public BaseComponent[] build() {
        // toArray() is faster with input array of 0 length
        // https://stackoverflow.com/questions/174093/toarraynew-myclass0-or-toarraynew-myclassmylist-size
        return this.components.toArray(new BaseComponent[0]);
    }

    public BaseComponent[] build(Player player) {
        BaseComponent[] translated = new BaseComponent[this.components.size()];

        for(int i = 0; i < translated.length; i++)
            translated[i] = components.get(i);

        return translated;
    }

}
