package sk.westland.discord.ranksync;


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
import sk.westland.discord.DiscordHandler;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.RunnableHelper;

import java.util.concurrent.TimeUnit;

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

        PlayerSync playerSync = null;
        for (PlayerSync playerSync_: discordHandler.getPlayerSyncList()) {
            if(playerSync_ != null &&
                    playerSync_.getCode() != null &&
                    playerSync_.getCode().equals(code)) {
                playerSync = playerSync_;
            }
        }

       if(playerSync == null) {
           ChatInfo.WARNING.send(context, "Tento kód buhužial neexistuje, skús znova!");
           return;
       }

        playerSync.setPlayer(context.getPlayer());
        Guild guild = discordHandler.getJda().getGuildById("796403023681290251");

        assert guild != null;
        Member member = guild.getMemberById(playerSync.getPlayerDiscordId());

        User user = null;
        if(member == null) {
            user = playerSync.getUser();
        }
        else user = member.getUser();

        user.openPrivateChannel().queue((privateChannel -> {
            privateChannel.sendMessage("Úspešne si si zosynchronizoval účet!").queue((msg) -> {
                msg.delete().queueAfter(3, TimeUnit.SECONDS);
            });
        }));
        long playerID = playerService.getWLPlayer(context.getPlayer()).getUserData().getId();
        RunnableHelper.save(rankDataRepository, new RankData(playerID, user.getId()));
        ChatInfo.SUCCESS.send(context, "Uspešne si si zosynchronizoval účet s discord účtom: " + user.getName());
    }
}
