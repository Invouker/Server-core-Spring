package sk.westland.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.database.data.ServerDataRepository;
import sk.westland.core.enums.EServerData;

@Service
public class ServerDataService {

    @Autowired
    private ServerDataRepository serverDataRepository;

    private boolean exist(EServerData serverData) {
        return serverDataRepository.findByServerData(serverData).isPresent();
    }


}
