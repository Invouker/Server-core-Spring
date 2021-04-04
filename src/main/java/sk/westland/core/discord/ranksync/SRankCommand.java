package sk.westland.core.discord.ranksync;


import dev.alangomes.springspigot.context.Context;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.RankData;
import sk.westland.core.database.player.RankDataRepository;
import sk.westland.core.discord.DiscordHandler;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.RunnableHelper;

@Component
@CommandLine.Command(name = "sync")
public class SRankCommand implements Runnable {

    @Autowired
    private Context context;

    @CommandLine.Parameters(index = "0")
    private String code;

    @Autowired
    private RankDataRepository rankDataRepository;

    @Autowired
    private PlayerService playerService;

    @Override
    public void run() {
        DiscordHandler discordHandler = WestLand.getDiscordHandler();
        for (PlayerSync playerSync : discordHandler.getPlayerSyncList()) {
            if(playerSync.getCode().equals(code)) {
                playerSync.setPlayer(context.getPlayer());
                Guild guild = discordHandler.getJda().getGuildById("796403023681290251");

                Member member = guild
                        .getMemberById(
                                playerSync.getPlayerDiscordId()
                        );

                User user = null;
                if(member == null) {
                    System.out.println("Member doesnt exists");
                    user = playerSync.getUser();
                }
                    else user = member.getUser();

                user.openPrivateChannel().queue((privateChannel -> {
                    privateChannel.sendMessage("Úspešne si si zosynchronizoval účet!").queue();
                }));
                long playerID = playerService.getWLPlayer(context.getPlayer()).getUserData().getId();
                RunnableHelper.save(rankDataRepository, new RankData(playerID, user.getId(), true));
                ChatInfo.SUCCESS.sendAll("Uspešne si si zosynchronizoval účet s : " + user.getName());
            }
        }
    }
}
