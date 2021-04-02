package sk.westland.core.database.player;

import java.util.Optional;

public interface PlayerID<T> {

    Optional<T> findByPlayerId(long id);
}
