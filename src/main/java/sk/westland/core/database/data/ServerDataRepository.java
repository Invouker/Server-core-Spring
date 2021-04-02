package sk.westland.core.database.data;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.westland.core.enums.EServerData;

import java.util.Optional;

public interface ServerDataRepository extends JpaRepository<ServerData, Long> {

    Optional<ServerData> findByServerData(EServerData serverData);
}
