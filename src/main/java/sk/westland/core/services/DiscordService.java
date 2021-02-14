package sk.westland.core.services;

import com.gmail.chickenpowerrr.ranksync.api.bot.Bot;
import com.gmail.chickenpowerrr.ranksync.api.data.Properties;
import com.gmail.chickenpowerrr.ranksync.discord.bot.BotFactory;
import com.gmail.chickenpowerrr.ranksync.discord.bot.DiscordBot;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.JDA;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.OnlineStatus;
import com.gmail.chickenpowerrr.ranksync.lib.jda.jda.api.entities.*;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.springframework.stereotype.Service;
import sk.westland.core.WestLand;
import sk.westland.core.discord.CommandRegister;
import sk.westland.core.event.PluginEnableEvent;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DiscordService implements Listener {

    private JDA jda;
    private Guild guild;

    private GuildChannel statusChannel;
    private GuildChannel infoStatusChannel;

    private CommandRegister commandRegister;

    @EventHandler(ignoreCancelled = true)
    @SuppressWarnings("unchecked")
    private void onPluginEnable(PluginEnableEvent event) throws NoSuchFieldException, IllegalAccessException {
        new BukkitRunnable() {
            @Override
            public void run() {
                Field field = null;
                Optional<Bot> botOptional = null;
                try {
                    field = BotFactory.class.getDeclaredField("botCache");
                    field.setAccessible(true);

                    botOptional =
                            ((HashMap<Properties, Bot>)field.get(BotFactory.getInstance()))
                                    .values()
                                    .stream()
                                    .findFirst();
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                if(!botOptional.isPresent())
                    throw new NullPointerException("Discord bot service, bot is null from reflection.");
                DiscordBot discordBot = (DiscordBot) botOptional.get();

                guild = discordBot.getGuild();
                jda = discordBot.getGuild().getJDA();

                commandRegister = new CommandRegister();
                jda.addEventListener(commandRegister);

                jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("play.westland.sk"));

                statusChannel = guild.getGuildChannelById("808801609106194462");
                infoStatusChannel = guild.getGuildChannelById("809460405935407175");

            }
        }.runTaskAsynchronously(event.getWestLand());

        discordStatus();
        //discordPlayers();
    }

    @EventHandler
    private void playerJoinEvent(PlayerJoinEvent event) {
        discordStatus();
    }

    @EventHandler
    private void playerQuitEvent(PlayerQuitEvent event) {
        discordStatus();
    }

    private void discordStatus() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(infoStatusChannel == null) {
                    Bukkit.getLogger().warning("Channel already doesnt exist.");
                    this.cancel();
                    return;
                }

                if(!(infoStatusChannel instanceof TextChannel))
                    return;
                new MessageHistory((MessageChannel) infoStatusChannel).retrievePast(5).queue((messageList) -> {

                    if(messageList == null || messageList.isEmpty()) {
                        ((TextChannel) infoStatusChannel).sendMessage("A").queue();
                        ((TextChannel) infoStatusChannel).sendMessage("\u1CBC").queue();
                        ((TextChannel) infoStatusChannel).sendMessage("B").queue();
                        ((TextChannel) infoStatusChannel).sendMessage("\u1CBC").queue();
                        ((TextChannel) infoStatusChannel).sendMessage("C").queue();
                        return;
                    }

                    Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
                    for (int i = 0; i < messageList.size(); i++) {
                        switch (i) {
                            case 4:{
                                double tps = BigDecimal.valueOf(MinecraftServer.getServer().recentTps[0]).setScale(2, RoundingMode.HALF_UP).doubleValue();
                                messageList.get(i).editMessage("\uD83D\uDDA5 Server status **Online** | \uD83C\uDFAE TPS **" + tps + "**\n" +
                                        "⌨️ IP **82.208.17.61:27227** / **mc.westland.sk**\n" +
                                        "\uD83E\uDDFE Whitelist **" + Bukkit.getServer().hasWhitelist() + "**").queue();
                                break;
                            }
                            case 2:{
                                StringBuilder stringBuilder = new StringBuilder();

                                onlinePlayers.forEach((player)-> stringBuilder.append(player.getName()).append("\n"));
                                String online = "";
                                if(onlinePlayers.size() > 0)
                                    online = "```txt\n" + stringBuilder.toString() + "```";
                                messageList.get(i)
                                        .editMessage("\uD83D\uDC66\uD83C\uDFFD Online players **" + onlinePlayers.size() + "**" + online)
                                        .queue();
                                break;
                            }
                            case 0:{
                                StringBuilder stringBuilder = new StringBuilder();
                                List<Player> admins = new ArrayList<>();
                                onlinePlayers.stream().filter((player -> player.hasPermission("admin") || player.isOp())).forEach(admins::add);
                                admins.forEach((player)-> stringBuilder.append(player.getName()).append("\n"));

                                String online = "";
                                if(onlinePlayers.size() > 0)
                                    online = "```txt\n" + stringBuilder.toString() + "```";
                                messageList.get(i)
                                        .editMessage("\uD83D\uDC66\uD83C\uDFFD Online admins **" + admins.size() + "**" + online)
                                        .queue();
                                break;
                            }
                        }
                    }

                });

            }
        }.runTaskTimerAsynchronously(WestLand.getInstance(), 20L, 20*20L);
    }

    private void pluginDisable() {
        new MessageHistory((MessageChannel) infoStatusChannel).retrievePast(5).queue((messageList) -> {

            if(messageList == null || messageList.isEmpty()) {
                ((TextChannel) infoStatusChannel).sendMessage("A").queue();
                ((TextChannel) infoStatusChannel).sendMessage("\u1CBC").queue();
                ((TextChannel) infoStatusChannel).sendMessage("B").queue();
                ((TextChannel) infoStatusChannel).sendMessage("\u1CBC").queue();
                ((TextChannel) infoStatusChannel).sendMessage("C").queue();
                return;
            }

            Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
            for (int i = 0; i < messageList.size(); i++) {
                switch (i) {
                    case 4:{
                        double tps = BigDecimal.valueOf(MinecraftServer.getServer().recentTps[0]).setScale(2, RoundingMode.HALF_UP).doubleValue();
                        messageList.get(i).editMessage("Server status **Offline**").queue();
                        break;
                    }
                    case 2:{
                        StringBuilder stringBuilder = new StringBuilder();

                        onlinePlayers.forEach((player)-> stringBuilder.append(player.getName()).append("\n"));
                        messageList.get(i)
                                .editMessage("Online players **" + 0 + "**```txt\n"
                                        + stringBuilder.toString() + "```")
                                .queue();
                        break;
                    }
                    case 0:{
                        messageList.get(i).editMessage("\u1CBC").queue();
                        break;
                    }
                }
            }

        });
    }

    private void discordPlayers() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(statusChannel == null) {
                    Bukkit.getLogger().warning("Channel already doesnt exist.");
                    this.cancel();
                    return;
                }

                guild.getGuildChannelById("809460405935407175").getManager().setName("\uD83D\uDCBB\u1CBC82∙208∙17·61∙27227").complete();
                //statusChannel.getManager().setName(
                  //      "TPS-" + BigDecimal.valueOf(MinecraftServer.getServer().recentTps[0]).setScale(2, RoundingMode.HALF_UP).doubleValue()  + "-|-Online-" + Bukkit.getOnlinePlayers().size()
                //).complete();
            }
        }.runTaskTimerAsynchronously(WestLand.getInstance(), 20*3L, 20*60*6L);
    }

    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        jda.removeEventListener(commandRegister);
        //statusChannel.getManager().setName("Server is offline").complete();
        pluginDisable();

        statusChannel = null;
        jda = null;
        guild = null;

        System.out.println("Disable Plugin !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public JDA getJDA() {
        return jda;
    }

    public Guild getGuild() {
        return guild;
    }

    public void errorMessage(TextChannel textChannel, String message) {
        errorMessage(textChannel, message, 5);
    }

    public void errorMessage(TextChannel textChannel, String message, int seconds) {
        textChannel.sendMessage("[ERROR]" + message).queue((text) -> {
            text.delete().queueAfter(seconds, TimeUnit.SECONDS);
        });
    }

    public String buildOffsetMessage(String[] string, int offset) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = offset; i < string.length; i++) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }
}
