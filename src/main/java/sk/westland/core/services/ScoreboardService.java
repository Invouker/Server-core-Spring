package sk.westland.core.services;

import com.coloredcarrot.api.sidebar.Sidebar;
import com.coloredcarrot.api.sidebar.SidebarString;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.WestLand;
import sk.westland.core.event.player.WLPlayerJoinEvent;
import sk.westland.core.utils.RunnableDelay;
import sk.westland.core.utils.RunnableHelper;

@Service
public class ScoreboardService implements Listener {

    public ScoreboardService() {
        System.out.println("Ty piča, načítaj sa!");
    }

    @Autowired
    private PlayerService playerService;

    @EventHandler
    private void onWLPlayerJoin(WLPlayerJoinEvent event) {
        System.out.println("Hráč " + event.getWlPlayer().getName() + " sa pripojil do hry!");
    }

    @EventHandler
    private void onWLPlayerJoin(PlayerJoinEvent event) {
        System.out.println("Joining");


        SidebarString line1 = new SidebarString("§r       ⺏⻔⺎");
        SidebarString line2 = new SidebarString("");
        SidebarString line3 = new SidebarString("");
        SidebarString line4 = new SidebarString(ChatColor.of("#838282") + "    %{server-time}");
        SidebarString line5 = new SidebarString("d");
        SidebarString line6 = new SidebarString("");
        SidebarString line7 = new SidebarString("ef");
        SidebarString line8 = new SidebarString("");
        SidebarString line9 = new SidebarString("g");
        SidebarString line10 = new SidebarString("");
        SidebarString line11 = new SidebarString("h");
        SidebarString line12 = new SidebarString("i");
        SidebarString line13 = new SidebarString("");
        SidebarString line14 = new SidebarString("k");

        Sidebar mySidebar = new Sidebar("", WestLand.getInstance(), 60, line1, line2, line3,line4,line5,line6,line7,line8,line9,line10,line11,line12,line13,line14);
        mySidebar.showTo(event.getPlayer());
    }
    /*@EventHandler
    private void onWLPlayerJoin(PlayerJoinEvent event) {
        System.out.println("Joining");
        Player player = event.getPlayer();
        BPlayerBoard board = Netherboard.instance().createBoard(player, "Scoreboard");

        System.out.println("Board: " + board);
        board.setName("");

        board.set("§r       ⺏⻔⺎", 15);
        board.set("§r ", 14);
        board.set(ChatColor.of("#838282") + "    %{server-time}", 13);
        board.set("§r ", 12);
        board.set(ChatColor.of("#AED6F1") + " " + player.getName(), 11);
        board.set("  §fPeniaze: " + ChatColor.of("#D6EAF8") + "20§r\u2E9A", 10);
        board.set("§r ", 9);
        board.set("  §2◄ §fGemy: " + ChatColor.of("#D6EAF8") + "45 §a⻳", 8);
        board.set("  §b◄ §fShardy: " + ChatColor.of("#D6EAF8") + "50 §f⺞", 7);
        board.set("§r ", 6);
        board.set(ChatColor.of("#AED6F1") + " Server", 5);
        board.set("  §fOnline: " + ChatColor.of("#D6EAF8") + Bukkit.getServer().getOnlinePlayers().size(), 4);
        board.set("  §fParty: " + ChatColor.of("#D6EAF8") + "45/100", 3);
        board.set("§r ", 2);


        RunnableHelper.runTaskTimerAsynchronously(() -> {
            board.set("§r       ⺏⻔⺎", 20);
            board.set("§r⻔", 19);
            board.set(ChatColor.of("#838282") + "    %{server-time}", 18);
            board.set("§r⻔", 17);
            board.set(ChatColor.of("#AED6F1") + " " + player.getName(), 16);
            board.set("  §fPeniaze: " + ChatColor.of("#D6EAF8") + "20§r\u2E9A", 15);
            board.set("§r⻔", 14);
            board.set("  §2◄ §fGemy: " + ChatColor.of("#D6EAF8") + "45 §a⻳", 13);
            board.set("  §b◄ §fShardy: " + ChatColor.of("#D6EAF8") + "50 §f⺞", 12);
            board.set("§r⻔", 11);
            board.set(ChatColor.of("#AED6F1") + " Server", 10);
            board.set("  §fOnline: " + ChatColor.of("#D6EAF8") + Bukkit.getServer().getOnlinePlayers().size(), 9);
            board.set("  §fParty: " + ChatColor.of("#D6EAF8") + "45/100", 8);
            board.set("§r⻔", 7);


        }, RunnableDelay.DELAY(), 20L);

    }*/
}
