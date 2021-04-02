package sk.westland.core.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import sk.westland.core.enums.EPlayerOptions;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.ScoreboardService;

import java.util.Arrays;
import java.util.Optional;


public class PlaceHolder extends PlaceholderExpansion {

    private Plugin plugin;
    private PlayerService playerService;
    private ScoreboardService scoreboardService;

    public PlaceHolder(Plugin plugin, PlayerService playerService, ScoreboardService scoreboardService) {
        this.plugin = plugin;
        this.playerService = playerService;
        this.scoreboardService = scoreboardService;
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
            return String.valueOf(playerService.getWLPlayer(player).getGems());
        }
        // %westland_shards%
        if(identifier.equalsIgnoreCase("shards")) {
            return String.valueOf(playerService.getWLPlayer(player).getShards());
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
}
