package sk.westland.core.database.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long>, PlayerID<UserData> {

    Optional<UserData> findByUuid(String uuid);
    Optional<UserData> findByUserName(String name);
    Optional<UserData> findByEmailAddress(String emailAddress);

}
