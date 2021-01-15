package sk.westland.world.commands.suggestion;

import org.springframework.stereotype.Component;
import sk.westland.world.items.Materials;

import java.util.Iterator;
import java.util.stream.Stream;

@Component
public class CraftingSuggestion implements Iterable<String> {

    private static String[] commands;

    public CraftingSuggestion() {
        commands = new String[getSizeCraftable()];

        for(int i = 0; i < Materials.Items.values().length; i ++) {
            Materials.Items item = Materials.Items.values()[i];
            if(item.isCraftable())
                commands[i] = item.name().toLowerCase();
        }
    }

    private int getSizeCraftable() {
        int i = 0;
        for (Materials.Items item : Materials.Items.values()) {
            if(item.isCraftable())
                i++;
        }
        return i;
    }

    @Override
    public Iterator<String> iterator() {
        return Stream.of(commands).iterator();
    }

}
