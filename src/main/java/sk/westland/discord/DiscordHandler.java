package sk.westland.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.event.Listener;
import sk.westland.core.App;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.RankDataRepository;
import sk.westland.core.database.player.UserDataRepository;
import sk.westland.core.services.VaultService;
import sk.westland.discord.ranksync.PlayerSync;
import sk.westland.world.commands.discord.LinkCommand;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class DiscordHandler implements Runnable, Listener {

    private JDA jda;
    private Guild guild;

    private final List<PlayerSync> playerSyncList = new ArrayList<>();

    private final RankDataRepository rankDataRepository;
    private final UserDataRepository userDataRepository;
    private final VaultService vaultService;

    public DiscordHandler(RankDataRepository rankDataRepository, UserDataRepository userDataRepository) {
        this.rankDataRepository = rankDataRepository;
        this.userDataRepository = userDataRepository;
        this.vaultService = App.getService(VaultService.class);

        var bot = new Thread(this, "DiscordBOT");
        if(!WestLand.isIsLocalhost()) {
            bot.start();
        }
        //runnableService.runTaskAsynchronously(this);
    }

    public JDA getJda() {
        return jda;
    }

    public void shutdown() {
        if(jda == null)
            return;

        jda.shutdown();
        //thread.interrupt();
        System.out.println("JDA is shutting down!");
    }

    @Override
    public void run() {
        try {
            jda = JDABuilder.createDefault("Nzk3ODU4MjA0OTIxODg4Nzg4.X_slWw.LB4oyW65w6Dwq08pC9a-6ZyRQ1I", GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
                    .addEventListeners(new LinkCommand(rankDataRepository, userDataRepository, vaultService))
                    .disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)

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