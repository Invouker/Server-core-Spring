package sk.westland.core.database.player;

import javax.persistence.EntityManager;
import java.util.Optional;

public class PlayerDataRepostiroy {

    private EntityManager entityManager;

    public PlayerDataRepostiroy(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<PlayerData> findByPlayerId(long id) {
        PlayerData author = entityManager.createNamedQuery("Author.findByPlayerId", PlayerData.class)
                .setParameter("playerId", id)
                .getSingleResult();
        return author != null ? Optional.of(author) : Optional.empty();
    }

    public Optional<PlayerData> save(PlayerData playerData) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(playerData);
            entityManager.getTransaction().commit();
            return Optional.of(playerData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
