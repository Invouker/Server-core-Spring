package sk.westland.world.commands.suggestion;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import sk.westland.core.enums.EPlayerOptions;

import java.util.*;
import java.util.stream.Stream;

@Component
public class PlayerOptionsSuggestion implements Iterable<String> {

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return Stream.of(EPlayerOptions.values()).map(Enum::name).iterator();
    }
}
