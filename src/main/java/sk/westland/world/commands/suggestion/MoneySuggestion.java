package sk.westland.world.commands.suggestion;


import org.springframework.stereotype.Component;
import sk.westland.core.enums.MoneyType;

import java.util.Iterator;
import java.util.stream.Stream;

@Component
public class MoneySuggestion implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Stream.of(MoneyType.values()).map(Enum::name).iterator();
    }
}
