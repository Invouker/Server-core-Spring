package sk.westland.world.commands;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.services.PlayerService;
import sk.westland.world.commands.Converter.PlayerArgConverter;

@Component
@CommandLine.Command(name = "coin")
@HasPermission("commands.coin")
public class CoinCommands implements Runnable {

    @Autowired
    private Context context;

    @Autowired
    private PlayerService playerService;

    @Override
    public void run() {
        Player player = context.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        ChatInfo.COMMAND_HELPER.sendCommandHelper(wlPlayer, "coin","get <player>", "set <>", "give");
    }

    @Component
    @CommandLine.Command(name = "get")
    @HasPermission("commands.coin.get")
    public static class GetCommand implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            if(targetPlayerArg == context.getPlayer()) {
                Player player = context.getPlayer();
                if(player == null) {
                    ChatInfo.ERROR.send(context.getSender(), "Príkaz iba pre  hráčov");
                    return;
                }
                targetPlayer = playerService.getWLPlayer(player);
            } else if(targetPlayerArg == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            } else
                targetPlayer = playerService.getWLPlayer(targetPlayerArg);

            if(targetPlayer == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            }

            WLPlayer wlPlayer = targetPlayer;
            ChatInfo.SUCCESS.send(wlPlayer, "Aktuálne máš " + wlPlayer.getCoin() + " coinov!");
        }
    }

    @Component
    @CommandLine.Command(name = "set")
    @HasPermission("commands.coin.set")
    public static class SetCoin implements Runnable {

        @CommandLine.Parameters(index = "0")
        private double coins;

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "1", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            if(targetPlayerArg == context.getPlayer()) {
                Player player = context.getPlayer();
                if(player == null) {
                    ChatInfo.ERROR.send(context.getSender(), "Príkaz iba pre  hráčov");
                    return;
                }
                targetPlayer = playerService.getWLPlayer(player);
            } else if(targetPlayerArg == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            } else
                targetPlayer = playerService.getWLPlayer(targetPlayerArg);

            if(targetPlayer == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            }

            WLPlayer wlPlayer = targetPlayer;
            wlPlayer.setCoin(coins);
            ChatInfo.SUCCESS.send(wlPlayer, "Aktuálne máš " + wlPlayer.getCoin() + " coinov!");
        }
    }

    @Component
    @CommandLine.Command(name = "give")
    @HasPermission("commands.coin.give")
    public static class SetCoins implements Runnable {

        @CommandLine.Parameters(index = "0")
        private double coins;

        @CommandLine.Parameters(index = "1", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            if(targetPlayerArg == context.getPlayer()) {
                Player player = context.getPlayer();
                if(player == null) {
                    ChatInfo.ERROR.send(context.getSender(), "Príkaz iba pre  hráčov");
                    return;
                }
                targetPlayer = playerService.getWLPlayer(player);
            } else if(targetPlayerArg == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            } else
                targetPlayer = playerService.getWLPlayer(targetPlayerArg);

            if(targetPlayer == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            }

            WLPlayer wlPlayer = targetPlayer;
            wlPlayer.setCoin(wlPlayer.getCoin()+coins);
            ChatInfo.SUCCESS.send(wlPlayer, "Aktuálne máš " + wlPlayer.getCoin() + " coinov!");
        }
    }
}
