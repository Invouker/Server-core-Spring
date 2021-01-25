package sk.westland.world.commands;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.MoneyType;
import sk.westland.core.database.player.UserData;
import sk.westland.core.database.player.UserRepository;
import sk.westland.core.services.MoneyService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.services.PlayerService;
import sk.westland.world.commands.Converter.PlayerArgConverter;
import sk.westland.world.commands.suggestion.MoneySuggestion;
import sk.westland.world.commands.suggestion.PlayerSuggestion;

import java.util.Optional;

@Component
@CommandLine.Command(name = "pmoney", aliases = {"premiummoney"})
@HasPermission("commands.money")
public class PMoneyCommands implements Runnable {

    @Autowired
    private Context context;

    @Override
    public void run() {
        ChatInfo.COMMAND_HELPER.sendCommandHelperS(context.getSender(), "pmoney","get <moneyType> <player>", "set <moneyType> <amount> <player>", "give <moneyType> <amount> <player>");
    }

    @Component
    @CommandLine.Command(name = "get")
    @HasPermission("commands.pmoney.get")
    public static class GetMoney implements Runnable {

        @CommandLine.Parameters(index = "0", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", completionCandidates = MoneySuggestion.class)
        private MoneyType moneyType;

        @Autowired
        private PlayerService playerService;

        @Autowired
        private MoneyService moneyService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            if(targetPlayerArg == context.getPlayer()) {
                Player player = context.getPlayer();
                if(player == null) {
                    ChatInfo.ERROR.send(context.getSender(), "Príkaz iba pre hráčov");
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

            ChatInfo.SUCCESS.send(context.getPlayer(), "Hráč " + targetPlayer.getName() + " má aktuálne " +  moneyService.get(targetPlayer, moneyType) + " shardov!");
        }
    }

    @Component
    @CommandLine.Command(name = "set")
    @HasPermission("commands.pmoney.set")
    public static class SetMoney implements Runnable {

        @CommandLine.Parameters(index = "2", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

        @CommandLine.Parameters(index = "0", completionCandidates = MoneySuggestion.class)
        private MoneyType moneyType;

        @CommandLine.Parameters(index = "1")
        private double amount;

        @Autowired
        private Context context;

        @Autowired
        private MoneyService moneyService;

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

            moneyService.set(targetPlayer, moneyType, amount);
            ChatInfo.SUCCESS.send(targetPlayer, "Aktuálne máš " + targetPlayer.getShards() + " shardov!");
        }
    }

    @Component
    @CommandLine.Command(name = "give")
    @HasPermission("commands.pmoney.give")
    public static class GiveMoney implements Runnable {

        @CommandLine.Parameters(index = "0", completionCandidates = MoneySuggestion.class)
        private MoneyType moneyType;

        @CommandLine.Parameters(index = "1")
        private double amount;

        @CommandLine.Parameters(index = "2", completionCandidates = PlayerSuggestion.class)
        private String targetPlayerArg;

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private MoneyService moneyService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            Player targetBukkitPlayer = Bukkit.getPlayer(targetPlayerArg);
            if(targetBukkitPlayer == null) {
                boolean success = runOffline(targetPlayerArg, moneyType, (int) amount);
                if(success)
                    ChatInfo.SUCCESS.send(context.getPlayer(), "Úspešne si prirátal " + amount + " " + moneyType.name() + ", hráčovi " + targetPlayerArg);
                return;
            }
            if(targetBukkitPlayer == context.getPlayer()) {
                Player player = context.getPlayer();
                if(player == null) {
                    ChatInfo.ERROR.send(context.getSender(), "Príkaz iba pre hráčov");
                    return;
                }
                targetPlayer = playerService.getWLPlayer(player);
            } else if(targetPlayerArg == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            } else
                targetPlayer = playerService.getWLPlayer(targetBukkitPlayer);

            if(targetPlayer == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            }

            if(moneyService.give(targetPlayer, moneyType, amount))
                ChatInfo.SUCCESS.send(targetPlayer, "Aktuálne máš " + moneyService.get(targetPlayer, moneyType) + " " + moneyType.name() + "!");
            else ChatInfo.ERROR.send(context.getSender(), "Error ");
        }

        private boolean runOffline(String targetPlayer, MoneyType moneyType, int amount) {
            Optional<UserData> userDataOptional = userRepository.findByName(targetPlayer);
            if(!userDataOptional.isPresent()) {
                ChatInfo.ERROR.sendAdmin("Hráč " + targetPlayer + " nebol najdený v DB a tak mu neboli pripočítané " + moneyType.name() + "!");
                return false;
            }

            UserData userData = userDataOptional.get();
            switch (moneyType) {
                case Shard:
                    userData.setShards(userData.getShards() + amount);
                case Gems:
                    userData.setGems(userData.getGems() + amount);
            }

            userRepository.save(userData);
            return true;
        }
    }
}
