package sk.westland.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.event.Listener;
import sk.westland.core.database.player.RankDataRepository;
import sk.westland.core.database.player.UserDataRepository;
import sk.westland.discord.ranksync.PlayerSync;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.VaultService;
import sk.westland.core.utils.RunnableHelper;
import sk.westland.world.commands.discord.LinkCommand;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class DiscordHandler implements Runnable, Listener {

    private JDA jda;
    private Guild guild;
    private Thread thread;

    private List<PlayerSync> playerSyncList = new ArrayList<>();

    private PlayerService playerService;
    private RankDataRepository rankDataRepository;
    private UserDataRepository userDataRepository;
    private VaultService vaultService;

    public DiscordHandler(PlayerService playerService, RankDataRepository rankDataRepository, UserDataRepository userDataRepository, VaultService vaultService) {
        this.playerService = playerService;
        this.rankDataRepository = rankDataRepository;
        this.userDataRepository = userDataRepository;
        this.vaultService = vaultService;

        RunnableHelper.runTaskAsynchronously(this);
    }

    public JDA getJda() {
        return jda;
    }

    public void shutdown() {
        jda.shutdown();
        //thread.interrupt();
        System.out.println("JDA is shutting down!");
    }

    @Override
    public void run() {
        try {
            jda = JDABuilder.createDefault("Nzk3ODU4MjA0OTIxODg4Nzg4.X_slWw.LB4oyW65w6Dwq08pC9a-6ZyRQ1I", GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
                    .addEventListeners(new LinkCommand(playerService, rankDataRepository, userDataRepository, vaultService))
                    .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
                    .build();
            jda.setAutoReconnect(true);
            for(Guild iGuild : jda.getGuilds()) {
                iGuild.loadMembers();
            }
            guild = jda.getGuildById("796403023681290251");

        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerSync> getPlayerSyncList() {
        return playerSyncList;
    }

    public boolean containsPlayerSync(String playerId) {
        for (PlayerSync playerSync : playerSyncList) {
            if(playerId.equals(playerSync.getPlayerDiscordId()))
                return true;
        }
        return false;
    }

    public void addPlayer(PlayerSync playerSync) {
        playerSyncList.add(playerSync);
    }

    public Guild getGuild() {
        return guild;
    }
}