package sk.westland.world.commands.Converter;

import dev.alangomes.springspigot.context.Context;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;


@Component
public class PlayerArgConverter implements CommandLine.ITypeConverter<Player> {

    @Autowired
    private Context context;

    @Override
    public Player convert(String s) throws Exception {
        if("@s".equals(s) || "@p".equals(s))
            return context.getPlayer();

        return Bukkit.getPlayer(s);
    }
}