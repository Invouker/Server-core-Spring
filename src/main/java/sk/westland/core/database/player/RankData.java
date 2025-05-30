package sk.westland.core.database.player;

import javax.persistence.*;

@Table(name = "wl_rank_sync")
@Entity
public class RankData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long playerId;
    private String discordUuid;

    public RankData() {
    }

    public RankData(long playerId, String discordUuid) {
        this.playerId = playerId;
        this.discordUuid = discordUuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getDiscordUuid() {
        return discordUuid;
    }

    public void setDiscordUuid(String discordUuid) {
        this.discordUuid = discordUuid;
    }

}
