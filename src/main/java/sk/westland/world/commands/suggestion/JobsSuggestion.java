package sk.westland.world.commands.suggestion;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import sk.westland.core.enums.JobList;
import sk.westland.world.items.Materials;

import java.util.Iterator;
import java.util.stream.Stream;

@Component
public class JobsSuggestion implements Iterable<String> {

    private static String[] commands;

    public JobsSuggestion() {
        commands = new String[JobList.values().length];

        for (int i = 0; i < JobList.values().length; i++)
            commands[i] = JobList.values()[i].getName().toLowerCase();

    }

    @Override
    public @NotNull Iterator<String> iterator() {
        return Stream.of(commands).iterator();
    }

}
