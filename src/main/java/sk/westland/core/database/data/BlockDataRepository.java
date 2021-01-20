package sk.westland.core.database.data;

import org.bukkit.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlockDataRepository extends JpaRepository<BlockData, Long> {

    Optional<BlockData> findByBlockLocation(Location blockLocation);

    List<BlockData> findByOwnerUUID(UUID uuid);

    List<BlockData> findByOwnerName(String ownerName);
}
