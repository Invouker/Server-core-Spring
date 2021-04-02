package sk.westland.core.database.data;

import sk.westland.core.enums.EServerData;

import javax.persistence.*;

@Table(name = "wl_server_data")
@Entity
public class ServerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    private EServerData serverData;

    private String stringData;
    private Double doubleData;
    private Long longData;
    private Float floatData;

    public ServerData() {
    }


    public ServerData(EServerData serverData) {
        this.serverData = serverData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EServerData getServerData() {
        return serverData;
    }

    public void setServerData(EServerData serverData) {
        this.serverData = serverData;
    }

    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public Double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Double doubleData) {
        this.doubleData = doubleData;
    }

    public Long getLongData() {
        return longData;
    }

    public void setLongData(Long longData) {
        this.longData = longData;
    }

    public Float getFloatData() {
        return floatData;
    }

    public void setFloatData(Float floatData) {
        this.floatData = floatData;
    }
}
