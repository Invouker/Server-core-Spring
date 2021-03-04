package sk.westland.core.database.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerOptionsRepository extends JpaRepository<PlayerOptions, Long> {

    Optional<PlayerOptions> findByPlayerId(long id);
    //List<UserOption> findByUuid(String uuid);
}
