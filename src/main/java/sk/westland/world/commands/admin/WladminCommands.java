package sk.westland.world.commands.admin;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.enums.EPlayerTimeReward;
import sk.westland.core.enums.EServerData;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.ServerDataService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.world.commands.Converter.PlayerArgConverter;
import sk.westland.world.commands.suggestion.PlayerSuggestion;
import sk.westland.world.commands.suggestion.TimeRewardSuggestion;
import sk.westland.world.events.AutoMessageEvent;

@Component
@CommandLine.Command(name = "wladmin")
@HasPermission("westland.commands.wladmin")
public class WladminCommands implements Runnable {

    @Override
    public void run() {

    }

    @Component
    @CommandLine.Command(name = "timereward")
    @HasPermission("westland.commands.timereward")
    public static class timereward implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", completionCandidates = TimeRewardSuggestion.class)
        private String stringTime;

        @CommandLine.Parameters(index = "1")
        private EPlayerTimeReward ePlayerTimeReward;

        @Autowired
        private PlayerService playerService;

        @CommandLine.Parameters(index = "2", completionCandidates = PlayerSuggestion.class, converter = PlayerArgConverter.class)
        private Player player;

        @Override
        public void run() {
            if(stringTime.equals("reset")) {
                ChatInfo.SUCCESS.send(context, "Úspešne si resetoval hráčovy time reward");
                playerService.getWLPlayer(player).setRewardClaimedTime(ePlayerTimeReward, 0);
            }
        }
    }

    @Component
    @CommandLine.Command(name = "automessage")
    @HasPermission("westland.commands.automessage")
    public static class automessage implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0")
        private int time;

        @Autowired
        private PlayerService playerService;

        @Autowired
        private ServerDataService serverDataService;

        @Override
        public void run() {
            serverDataService.setIntData(EServerData.AUTOMESSAGE_TIME, time*60);
            AutoMessageEvent.getAutoMessageEvent().reStart();
            ChatInfo.SUCCESS.send(context, "Úspešne si nastavil automessage interval na " + time + " minút");
        }
    }
}