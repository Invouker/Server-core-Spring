package sk.westland.world.commands.player;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.database.player.PlayerData;
import sk.westland.core.database.player.PlayerDataRepository;
import sk.westland.core.database.player.UserData;
import sk.westland.core.database.player.UserDataRepository;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.services.MoneyService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
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
        ChatInfo.COMMAND_HELPER.sendCommandHelperS(context.getSender(), "pmoney",
                "get <moneyType> <player>",
                "set <moneyType> <amount> <player>",
                "give <moneyType> <amount> <player>",
                "remove <moneyType> <amount> (player)");
    }

    @Component
    @CommandLine.Command(name = "get")
    @HasPermission("commands.pmoney.get")
    public static class GetMoney implements Runnable {

        @CommandLine.Parameters(index = "1", defaultValue = "@s", converter = PlayerArgConverter.class)
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

           ChatInfo.SUCCESS.send(context.getPlayer(), "Hráč " + targetPlayer.getName() + " má aktuálne " +  moneyService.get(targetPlayer, moneyType) + " " + moneyType.getName() + "!");
        }
    }

    @Component
    @CommandLine.Command(name = "set")
    @HasPermission("commands.pmoney.set")
    public static class SetMoney implements Runnable {

        @CommandLine.Parameters(index = "0", completionCandidates = MoneySuggestion.class)
        private MoneyType moneyType;

        @CommandLine.Parameters(index = "1")
        private int amount;

        @CommandLine.Parameters(index = "2", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

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

            if(moneyService.set(targetPlayer, moneyType, amount)) {
                ChatInfo.SUCCESS.send(context.getPlayer(), "Nastavil si hráčovy " + targetPlayer.getName() + " " + moneyService.get(targetPlayer, moneyType) + " " + moneyType.getName());
                ChatInfo.SUCCESS.send(targetPlayer, "Aktuálne máš " + moneyService.get(targetPlayer, moneyType) + " " + moneyType.getName() +"!");
            }
        }
    }

    @Component
    @CommandLine.Command(name = "remove")
    @HasPermission("commands.pmoney.remove")
    public static class RemoveMoney implements Runnable {

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

            moneyService.give(targetPlayer, moneyType, -amount);
            if(context.getSender() instanceof Player)
                ChatInfo.SUCCESS.send(context.getPlayer(), "Odobral si hráčovy " + targetPlayer.getName() + " " + moneyService.get(targetPlayer, moneyType) + " " + moneyType.getName());
            ChatInfo.SUCCESS.send(targetPlayer, "Aktuálne máš " + moneyService.get(targetPlayer, moneyType) + " " + moneyType.getName() +"!");
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

        @CommandLine.Parameters(index = "2", completionCandidates = PlayerSuggestion.class, defaultValue = "@me")
        private String targetPlayerArg;

        @Autowired
        private Context context;

        @Autowired
        private PlayerService playerService;

        @Autowired
        private PlayerDataRepository playerDataRepository;

        @Autowired
        private UserDataRepository userDataRepository;

        @Autowired
        private MoneyService moneyService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            if(targetPlayerArg.equalsIgnoreCase("@me"))
                targetPlayerArg = context.getPlayer().getName();

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

            ChatInfo.SUCCESS.send(context.getSender(), "Dal si hráčovy " + targetPlayer.getName() + " " + moneyService.get(targetPlayer, moneyType) + " " + moneyType.getName());
            playerService.save(targetPlayer);
        }

        private boolean runOffline(String targetPlayer, MoneyType moneyType, int amount) {
            Optional<UserData> userDataOptional =userDataRepository.findByUserName(targetPlayer);
            if(!userDataOptional.isPresent()) {
                ChatInfo.ERROR.sendAdmin("Hráč " + targetPlayer + " nebol najdený v DB a tak mu neboli pripočítané " + moneyType.name() + "!");
                return false;
            }
            long id = userDataOptional.get().getId();

            Optional<PlayerData> playerDataOptional = playerDataRepository.findById(id);
            if(!playerDataOptional.isPresent()) {
                ChatInfo.ERROR.sendAdmin("Hráč " + targetPlayer + " nebol najdený v DB a tak mu neboli pripočítané " + moneyType.name() + "!");
                return false;
            }

            PlayerData playerData = playerDataOptional.get();
            switch (moneyType) {
                case Shard: {
                    playerData.setShards(playerData.getShards() + amount);
                    break;
                }
                case Gems: {
                    playerData.setGems(playerData.getGems() + amount);
                    break;
                }
                case Money: {
                    moneyService.getVaultService().getEconomy().depositPlayer(Bukkit.getOfflinePlayer(targetPlayer), amount);
                    break;
                }
            }

            playerDataRepository.save(playerData);
            return true;
        }
    }
}
