package sk.westland.core.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import sk.westland.core.App;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.EBadge;
import sk.westland.core.enums.EPlayerOptions;
import sk.westland.core.enums.EServerData;
import sk.westland.core.services.*;

import java.util.Arrays;
import java.util.Optional;


public class PlaceHolder extends PlaceholderExpansion implements Relational {

    private final Plugin plugin;
    private final PlayerService playerService;
    private final ScoreboardService scoreboardService;
    private final ServerDataService serverDataService;

    public PlaceHolder(Plugin plugin, PlayerService playerService, ScoreboardService scoreboardService, ServerDataService serverDataService) {
        this.plugin = plugin;
        this.playerService = playerService;
        this.scoreboardService = scoreboardService;
        this.serverDataService = serverDataService;
    }


    @Override
    public boolean persist(){
        return true;
    }
    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     *
     * @return always true since we do not have any dependencies.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return "XpresS";
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>The identifier has to be lowercase and can't contain _ or %
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "westland";
    }

    /**
     * This is the version of this expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null) {
            return "";
        }

        if(playerService.getWLPlayer(player) == null)
            return "";

        // %westland_gems%
        if(identifier.equalsIgnoreCase("scoreboard")) {
            return String.valueOf(scoreboardService.playerHasScoreboard(player));
        }
        // %westland_gems%
        if(identifier.equalsIgnoreCase("gems")) {
            return String.valueOf(Double.valueOf(playerService.getWLPlayer(player).getGems()).intValue());
        }
        // %westland_shards%
        if(identifier.equalsIgnoreCase("shards")) {
            return String.valueOf(Double.valueOf(playerService.getWLPlayer(player).getShards()).intValue());
        }

        // %westland_votes_total%
        if(identifier.equalsIgnoreCase("votes_total")) {
            int totalVotes = serverDataService.getIntData(EServerData.VOTES_TOTAL);
            return String.valueOf(totalVotes);
        }

        // %westland_voteparty_votes%
        if(identifier.equalsIgnoreCase("voteparty_votes")) {
            int totalVotes = serverDataService.getIntData(EServerData.VOTES_TOTAL);
            return String.valueOf(totalVotes % VotePartyService.VOTEPARTY);
        }

        // %westland_voteparty_total%
        if(identifier.equalsIgnoreCase("voteparty_total")) {
            return String.valueOf(VotePartyService.VOTEPARTY);
        }

        // %westland_voteparty_votes_until%
        if(identifier.equalsIgnoreCase("voteparty_votes_until")) {
            int totalVotes = serverDataService.getIntData(EServerData.VOTES_TOTAL);
            return String.valueOf(VotePartyService.VOTEPARTY - (totalVotes % VotePartyService.VOTEPARTY));
        }

        if(identifier.equalsIgnoreCase("prefix")) {

            //Arrays.stream(App.getService(VaultService.class).getPerms().getGroups()).filter((group )-> !group.equalsIgnoreCase("resourcepack")).toList();
            String group = App.getService(VaultService.class).getPerms().getPlayerGroups(player)[0];
            if(group == null)
                return "Group is null";

            if(group.equals("resourcepack") && App.getService(VaultService.class).getPerms().getPlayerGroups(player).length > 0)
                group = App.getService(VaultService.class).getPerms().getPlayerGroups(player)[1];

            return switch(group) {
                case "leader" ->  player.hasPermission("westland_prefix") ? "妎" : "Leader";
                case "admin" -> player.hasPermission("westland_prefix") ? "院" : "Admin";
                case "resourcepack" -> player.hasPermission("westland_prefix") ? "院" : "Admin";
                case "legion" -> player.hasPermission("westland_prefix") ? "民" : "Legion";
                default -> "default";
            };

           /*
            if(player.hasPermission("westland_prefix")) {
                return App.getService(VaultService.class).getPerms().getPrimaryGroup(player) + " s RP";
            }
            return App.getService(VaultService.class).getPerms().getPrimaryGroup(player) + " bez RP";
           * */
        }

        // %westland_rola%
        if(identifier.equalsIgnoreCase("rola")) {
            //Roľník, Farmár, Osadník, Bojovník, Kovár
            if(player.hasPermission("rola.kovar"))
                return "Kovár";

            if(player.hasPermission("rola.bojovnik"))
                return "Bojovník";

            if(player.hasPermission("rola.osadnik"))
                return "Osadník";

            if(player.hasPermission("rola.farmar"))
                return "Farmár";

            if(player.hasPermission("rola.rolnik"))
                return "Roľník";
        }


        {
            String var = identifier.toLowerCase();
            Optional<EPlayerOptions> ePlayerOptionsOptional =
                    Arrays.stream(EPlayerOptions.values()).filter(ePlayerOptions -> ePlayerOptions.name().toLowerCase().equals(var)).findFirst();

            if(ePlayerOptionsOptional.isPresent())
                return String.valueOf(ePlayerOptionsOptional.get().getPlayerOptions(playerService.getWLPlayer(player)));

        }

        return null;
    }

    @Override
    public String onPlaceholderRequest(Player player, Player two, String identifier) {

        // %rel_westland_distance%
        if (identifier.equals("distance")) {
            return String.valueOf(player.getLocation().distance(two.getLocation()));
        }

        // %rel_westland_prefix%
        if (identifier.equals("prefix")) {
            ResourcePackService resourcePackService = App.getService(ResourcePackService.class);
            var hasResourcePack = resourcePackService.hasPlayerResourcePack(player);
            sk.westland.core.services.ResourcePackService.Group group = resourcePackService.getGroupByPrimary(two);
            if(hasResourcePack) {

                return group.getRpPrefix();
            } else {
                return group.getPrefixColor() + "§l" + group.getPrefix() + "§r";
            }
        }

        // %rel_westland_prefix%
        if (identifier.equals("badge")) {
            PlayerService playerService = App.getService(PlayerService.class);
            if(playerService == null)
                return null;

            ResourcePackService resourcePackService = App.getService(ResourcePackService.class);
            var hasResourcePack = resourcePackService.hasPlayerResourcePack(player);

            WLPlayer wlPlayer = playerService.getWLPlayer(player);
            EBadge eBadge = wlPlayer.getActiveBadge();

        }
        return null;
    }
}
