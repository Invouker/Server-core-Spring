package sk.westland.core.database.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserOptionRepository extends JpaRepository<UserOption, Long> {

    Optional<UserOption> findByUuid(String uuid);
}
