package sk.westland.core.database.player;

import javax.persistence.*;

@Table
@Entity
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nickname")
    private String name;

    private String uuid;

    private double coin;

    public UserData() { }

    public UserData(String name, String uuid, double coin) {
        this.name = name;
        this.uuid = uuid;
        this.coin = coin;
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

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }
}
