package sk.westland.core.discord.ranksync;

import net.dv8tion.jda.api.entities.User;
import org.bukkit.entity.Player;

public class PlayerSync {

    private Player player;
    private String playerDiscordId;
    private User user;
    private String code;

    public PlayerSync(String playerDiscordId, String code) {
        this.playerDiscordId = playerDiscordId;
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPlayerDiscordId() {
        return playerDiscordId;
    }

    public void setPlayerDiscordId(String playerDiscordId) {
        this.playerDiscordId = playerDiscordId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
