package sk.westland.world.commands.suggestion;

import org.springframework.stereotype.Component;
import sk.westland.world.items.Materials;

import java.util.Iterator;
import java.util.stream.Stream;

@Component
public class CraftingSuggestion implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Stream.of(Materials.Items.values()).filter(Materials.Items::isCraftable).map(Enum::name).iterator();
    }

}
