package sk.westland.core.database.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByUuid(String uuid);

    Optional<UserData> findById(long id);

    Optional<UserData> findByName(String name);
}
