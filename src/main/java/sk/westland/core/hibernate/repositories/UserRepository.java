package sk.westland.core.hibernate.repositories;

import sk.westland.core.database.player.UserData;
import sk.westland.core.hibernate.AbstractController;
import sk.westland.core.hibernate.AsyncCallBackExceptionHandler;
import sk.westland.core.hibernate.AsyncCallBackObject;
import sk.westland.core.hibernate.RunicException;

import java.util.Optional;

public class UserRepository extends AbstractController<UserRepository> {

    public UserRepository() {
        super(UserRepository.class);
    }

    public Optional<UserData> findByUuid(String uuid) throws RunicException {
        UserData userData = getEntityManager().createNamedQuery("UserData.findByUuid", UserData.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
        return userData != null ? Optional.of(userData) : Optional.empty();
    }

    public Optional<UserData> findByUserName(String userName) throws RunicException {
        UserData userData = getEntityManager().createNamedQuery("UserData.findByUserName", UserData.class)
                .setParameter("userName", userName)
                .getSingleResult();
        return userData != null ? Optional.of(userData) : Optional.empty();
    }

    public Optional<UserData> findById(long id) throws RunicException {
        UserData userData = getEntityManager().createNamedQuery("UserData.findById", UserData.class)
                .setParameter("id", id)
                .getSingleResult();
        return userData != null ? Optional.of(userData) : Optional.empty();
    }/*Optional<UserData> findByUserName(String name);
    Optional<UserData> findByEmailAddress(String emailAddress);
    Optional<UserData> findById(long id);*/
}
