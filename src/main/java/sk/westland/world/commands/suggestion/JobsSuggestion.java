package sk.westland.world.commands.suggestion;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import sk.westland.core.enums.JobList;

import java.util.Iterator;
import java.util.stream.Stream;

@Component
public class JobsSuggestion implements Iterable<String> {

    @Override
    public @NotNull Iterator<String> iterator() {
        return Stream.of(JobList.values()).map(Enum::name).iterator();
    }

}
