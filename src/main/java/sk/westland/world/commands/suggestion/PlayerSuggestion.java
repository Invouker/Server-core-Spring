package sk.westland.world.commands.suggestion;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@Component
public class PlayerSuggestion implements Iterable<String> {
    @Override
    public Iterator<String> iterator() {
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).iterator();
    }
}