package sk.westland.core.services;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.WestLand;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.EPlayerOptions;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.scoreboard.ScoreDisplay;
import sk.westland.core.utils.RunnableDelay;

import java.util.HashMap;

@Service
public class ScoreboardService implements Listener, Runnable {

    @Autowired
    private PlayerService playerService;

    private final HashMap<Player, ScoreDisplay> scoreDisplayHashMap = new HashMap<>();

    public ScoreboardService() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(WestLand.getInstance(), this, RunnableDelay.DELAY(), 20*4);
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        Bukkit.getOnlinePlayers().forEach((player -> showScoreboardForPlayer(player, true)));
    }

    @EventHandler
    private void onResourcePackEventLo(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        WLPlayer wlPlayer = playerService.getWLPlayer(player);
        if(event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)
            showScoreboardForPlayer(player, EPlayerOptions.SHOW_SCOREBOARD.getPlayerOptions(wlPlayer));
    }

    public boolean playerHasScoreboard(Player player) {
        return scoreDisplayHashMap.containsKey(player);
    }

    public void removeScoreboard(Player player) {
        if(!playerHasScoreboard(player))
            return;

        scoreDisplayHashMap.remove(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public void showScoreboardForPlayer(Player player, boolean show) {
        if(!show) {
            removeScoreboard(player);
            return;
        }

        if(playerHasScoreboard(player))
            return;

        ScoreDisplay scoreDisplay = new ScoreDisplay(player, " ");
        scoreDisplay.setLine("&r       ⺏⻔⺎", 0);
        scoreDisplay.setLine(" ", 1);
        scoreDisplay.setLine(ChatColor.of("#838282") + "    %server_time_dd.MM.yyyy HH:mm%", 2);
        scoreDisplay.setLine(" ", 3);
        scoreDisplay.setLine(ChatColor.of("#AED6F1") + " %cmi_user_name%", 4);
        scoreDisplay.setLine("  &fPeniaze: " + ChatColor.of("#D6EAF8") + "%cmi_user_balance_formated%\u2E9A", 5);
        scoreDisplay.setLine(" ", 6);
        scoreDisplay.setLine("  &2◄ &fGemy: " + ChatColor.of("#D6EAF8") + "%westland_gems% &a⻳", 7);
        scoreDisplay.setLine("  &b◄ &fShardy: " + ChatColor.of("#D6EAF8") + "%westland_shards% &f⺞", 8);
        scoreDisplay.setLine(" ", 9);
        scoreDisplay.setLine(ChatColor.of("#AED6F1") +" Server", 10);
        scoreDisplay.setLine("  &fOnline: " + ChatColor.of("#D6EAF8") + "%server_online%", 11);
        scoreDisplay.setLine("  &fParty: " + ChatColor.of("#D6EAF8") + "soon", 12);
        scoreDisplay.setLine(" ", 13);

        scoreDisplayHashMap.put(player, scoreDisplay);
    }

    @EventHandler
    private void onWLPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        removeScoreboard(player);
    }

    @Override
    public void run() {
        scoreDisplayHashMap.forEach((player, scoreDisplay) -> {
            scoreDisplay.setLine("&r       ⺏⻔⺎", 0);
            scoreDisplay.setLine(" ", 1);
            scoreDisplay.setLine(ChatColor.of("#838282") + "    %server_time_dd.MM.yyyy HH:mm%", 2);
            scoreDisplay.setLine(" ", 3);
            scoreDisplay.setLine(ChatColor.of("#AED6F1") + " %cmi_user_name%", 4);
            scoreDisplay.setLine("  &fPeniaze: " + ChatColor.of("#D6EAF8") + "%cmi_user_balance_formated%\u2E9A", 5);
            scoreDisplay.setLine(" ", 6);
            scoreDisplay.setLine("  &2◄ &fGemy: " + ChatColor.of("#D6EAF8") + "%westland_gems% &a⻳", 7);
            scoreDisplay.setLine("  &b◄ &fShardy: " + ChatColor.of("#D6EAF8") + "%westland_shards% &f⺞", 8);
            scoreDisplay.setLine(" ", 9);
            scoreDisplay.setLine(ChatColor.of("#AED6F1") +" Server", 10);
            scoreDisplay.setLine("  &fOnline: " + ChatColor.of("#D6EAF8") + "%server_online%", 11);
            scoreDisplay.setLine("  &fParty: " + ChatColor.of("#D6EAF8") + "soon", 12);
            scoreDisplay.setLine(" ", 13);
        });

    }

}
