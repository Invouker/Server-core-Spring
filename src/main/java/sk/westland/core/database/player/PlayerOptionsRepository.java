package sk.westland.core.database.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerOptionsRepository extends JpaRepository<PlayerOptions, Long>, PlayerID<PlayerOptions> {

}
