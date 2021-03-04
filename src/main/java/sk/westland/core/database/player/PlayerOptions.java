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

    private boolean showDeathMessage = true;

    public PlayerOptions() {
    }

    public PlayerOptions(long playerId) {
        this.playerId = playerId;
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
}
