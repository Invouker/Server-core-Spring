package sk.westland.world.commands.suggestion;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;

@Component
public class VotePartySuggestion implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Arrays.asList("reset", "remove").iterator();
    }
}