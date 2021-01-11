package sk.westland.core.database.player;

import javax.persistence.*;

@Table(name = "wl_player_data")
@Entity
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nickname")
    private String name;

    private String uuid;

    private double coin = 0;

    private int level = 1;
    private int exp = 0;

    private int activeJoinMessage = -1;
    private int activeQuitMessage = -1;

    public UserData() { }

    public UserData(String name, String uuid, double coin, int level, int exp, int activeJoinMessage, int activeQuitMessage) {
        this.name = name;
        this.uuid = uuid;
        this.coin = coin;
        this.level = level;
        this.exp = exp;
        this.activeJoinMessage = activeJoinMessage;
        this.activeQuitMessage = activeQuitMessage;
    }

    public long getId() {
        return id;
    }

    public int getActiveJoinMessage() {
        return activeJoinMessage;
    }

    public void setActiveJoinMessage(int activeJoinMessage) {
        this.activeJoinMessage = activeJoinMessage;
    }

    public int getActiveQuitMessage() {
        return activeQuitMessage;
    }

    public void setActiveQuitMessage(int activeQuitMessage) {
        this.activeQuitMessage = activeQuitMessage;
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

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
