package sk.westland.core.services;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import org.springframework.beans.factory.annotation.Autowired;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.UserDataRepository;
import sk.westland.discord.commands.CommandRegister;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.event.ServerDisableEvent;
import sk.westland.core.utils.RunnableDelay;
import sk.westland.core.utils.RunnableHelper;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DiscordService implements Listener, Runnable {

    @Autowired
    private UserDataRepository userDataRepository;

    private JDA jda;
    private Guild guild;
    private Thread thread;

    private GuildChannel infoStatusChannel;

    private CommandRegister commandRegister = null;
    private BukkitTask task;

    private ServerStatus serverStatus = ServerStatus.ONLINE;
    private final long startTime = System.currentTimeMillis();
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @EventHandler
    private void onPluginEnable(PluginEnableEvent event) {
        RunnableHelper.runTaskLaterAsynchronously(()-> {
            jda = WestLand.getDiscordHandler().getJda();

            guild = jda.getGuildById("796403023681290251");
            infoStatusChannel = guild.getGuildChannelById("809460405935407175");
            task = Bukkit.getScheduler().runTaskTimerAsynchronously(WestLand.getInstance(), this, RunnableDelay.DELAY(), 20*20L);

        }, 20*20L);
/*
        new BukkitRunnable() {
            @Override
            public void run() {
                Field field = null;
                Optional<Bot> botOptional = Optional.empty();
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

              //  guild = discordBot.getGuild();
               // jda = discordBot.getGuild().getJDA();

                //commandRegister = new CommandRegister();
                //jda.addEventListener(commandRegister);

                jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("play.westland.sk"));

                //statusChannel = guild.getGuildChannelById("808801609106194462");


            }
        };//.runTaskAsynchronously(WestLand.getInstance());
*/
            }

    @EventHandler
    private void onPluginDisable(ServerDisableEvent event) {

        serverStatus = ServerStatus.OFFLINE;
        //this.run();

        if(commandRegister == null)
            return;

        if(jda == null)
            return;

        jda.removeEventListener(commandRegister);
        //statusChannel.getManager().setName("Server is offline").complete();
        pluginDisable();

        jda = null;
        guild = null;

    }

    @EventHandler
    private void playerJoinEvent(PlayerJoinEvent event) {
        run();
    }

   @EventHandler
    private void playerQuitEvent(PlayerQuitEvent event) {
        run();
    }

    @Override
    public void run() {
        if(infoStatusChannel == null) {
            if(task != null) {
                task.cancel();

                Bukkit.getLogger().warning("Channel already doesnt exist.");
            }
            return;
        }

        if(!(infoStatusChannel instanceof TextChannel))
            return;


        new MessageHistory((MessageChannel) infoStatusChannel).retrievePast(1).queue((messageList) -> {
            List<String> onlinePlayers = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach((player) -> onlinePlayers.add(player.getName()));
            double tps = BigDecimal.valueOf(MinecraftServer.getServer().recentTps[0]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            List<String> admins = new ArrayList<>();
            Bukkit.getOnlinePlayers().stream().filter((player -> player.hasPermission("admin") || player.isOp())).forEach((player) -> admins.add(player.getName()));

            try {
                MessageEmbed messageEmbed = generateEmbedMessage(serverStatus, tps, onlinePlayers, admins, Long.parseLong(PlaceholderAPI.setPlaceholders(null, "%server_unique_joins%")));
                if (messageList == null || messageList.isEmpty())
                    ((TextChannel) infoStatusChannel).sendMessage(messageEmbed).queue();
                else
                    messageList.get(0).editMessage(messageEmbed).queue();
            }catch (Exception ignore) { }
        });
    }

    enum ServerStatus {
        ONLINE("Online", 12118406),
        OFFLINE("Offline",13632027);

        private final int color;
        private final String status;

        ServerStatus(String status, int color) {
            this.color = color;
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public int getColor() {
            return color;
        }
    }

    //red - 13632027
    //green - 12118406
    private MessageEmbed generateEmbedMessage(ServerStatus serverStatus, double tps, List<String> players, List<String> admins, long uniqueConnections) {
        return new EmbedBuilder()
                .setDescription(":signal_strength: Status: " + serverStatus.getStatus() + " | :white_check_mark: TPS: "
                        + tps + "\n:label: IP: mc.westland.sk | play.westland.sk\n ")
                .setColor(new Color(serverStatus.getColor()))
                .setTimestamp(new Date().toInstant()) // OffsetDateTime.parse("2021-03-28T17:00:32.497Z")
                .setFooter("WestLand.sk | Posledný update", "https://westland.sk/images/wl.png")
                .setThumbnail("https://westland.sk/images/wl.png")
                .setAuthor("Server status", "http://westland.sk", "https://westland.sk/images/wl.png")
                .addField("", "**Hráči online** ( " + players.size() + " )\n" +StringUtils.join(players, ", "), true)
                .addField("", "**Členovia AT **( " + admins.size() + " )\n" +StringUtils.join(admins, ", ") + "\n", true)
               .addBlankField(false)
                .addField("Server je spustený od", dateFormat.format(startTime), true)
                .addField("Počet unikátnych pripojení:", String.valueOf(uniqueConnections), true)
                .addField("Oficiálny dátum spustenia:", "1.máj (1.5.2021)\n ", true)
                .build();
    }


    private void pluginDisable() {
        new MessageHistory((MessageChannel) infoStatusChannel).retrievePast(1).queue((messageList) -> {
            List<String> onlinePlayers = new ArrayList<>();
            Bukkit.getOnlinePlayers().stream().forEach((player) -> onlinePlayers.add(player.getName()));
            double tps = BigDecimal.valueOf(MinecraftServer.getServer().recentTps[0]).setScale(2, RoundingMode.HALF_UP).doubleValue();
            List<String> admins = new ArrayList<>();
            Bukkit.getOnlinePlayers().stream().filter((player -> player.hasPermission("admin") || player.isOp())).forEach((player) -> admins.add(player.getName()));

            MessageEmbed messageEmbed = generateEmbedMessage(ServerStatus.OFFLINE, tps, onlinePlayers, admins, userDataRepository.count());
            if(messageList == null || messageList.isEmpty())
                ((TextChannel) infoStatusChannel).sendMessage(messageEmbed).queue();
            else
                messageList.get(0).editMessage(messageEmbed).queue();
        });
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
