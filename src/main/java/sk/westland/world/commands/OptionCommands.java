package sk.westland.world.commands;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.enums.EPlayerOptions;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.world.commands.suggestion.PlayerOptionsSuggestion;

@Component
@CommandLine.Command(name = "option")
@HasPermission("commands.option")
public class OptionCommands implements Runnable {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private Context context;

    @CommandLine.Parameters(index = "0", completionCandidates = PlayerOptionsSuggestion.class)
    private EPlayerOptions playerOptions;

    @CommandLine.Parameters(index = "1", completionCandidates = PlayerOptionsSuggestion.class)
    private boolean option;

    @Override
    public void run() {
        ChatInfo.SUCCESS.send(context.getPlayer(),
                "Úspešne si si nastavil §6" + StringUtils.capitalize(playerOptions.name().toLowerCase()) + "§f na §6" + option);
        playerOptions.setPlayerOptions(playerService.getWLPlayer(context.getPlayer()), option);
    }
}
