package sk.westland.core.database.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerDataRepository extends JpaRepository<PlayerData, Long>, PlayerID<PlayerData> {

    Optional<PlayerData> findByPlayerId(long id);

    //Optional<PlayerData> findByUuid(String uuid);
    //Optional<PlayerData> findByName(String name);
}
