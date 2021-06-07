package sk.westland.world.commands.suggestion;

import org.springframework.stereotype.Component;
import sk.westland.core.enums.EEventType;

import java.util.Iterator;
import java.util.stream.Stream;

@Component
public class EventTypeSuggestion implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Stream.of(EEventType.values()).map(Enum::name).iterator();
    }

}
