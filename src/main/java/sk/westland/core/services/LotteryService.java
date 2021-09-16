package sk.westland.core.services;

import dev.alangomes.springspigot.extensions.EconomyService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import sk.westland.core.WestLand;
import sk.westland.core.utils.ChatInfo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class LotteryService implements Listener, BeanWire {

    private final int betPrice = 2000;
    private final int minPlayers = 3;
    private int playerBet = 0;
    private final double lotteryMultiplier = 1.45;
    private static int lastKnownHour = 0;

    private final ArrayList<LotteryPlayer> players = new ArrayList<>();

    SimpleDateFormat dateFormatter = new SimpleDateFormat("H");
    // H - hour
    // m - minute

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private EconomyService economyService;

    public LotteryService() {

       // isLotteryEnabled = App.getService(ServerDataService.class).getBooleanData(EServerData.LOTTERY_RUNNING);

        Thread thread = new Thread(() -> {
            while(WestLand.getInstance().isEnabled()) {
                try {
                    tick();

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "scheduler");
        thread.start();
    }

    public void addToLotery(Player player) {
        players.add(new LotteryPlayer(player.getUniqueId(), player, player.getName()));
        playerBet++;
        ChatInfo.GENERAL_INFO.sendAll("Hráč " + player.getName() + " sa prihlásil do lotérie! (/loteria)");
    }


    public void tick() {
        int hour = Integer.parseInt(dateFormatter.format(new Date()));
        if(hour == lastKnownHour)
            return;

        lastKnownHour = hour;

        Bukkit.getScheduler().runTask(WestLand.getInstance(), () -> {
            System.out.println("LOTTERY: 0");
            LotteryPlayer lotteryWinner = getLotteryWinner();
            if(lotteryWinner == null)
                return;

            System.out.println("LOTTERY: 1");
            int playerSize = players.size();
            if(playerSize < minPlayers) {
                for(LotteryPlayer player : players) {
                    economyService.deposit(Bukkit.getOfflinePlayer(player.uuid), new BigDecimal(2000));
                }
                playerBet = 0;
                clearLottery();
                ChatInfo.SUCCESS.sendAll("Do lotérie sa nepripojila minimálny počet účastnikov!");
                return;
            }

            System.out.println("LOTTERY: 2");
            if(Bukkit.getPlayer(lotteryWinner.uuid) == null) {
                ChatInfo.SUCCESS.sendAll("Výherca sa odpojil a tak mu neni možné priradiť odmenu! " +
                        "A tak bol Jackpot prehodený do ďalšieho kola!");
                return;
            }


            System.out.println("LOTTERY: 3");
            economyService.deposit(Bukkit.getOfflinePlayer(lotteryWinner.uuid), BigDecimal.valueOf(getLotteryWinPrice()));

            ChatInfo.SUCCESS.sendAll("Hráč " + lotteryWinner.name + " vyhrál lotériu! JACKPOT: "  + getLotteryWinPrice()+"$");
            playerBet = 0;
            clearLottery();
            System.out.println("LOTTERY: 4");

        });
    }


    public double getLotteryWinPrice() {
        return round(players.size() * lotteryMultiplier * betPrice, 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public LotteryPlayer getLotteryWinner() {
        Random random = new Random();
        if(players.isEmpty())
            return null;

        int randomPlayer = random.nextInt(players.size());
        return players.get(randomPlayer);
    }

    public int getBetPrice() {
        return betPrice;
    }

    public void clearLottery() {
        players.clear();
    }

    public String getPlayerList() {
        StringBuilder stringBuilder = new StringBuilder("Hráči v loterii: ");
        players.forEach((player) -> {
            stringBuilder.append(player.name).append(", ");
        });

        return stringBuilder.toString();
    }

    public ArrayList<LotteryPlayer> getPlayers() {
        return players;
    }


    public int getMinPlayers() {
        return minPlayers;
    }

    record LotteryPlayer(UUID uuid, Player player, String name) {
        @Override
        public UUID uuid() {
            return uuid;
        }

        @Override
        public Player player() {
            return player;
        }

        @Override
        public String name() {
            return name;
        }
    }
}
