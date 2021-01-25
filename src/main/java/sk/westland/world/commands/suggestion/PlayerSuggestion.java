package sk.westland.world.commands.suggestion;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@Component
public class PlayerSuggestion implements Iterable<String> {

    private Collection<String> players = new HashSet<>();

    public PlayerSuggestion() {
        Bukkit.getOnlinePlayers().forEach((player -> players.add(player.getName())));
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return players.iterator();
    }
}