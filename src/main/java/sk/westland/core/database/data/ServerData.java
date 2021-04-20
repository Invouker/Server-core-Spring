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

    private String stringData = "";
    private int intData;
    private double doubleData;
    private long longData;
    private float floatData;
    private boolean booleanData;

    public ServerData() {
    }

    public ServerData(EServerData serverData, String data) {
        this.serverData = serverData;
        this.stringData = data;
    }

    public ServerData(EServerData serverData, long data) {
        this.serverData = serverData;
        this.longData = data;
    }

    public ServerData(EServerData serverData, float data) {
        this.serverData = serverData;
        this.floatData = data;
    }

    public ServerData(EServerData serverData, double data) {
        this.serverData = serverData;
        this.doubleData = data;
    }

    public ServerData(EServerData serverData, int data) {
        this.serverData = serverData;
        this.intData = data;
    }

    public ServerData(EServerData serverData, boolean data) {
        this.serverData = serverData;
        this.booleanData = data;
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

    public int getIntData() {
        return intData;
    }

    public void setIntData(int intData) {
        this.intData = intData;
    }

    public boolean getBooleanData() {
        return booleanData;
    }

    public void setBooleanData(boolean booleanData) {
        this.booleanData = booleanData;
    }
}
