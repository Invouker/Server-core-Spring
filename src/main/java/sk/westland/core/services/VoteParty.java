package sk.westland.core.services;

import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sk.westland.core.enums.EServerData;

@Component
public class VoteParty implements Listener {

    public final static int VOTEPARTY = 150;
    private Entity votePartyEntity;

    @Autowired
    private ServerDataService serverDataService;

    @EventHandler
    private void onVotifier(VotifierEvent event) {
        EServerData serverData = EServerData.VOTES_TOTAL;
        int totalVotes = serverDataService.getIntData(serverData)+1;
        serverDataService.setIntData(serverData, totalVotes);

        if(totalVotes % (VOTEPARTY+1) == 150) {
            spawnVoteParty();
        }
    }

    public void spawnVoteParty() {

    }

}
