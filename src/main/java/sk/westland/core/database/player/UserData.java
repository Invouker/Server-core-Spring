package sk.westland.core.database.player;

import javax.persistence.*;

@Table(name = "wl_users")
@Entity
@NamedQueries({
        @NamedQuery(name = "UserData.findByUserName",
                query = "SELECT u FROM UserData u WHERE u.userName = :userName"),

        @NamedQuery(name = "UserData.findById",
                query = "SELECT u FROM UserData u WHERE u.id = :id"),

        @NamedQuery(name = "UserData.findByUuid",
                query = "SELECT u FROM UserData u WHERE u.uuid = :uuid")
})
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String userName;

    @Column(unique = true)
    private String uuid;

    @Column(nullable = true)
    private String emailAddress;

    private long firstConnection;

    public UserData() {
    }

    public UserData(String userName, String uuid) {
        this.userName = userName;
        this.uuid = uuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public long getFirstConnection() {
        return firstConnection;
    }

    public void setFirstConnection(long firstConnection) {
        this.firstConnection = firstConnection;
    }
}
