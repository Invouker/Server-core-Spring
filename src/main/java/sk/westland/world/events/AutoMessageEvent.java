package sk.westland.world.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.enums.EPlayerOptions;
import sk.westland.core.enums.EServerData;
import sk.westland.core.event.PluginEnableEvent;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.ServerDataService;

@Component
public class AutoMessageEvent implements Listener, Runnable {

    @Autowired
    private PlayerService playerService;

    private final String autoMessagePrefix = ChatColor.of("#fffca6") + "§l[*]§r ";
    private int position = 0;
    private BukkitTask task;
    private final String[] autoMessages = new String[] {
            "Prepojením svojho minecraft účtu s discord účtom získaš rolu Trusted Membera a taktiež si zosynchronizuješ svoj ingame rank s rolou na discorde. Postup ako na to zistíš po napísaní príkazu /synchronizacia.",
            "Hlavný spôsob ako si zarobiť svoje prvé peniaze sú práce, ako bonus má každá práca zvlášť odmeny na každom leveli. Pre zamestnanie použi príkaz /prace.",
            "Zavadzia ti naša tabuľka, otravujú ťa zvuky reakcií v chate alebo chceš napríklad vypnúť prihlasovacie správy? Tieto nastavenia a oveľa viac si môžeš zmeniť vo svojom profile po napísaní príkazu /profil.",
            "Si hráč so zakúpenou originálnou verziou hry a nebaví ťa stále zadávať heslo pri prihlasovaní? Aktivuj si svoj premium účet príkazom /premium. Túto možnosť nepoužívaj pokiaľ nemáš zakúpenú hru!",
            "Residenciu vytvoríš pomocou drevenej motyčky, aktuálny limit residencie zobrazíš príkazom /res limits. Je limit nedostačujúci? Pre získanie väčšieho limitu a viac residencií si zakúp jeden z našich rankov /ranky alebo vylepšuj svoju hráčsku rolu /role.",
            "Podporiť nás môžeš rôznymi spôsobmi. Jedna z nich je zakúpenie ranku /ranky. Taktiež si môžeš kúpiť rôzne kľúče, špeciálne nástroje a chestky. Toto a oveľa viac nájdeš v našom /menu. Za každú podporu vám ďakujeme!",
            "Zakúp si svoj rank a získaj výhody, ktoré ťa určite nesklamú! Svoj rank môžeš zakúpiť v /ranky. Výhody rankov sú zobrazené na našom webe: https://westland.sk/store",
            "Zapoj sa do naších turnajov a získaj skvelé odmeny, prvé 3 miesta sú vždy odmenené! Viac o turnajoch sa dozvieš po napísaní príkazu /turnaje.",
            "Hlasovaním podporíš náš server a získaš odmeny v podobe peňazí, xp a vote kľúča. Tento kľúč je možné použiť na otvorenie boxu. K boxom sa dostaneš príkazom /boxy.",
            "Nevieš nájsť nejaký item alebo sa ti nechce ťažiť? Použi náš serverový obchod, kde nájdeš tie najhlavnejšie veci! Obchod otvoríš príkazom /shop.",
            "Chceš si vytvoriť vlastný warp alebo sa pozrieť na warpy ostatných hráčov? Ponuku warpov zobrazíš príkazom /pw. Počet možných warpov je určený podľa ranku.",
            "Nevieš predať nejaký item v shope? Predaj ho v aukciách! Pridaj, nastav cenu a už len čakaj, kto ho od teba odkúpi. Aukcie otvoríš príkazom /ah.",
            "Nezabudni si vybrať každý deň svoju dennú odmenu! Tieto odmeny nájdeš na našom spawne v nákupnej zóne.",
            "Chceš si vyskúšať svoje brnenie a nástroje oproti nejakému hráčovi? Navštív našu pvp arénu /pvp. V tejto aréne nie je možné stratiť veci.",
            "Navštív náš discord, kde sa vždy dozvieš všetky novinky, ktoré majú prísť. Odkaz na náš discord nájdeš v /discord.",
            "Nebaví ťa dlho bežať a hľadať miesto na dom? Použi príkaz /rtp ktorý ťa portne na náhodnú lokáciu a ušetrí tvoj čas.",
            "Zakúp si svojho vlastného koňa, ktorého budeš môcť spawnúť kedykoľvek pomocou špeciálneho sedla! Taktiež je možné tohoto koňa vylepšiť podľa vlastného uváženia! Toto všetko nájdeš po napísaní príkazu /stajne."
    };
    private static AutoMessageEvent autoMessageEvent = null;

    public AutoMessageEvent() {
        autoMessageEvent = this;
    }

    @Autowired
    private ServerDataService serverDataService;

    @EventHandler(ignoreCancelled = true)
    private void onPluginEnable(PluginEnableEvent event) {
        reStart();
    }

    public void reStart() {
        stop();

        if(serverDataService.getIntData(EServerData.AUTOMESSAGE_TIME) == 0)
            serverDataService.setIntData(EServerData.AUTOMESSAGE_TIME, 60);

        int data = serverDataService.getIntData(EServerData.AUTOMESSAGE_TIME);
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(WestLand.getInstance(), this, 20*10, (20L * data));
    }

    public void stop() {
        if(task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public void run() {
        String text = autoMessages[position % autoMessages.length];
        Bukkit.getOnlinePlayers().stream()
                .filter((player -> EPlayerOptions.SHOW_AUTOMESSAGE.getPlayerOptions(playerService.getWLPlayer(player))))
                .forEach((player -> player.sendMessage(" \n" +autoMessagePrefix + text + " \n ")));
        position++;
    }


    public static AutoMessageEvent getAutoMessageEvent() {
        return autoMessageEvent;
    }
}
