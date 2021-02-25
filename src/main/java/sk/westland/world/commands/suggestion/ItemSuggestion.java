package sk.westland.world.commands.suggestion;

import org.springframework.stereotype.Component;
import sk.westland.world.items.Materials;

import java.util.Iterator;
import java.util.stream.Stream;

@Component
public class ItemSuggestion implements Iterable<String> {

    private static String[] commands;

    public ItemSuggestion() {
        int itemLength = Materials.Items.values().length;
        commands = new String[itemLength];

        for(int i = 0; i < itemLength; i ++) {
            Materials.Items item = Materials.Items.values()[i];
            commands[i] = item.name().toLowerCase();
        }
    }

    @Override
    public Iterator<String> iterator() {
        return Stream.of(commands).iterator();
    }

}
