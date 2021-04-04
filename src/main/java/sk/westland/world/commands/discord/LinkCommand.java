package sk.westland.world.commands.discord;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.RankData;
import sk.westland.core.database.player.RankDataRepository;
import sk.westland.core.database.player.UserData;
import sk.westland.core.database.player.UserDataRepository;
import sk.westland.core.discord.DiscordHandler;
import sk.westland.core.discord.ranksync.PlayerSync;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.VaultService;
import sk.westland.core.utils.Utils;
import sk.westland.discord.PermissionHandler;

import java.awt.*;
import java.util.Optional;

public class LinkCommand extends ListenerAdapter {

    private PlayerService playerService;
    private RankDataRepository rankDataRepository;
    private UserDataRepository userDataRepository;
    private VaultService vaultService;

    public LinkCommand(PlayerService playerService, RankDataRepository rankDataRepository, UserDataRepository userDataRepository, VaultService vaultService) {
        this.playerService = playerService;
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

        Optional<RankData> rankDataOptional = rankDataRepository.findByDiscordUuidAndIsSynced(event.getAuthor().getId(), true);

        if(rankDataOptional.isPresent()) {
            event.getMessage().delete().queue();
            event.getTextChannel().sendMessage("Tvoj účet už bol zosynchronizovaný!").queue();
            return;
        }

        if(discordHandler.containsPlayerSync(user.getId())) {
            user.openPrivateChannel().queue((privateChannel -> {
                privateChannel.sendMessage("Už máš vygenerovaný kód!").queue();
            }));
            return;
        }
        event.getTextChannel().sendMessage("Kód aj s návodom ti budú doručené do súkromej správy!").queue();
        String code = Utils.generateRandomChars(8);
        user.openPrivateChannel().queue((privateChannel -> {
            privateChannel.sendMessage(new EmbedBuilder()
                    .setAuthor("WestLand")
                    .setColor(Color.YELLOW)
                    .setDescription("Idk čo sem")
                    .addField("Tvoj kód ktorý zadáš príkazom do hry", "`/sync " + code + "`", true)
                    .setFooter("Správa bola automaticky vygenerovaná").build())
                    .queue();
        }));
        PlayerSync playerSync = new PlayerSync(user.getId(), code);
        playerSync.setUser(user);
        discordHandler.addPlayer(playerSync);
    }

    /*
    @Override
    @Command(command = "link",
            description = {"" +
                    "Pre linknutie discord účtov, s účtom ", "ktorý vlastníte na minecraft serveri."},
            aliases = {"sync","ranksync", "syncrank"})
    public void onCommand(User user, String command, String[] args, String arg, MessageReceivedEvent event) {

    }*/
}
