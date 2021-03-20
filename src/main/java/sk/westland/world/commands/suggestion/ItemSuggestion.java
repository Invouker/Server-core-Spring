package sk.westland.world.commands.suggestion;

import org.springframework.stereotype.Component;
import sk.westland.world.items.Materials;

import java.util.Iterator;
import java.util.stream.Stream;

@Component
public class ItemSuggestion implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Stream.of(Materials.Items.values()).map(Enum::name).iterator();
    }

}
