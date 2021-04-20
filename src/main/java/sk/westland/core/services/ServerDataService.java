package sk.westland.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.database.data.ServerData;
import sk.westland.core.database.data.ServerDataRepository;
import sk.westland.core.enums.EServerData;

import java.util.Optional;

public class ServerDataService {

    @Autowired
    private ServerDataRepository serverDataRepository;

    public String getStringData(EServerData eServerData) {
        if(!serverDataRepository.findByServerData(eServerData).isPresent())
            return null;

        return serverDataRepository.findByServerData(eServerData).get().getStringData();
    }

    public void setStringData(EServerData eServerData, String data) {
        Optional<ServerData> serverDataOptional = serverDataRepository.findByServerData(eServerData);

        if(!serverDataOptional.isPresent()) {
            serverDataRepository.save(new ServerData(eServerData, data));
            return;
        }

        ServerData serverData = serverDataOptional.get();
        serverData.setStringData(data);
        serverDataRepository.save(serverData);
    }

    public Double getDoubleData(EServerData eServerData) {
        if(!serverDataRepository.findByServerData(eServerData).isPresent())
            return 0d;

        return serverDataRepository.findByServerData(eServerData).get().getDoubleData();
    }

    public void setDoubleData(EServerData eServerData, double data) {
        Optional<ServerData> serverDataOptional = serverDataRepository.findByServerData(eServerData);

        if(!serverDataOptional.isPresent()) {
            serverDataRepository.save(new ServerData(eServerData, data));
            return;
        }

        ServerData serverData = serverDataOptional.get();
        serverData.setDoubleData(data);
        serverDataRepository.save(serverData);
    }

    public Long getLongData(EServerData eServerData) {
        if(!serverDataRepository.findByServerData(eServerData).isPresent())
            return 0L;

        return serverDataRepository.findByServerData(eServerData).get().getLongData();
    }

    public void setLongData(EServerData eServerData, long data) {
        Optional<ServerData> serverDataOptional = serverDataRepository.findByServerData(eServerData);

        if(!serverDataOptional.isPresent()) {
            serverDataRepository.save(new ServerData(eServerData, data));
            return;
        }

        ServerData serverData = serverDataOptional.get();
        serverData.setLongData(data);
        serverDataRepository.save(serverData);
    }

    public Float getFloatData(EServerData eServerData) {
        if(!serverDataRepository.findByServerData(eServerData).isPresent())
            return 0F;

        return serverDataRepository.findByServerData(eServerData).get().getFloatData();
    }

    public void setFloatData(EServerData eServerData, float data) {
        Optional<ServerData> serverDataOptional = serverDataRepository.findByServerData(eServerData);

        if(!serverDataOptional.isPresent()) {
            serverDataRepository.save(new ServerData(eServerData, data));
            return;
        }

        ServerData serverData = serverDataOptional.get();
        serverData.setFloatData(data);
        serverDataRepository.save(serverData);
    }

    public int getIntData(EServerData eServerData) {
        if(!serverDataRepository.findByServerData(eServerData).isPresent())
            return 0;

        return serverDataRepository.findByServerData(eServerData).get().getIntData();
    }

    public void setIntData(EServerData eServerData, int data) {
        Optional<ServerData> serverDataOptional = serverDataRepository.findByServerData(eServerData);

        if(!serverDataOptional.isPresent()) {
            serverDataRepository.save(new ServerData(eServerData, data));
            return;
        }

        ServerData serverData = serverDataOptional.get();
        serverData.setIntData(data);
        serverDataRepository.save(serverData);
    }

    public boolean getBooleanData(EServerData eServerData) {
        if(!serverDataRepository.findByServerData(eServerData).isPresent())
            return false;

        return serverDataRepository.findByServerData(eServerData).get().getBooleanData();
    }

    public void setBooleanData(EServerData eServerData, boolean data) {
        Optional<ServerData> serverDataOptional = serverDataRepository.findByServerData(eServerData);

        if(!serverDataOptional.isPresent()) {
            serverDataRepository.save(new ServerData(eServerData, data));
            return;
        }

        ServerData serverData = serverDataOptional.get();
        serverData.setBooleanData(data);
        serverDataRepository.save(serverData);
    }
}
