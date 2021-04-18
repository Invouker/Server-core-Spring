package sk.westland.world.commands.discord;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Validator;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.RankData;
import sk.westland.core.database.player.RankDataRepository;
import sk.westland.core.database.player.UserData;
import sk.westland.core.database.player.UserDataRepository;
import sk.westland.discord.DiscordHandler;
import sk.westland.discord.ranksync.PlayerSync;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.VaultService;
import sk.westland.core.utils.Utils;
import sk.westland.discord.PermissionHandler;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class LinkCommand extends ListenerAdapter {

    private final RankDataRepository rankDataRepository;
    private final UserDataRepository userDataRepository;
    private final VaultService vaultService;

    public LinkCommand(RankDataRepository rankDataRepository, UserDataRepository userDataRepository, VaultService vaultService) {
        this.rankDataRepository = rankDataRepository;
        this.userDataRepository = userDataRepository;
        this.vaultService = vaultService;
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        String discordId = event.getMember().getId();

        if(rankDataRepository == null)
            return;

        Optional<RankData> rankDataOptional = rankDataRepository.findByDiscordUuid(discordId);
        if(!rankDataOptional.isPresent())
            return;

        RankData rankData = rankDataOptional.get();
        Optional<UserData> userDataOptional = userDataRepository.findById(rankData.getPlayerId());
        if(!userDataOptional.isPresent())
            return;

        UserData userData = userDataOptional.get();
        String groupName = vaultService.getPerms().getPrimaryGroup("world", Bukkit.getOfflinePlayer(userData.getUuid()));

        PermissionHandler.getPermissionHandler().updateRole(userData.getId(), groupName);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if(event.getAuthor().isBot())
            return;

        if(!event.getMessage().getContentDisplay().startsWith("!link"))
            return;

        DiscordHandler discordHandler = WestLand.getDiscordHandler();
        User user = event.getAuthor();

        if(rankDataRepository == null) {
            Bukkit.getLogger().warning("RankDataRepository cannot be null");
            return;
        }

        Optional<RankData> rankDataOptional = rankDataRepository.findByDiscordUuid(event.getAuthor().getId());

        if(rankDataOptional.isPresent()) {
            event.getMessage().delete().queue();
            event.getTextChannel().sendMessage("Tvoj účet už bol zosynchronizovaný!").queue((msg) -> {
                msg.delete().queueAfter(3, TimeUnit.SECONDS);
            });
            return;
        }

        if(discordHandler.containsPlayerSync(user.getId())) {
            event.getMessage().delete().queue();
            user.openPrivateChannel().queue((privateChannel -> {
                privateChannel.sendMessage("Už máš vygenerovaný kód!").queue((msg) -> {
                    msg.delete().queueAfter(3, TimeUnit.SECONDS);
                    privateChannel.close().queue();
                });
            }));
            return;
        }

        event.getTextChannel().sendMessage("Kód aj s návodom ti budú doručené do súkromej správy!").queue((msg) -> {
            msg.delete().queueAfter(3, TimeUnit.SECONDS);
        });

        String code = Utils.generateRandomChars(8);
        user.openPrivateChannel().queue((privateChannel -> {
            privateChannel.sendMessage(new EmbedBuilder()
                    .setAuthor("PREPOJENIE ÚČTU")
                    .setColor(Color.YELLOW)
                    .addField(" ", "Autorizačný kód: `" + code + "`, s nikým svoj kód nezdieľaj!\n" +
                            "Tvoj účet bude prepojený a získaš svoj príslušný rank.", true)
                    .addField(" ", "Pre dokončenie je nutné napísať následovný príkaz\n" +
                            "na našom serveri: `/sync " + code + "`", false)
                    .setFooter("Správa bude automatický zmazaná do 10 minút!").build())
                    .queue((msg) ->
                            msg.delete().queueAfter(10, TimeUnit.MINUTES));//;
            privateChannel.close().queue();
        }));

        PlayerSync playerSync = new PlayerSync(user.getId(), code);
        playerSync.setUser(user);
        discordHandler.addPlayer(playerSync);
        event.getMessage().delete().queue();
    }
}
