package sk.westland.world.commands.player;

import dev.alangomes.springspigot.context.Context;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.services.LotteryService;
import sk.westland.core.services.MoneyService;
import sk.westland.core.utils.ChatInfo;

@Component
@CommandLine.Command(name = "loteria", aliases = {"lottery", "lotery", "tipos", "loto"})
public class LotteryCommand implements Runnable {

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private Context context;

    @Override
    public void run() {
        ChatInfo.COMMAND_HELPER.sendCommandHelperS(context.getSender(), "loteria", "bet", "info");
    }

    @Component
    @CommandLine.Command(name = "bet")
    static class Bet implements Runnable {

        @Autowired
        private LotteryService lotteryService;

        @Autowired
        private Context context;

        @Autowired
        private MoneyService moneyService;

        @Override
        public void run() {
            Player player = context.getPlayer();

            if(!(context.getSender() instanceof Player))
                return;

            if(!moneyService.canPay(player, MoneyType.Money, lotteryService.getBetPrice())) {
                ChatInfo.ERROR.send(player, "Nemáš dostatok penazí! (2000$)");
                return;
            }

            moneyService.pay(player, MoneyType.Money, lotteryService.getBetPrice());
            lotteryService.addToLotery(player);
        }
    }

    @Component
    @CommandLine.Command(name = "info")
    static class Info implements Runnable {

        @Autowired
        private LotteryService lotteryService;

        @Autowired
        private Context context;

        @Autowired
        private MoneyService moneyService;

        @Override
        public void run() {
            CommandSender commandSender = context.getSender();
            commandSender.sendMessage("");
            ChatInfo.SUCCESS.send(commandSender, "Zápisné do lotérie je§6 " + lotteryService.getBetPrice());
            ChatInfo.SUCCESS.send(commandSender, "Hrá sa o§6 " + lotteryService.getLotteryWinPrice()+"$");
            ChatInfo.SUCCESS.send(commandSender, "Aktuálne zapísaných ľudí§6 " + lotteryService.getPlayers().size());
            ChatInfo.SUCCESS.send(commandSender, "Pre vyžrebovanie výhercu je potreba: §6" + lotteryService.getMinPlayers() + "§f hráčov!");
            commandSender.sendMessage("");
            if(lotteryService.getPlayers().size() > 0) {
                ChatInfo.GENERAL_INFO.send(commandSender, lotteryService.getPlayerList());
                commandSender.sendMessage("");
            }
        }
    }
}
