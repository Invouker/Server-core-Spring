package sk.westland.core.database.player;

import javax.persistence.*;

@Table(name = "wl_player_options")
@Entity
public class PlayerOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long playerId;

    private boolean showJoinMessage = true;
    private boolean showQuitMessage = false;
    private boolean chatReactionSound = true;
    private boolean showDeathMessage = true;
    private boolean showScoreboard = true;
    private boolean teleportEffect = true;
    private boolean showAutoMessage = true;

    public PlayerOptions() {
    }

    public PlayerOptions(long playerId) {
        this.playerId = playerId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public boolean isChatReactionSound() {
        return chatReactionSound;
    }

    public void setChatReactionSound(boolean chatReactionSound) {
        this.chatReactionSound = chatReactionSound;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isShowJoinMessage() {
        return showJoinMessage;
    }

    public void setShowJoinMessage(boolean showJoinMessage) {
        this.showJoinMessage = showJoinMessage;
    }

    public boolean isShowQuitMessage() {
        return showQuitMessage;
    }

    public void setShowQuitMessage(boolean showQuitMessage) {
        this.showQuitMessage = showQuitMessage;
    }

    public boolean isShowDeathMessage() {
        return showDeathMessage;
    }

    public void setShowDeathMessage(boolean showDeathMessage) {
        this.showDeathMessage = showDeathMessage;
    }

    public boolean isShowScoreboard() {
        return showScoreboard;
    }

    public void setShowScoreboard(boolean showScoreboard) {
        this.showScoreboard = showScoreboard;
    }

    public boolean isTeleportEffect() {
        return teleportEffect;
    }

    public void setTeleportEffect(boolean teleportEffect) {
        this.teleportEffect = teleportEffect;
    }

    public boolean isShowAutoMessage() {
        return showAutoMessage;
    }

    public void setShowAutoMessage(boolean showAutoMessage) {
        this.showAutoMessage = showAutoMessage;
    }
}
