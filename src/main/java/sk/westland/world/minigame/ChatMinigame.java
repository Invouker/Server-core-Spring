package sk.westland.world.minigame;

import fr.xephi.authme.api.v3.AuthMeApi;
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
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.RunnableDelay;
import sk.westland.core.utils.Utils;

@Component
public class ChatMinigame implements Listener, Runnable {

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private PlayerService playerService;

    private static String guessWhat = null;
    private static boolean isRunning = false;
    private BukkitTask task;

    public ChatMinigame() {
        Bukkit.getScheduler().runTaskTimer(WestLand.getInstance(), () -> {

            if(Bukkit.getOnlinePlayers().size() < 3)
                return;

            isRunning = true;
            guessWhat = Utils.generateRandomChars(Utils.BaseMath.getRandomMinMaxInt(6, 10));
            ChatInfo.GENERAL_INFO.sendAll("Kto ako prvý napíše §6" + guessWhat + "§f vyhrá odmenu!");

            Bukkit.getOnlinePlayers().stream()
                    .filter((player) -> playerService.getWLPlayer(player) != null)
                    .filter((player) -> playerService.getWLPlayer(player).getPlayerOptions().isChatReactionSound())
                    .forEach(player -> Utils.playSound(player, Sound.ENTITY_PUFFER_FISH_BLOW_UP));
           // Utils.playSound(Sound.ENTITY_PUFFER_FISH_BLOW_UP);
            //20*60*5
            task = Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), this, Math.max(20*60*15, 60-Bukkit.getOnlinePlayers().size()));

        }, RunnableDelay.DELAY(), 20*62*5L);
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if(guessWhat != null && isRunning && event.getMessage().contains(guessWhat)) {
            Player player = event.getPlayer();

            AuthMeApi authMeApi = AuthMeApi.getInstance();;
            if(!authMeApi.isAuthenticated(player))
                return;

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
