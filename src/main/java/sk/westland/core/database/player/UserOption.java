package sk.westland.core.database.player;

import javax.persistence.*;

@Table(name = "wl_player_options")
@Entity
public class UserOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nickname")
    private String name;

    private String uuid;

    private boolean showJoinMessage = true;
    private boolean showQuitMessage = false;

    private boolean showDeathMessage = true;

    public UserOption() {
    }

    public UserOption(String name, String uuid, boolean showJoinMessage, boolean showQuitMessage, boolean showDeathMessage) {
        this.name = name;
        this.uuid = uuid;
        this.showJoinMessage = showJoinMessage;
        this.showQuitMessage = showQuitMessage;
        this.showDeathMessage = showDeathMessage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
