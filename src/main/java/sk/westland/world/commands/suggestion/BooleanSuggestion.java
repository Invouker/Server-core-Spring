package sk.westland.world.commands.suggestion;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.stream.Stream;

@Component
public class BooleanSuggestion implements Iterable<String> {

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return Stream.of("true", "false").iterator();
    }
}
