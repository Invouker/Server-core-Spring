package sk.westland.core.services;

import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import sk.westland.core.enums.EServerData;
import sk.westland.core.event.player.WLVotePartyDeath;
import sk.westland.core.utils.ChatInfo;
import sk.westland.world.minigame.PartyGame;

public class VotePartyService implements Listener {

    public final static int VOTEPARTY = 150;
    private PartyGame partyGame;

    @Autowired
    private ServerDataService serverDataService;

    @EventHandler
    private void onVotifier(VotifierEvent event) {
        EServerData serverData = EServerData.VOTES_TOTAL;
        int totalVotes = serverDataService.getIntData(serverData)+1;
        serverDataService.setIntData(serverData, totalVotes);

        if(totalVotes % (VOTEPARTY+1) == VOTEPARTY) {
            spawnVoteParty();
        }
    }

    public void spawnVoteParty() {
        if(partyGame != null)
            partyGame.despawn();

        partyGame = new PartyGame(new Location(Bukkit.getWorld("worldspawn"), 69.5, 58, -29.5));
        ChatInfo.SUCCESS.sendAll("Na spawne sa zjavil VoteParty Illusioner!");
    }

    public void despawn() {
        if(partyGame != null)
        partyGame.despawn();
    }

    @EventHandler(ignoreCancelled = true)
    private void onWLVotePartyDeath(WLVotePartyDeath event) {
        partyGame = null;
        ChatInfo.SUCCESS.sendAll("Odmena z VoteParty bola rozdaná!");
    }
}
