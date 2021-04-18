package sk.westland.core.database.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankDataRepository extends JpaRepository<RankData, Long>, PlayerID<RankData> {

    Optional<RankData> findByPlayerId(long playerId);
    Optional<RankData> findByDiscordUuid(String discordUuid);

}
