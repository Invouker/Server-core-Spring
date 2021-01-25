package sk.westland.world.commands.suggestion;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import sk.westland.core.MoneyType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@Component
public class MoneySuggestion implements Iterable<String> {

    private Collection<String> type = new HashSet<>();

    public MoneySuggestion() {
        Arrays.stream(MoneyType.values()).forEach(moneyType -> type.add(moneyType.name()));
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return type.iterator();
    }
}
