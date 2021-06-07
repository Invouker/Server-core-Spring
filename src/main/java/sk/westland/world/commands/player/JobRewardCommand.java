package sk.westland.world.commands.player;

import dev.alangomes.springspigot.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.enums.JobList;
import sk.westland.core.services.MoneyService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.world.commands.suggestion.JobsSuggestion;
import sk.westland.world.inventories.JobsInventory;

@Component
@CommandLine.Command(name = "jobreward")
public class JobRewardCommand implements Runnable {

    @Autowired
    private Context context;

    @CommandLine.Parameters(index = "0", completionCandidates = JobsSuggestion.class)
    private JobList jobList;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MoneyService moneyService;

    @Override
    public void run() {

        if(jobList == null) {
            ChatInfo.ERROR.send(context.getPlayer(), "Zadal si nesprávny názov práce!");
            return;
        }

        JobsInventory jobsInventory =
                new JobsInventory(moneyService,
                        playerService,
                        playerService.getWLPlayer(context.getPlayer()),
                        jobList,
                        0);
        jobsInventory.open(context.getPlayer());
    }
}
