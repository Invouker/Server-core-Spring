package sk.westland.core.database.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestRepository extends JpaRepository<UserQuestData, Long> {

    List<UserQuestData> findByName(String name);
    List<UserQuestData> findByUuid(String uuid);

}
