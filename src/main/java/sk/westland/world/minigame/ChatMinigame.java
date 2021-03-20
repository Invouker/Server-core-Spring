package sk.westland.world.minigame;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.services.MoneyService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.RunnableDelay;
import sk.westland.core.utils.Utils;

@Component
public class ChatMinigame implements Listener, Runnable {

    @Autowired
    private MoneyService moneyService;

    char[] alphabet = "abcdefghijklmnopqrstuvwxyz123456789".toCharArray();
    private static String guessWhat = null;
    private static boolean isRunning = false;
    private BukkitTask task;

    private String getStringOfRandomChars(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(
                    Utils.BaseMath.getRandomBoolean() ?
                            alphabet[Utils.BaseMath.getRandomInt(alphabet.length-1)] :
                            Character.toUpperCase(alphabet[Utils.BaseMath.getRandomInt(alphabet.length-1)])
            );
        }
        return stringBuilder.toString();
    }

    public ChatMinigame() {
        Bukkit.getScheduler().runTaskTimer(WestLand.getInstance(), () -> {

            if(Bukkit.getOnlinePlayers().size() < 3)
                return;

            isRunning = true;
            guessWhat = getStringOfRandomChars(Utils.BaseMath.getRandomMinMaxInt(6, 10));
            ChatInfo.GENERAL_INFO.sendAll("Kto ako prvý napíše §6" + guessWhat + "§f vyhrá odmenu!");
            Utils.playSound(Sound.ENTITY_PUFFER_FISH_BLOW_UP);
            task = Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), this, 20*60L);

        }, RunnableDelay.DELAY(), 20*62*5L);
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if(guessWhat != null && isRunning)
        if(event.getMessage().contains(guessWhat)) {
            Player player = event.getPlayer();
            int gem = Utils.BaseMath.getRandomMinMaxInt(2, 6);
            int money = Utils.BaseMath.getRandomMinMaxInt(100, 250);

            moneyService.give(player, MoneyType.Gems, gem);
            moneyService.give(player, MoneyType.Money, money);
            Utils.playSound(player, Sound.ENTITY_PLAYER_LEVELUP);
            ChatInfo.SUCCESS.send(player, "Dostal si §2" + gem + " gemy§f a §6" + money + "$§f ako odmenu!");
            ChatInfo.GENERAL_INFO.sendAll("Hráč " + ChatColor.of("#f9e79f") + event.getPlayer().getName() + "§f vyhral reakciu a dostal odmenu!");
            isRunning = false;
            guessWhat = null;

            event.setCancelled(true);
        }
    }

    @Override
    public void run() {
        if(guessWhat != null && isRunning && task != null) {

            ChatInfo.GENERAL_INFO.sendAll("Nikto nenapísal správu ako prvý!");

            isRunning = false;
            guessWhat = null;
            task = null;
        }
    }
}
