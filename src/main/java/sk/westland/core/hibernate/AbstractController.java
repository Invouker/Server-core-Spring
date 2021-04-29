package sk.westland.core.hibernate;

import sk.westland.core.WestLand;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class AbstractController<T> {

    private final Class<T> entityClass;
    private static EntityManagerFactory entityManagerFactory;

    private final static boolean remoteDatabase = true;
    private final static String databaseHost = "casa45.fakaheda.eu";
    private final static int databasePort = 3306;
    private final static String databaseSchema = "338529_mysql_db";
    private final static String databaseUsername = "338529_mysql_db";
    private final static String databasePassword = "MCWdq6jQ2D5K5Zjp";

    public AbstractController(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    protected synchronized void create(T entity) throws RunicException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(entity);

        entityTransaction.commit();
        entityManager.close();
    }

    public void create(T entity, AsyncCallBackExceptionHandler asyncCallBackExceptionHandler) {
        new Thread(() -> {
            try {
                create(entity);
            } catch (RunicException e) {
                asyncCallBackExceptionHandler.error(e);
            }
        }).start();
    }

    @Transactional
    protected synchronized Optional<T> edit(T entity) throws RunicException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.merge(entity);

        entityTransaction.commit();
        entityManager.close();
        return Optional.of(entity);
    }

    public synchronized void edit(T entity, AsyncCallBackObject<T> asyncCallBackObject, AsyncCallBackExceptionHandler asyncCallBackExceptionHandler) {
        new Thread(() -> {
            try {
                asyncCallBackObject.done(edit(entity));
            } catch (RunicException e) {
                asyncCallBackExceptionHandler.error(e);
            }
        }).start();
    }

    @Transactional
    protected synchronized void remove(T entity) throws RunicException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.remove(entityManager.merge(entity));

        entityTransaction.commit();
        entityManager.close();
    }

    public void remove(T entity, AsyncCallBackExceptionHandler asyncCallBackExceptionHandler) {
        new Thread(() -> {
            try {
                remove(entity);
            } catch (RunicException e) {
                asyncCallBackExceptionHandler.error(e);
            }
        }).start();
    }

    protected synchronized Optional<T> find(Object id) throws RunicException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        T e = entityManager.find(entityClass, id);

        entityTransaction.commit();
        entityManager.close();

        return e == null ? Optional.empty() : Optional.of(e);
    }

    public void find(Object id, AsyncCallBackObject<T> asyncCallBackObject, AsyncCallBackExceptionHandler asyncCallBackExceptionHandler) {
        new Thread(() -> {
            try {
                asyncCallBackObject.done(find(id));
            } catch (RunicException e) {
                asyncCallBackExceptionHandler.error(e);
            }
        }).start();
    }

    protected synchronized List<T> findAll() throws RunicException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        List<T> e = getEntityManager().createQuery(cq).getResultList();

        entityTransaction.commit();
        entityManager.close();
        return e;
    }

    public void findAll(AsyncCallBackObjectList<T> asyncCallBackObjectList, AsyncCallBackExceptionHandler asyncCallBackExceptionHandler) {
        new Thread(() -> {
            try {
                asyncCallBackObjectList.done(findAll());
            } catch (RunicException e) {
                asyncCallBackExceptionHandler.error(e);
            }
        }).start();
    }

    protected EntityManager getEntityManager() throws RunicException {
        setupEntityManagerFactory();
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            return entityManagerFactory.createEntityManager();
        } else {
            throw new EntityManagerNotInitializedException();
        }
    }

    private void setupEntityManagerFactory() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            Properties properties = new Properties();

            if (remoteDatabase) {
                properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
                properties.put("javax.persistence.jdbc.url", "jdbc:mysql://" + databaseHost + ":" + databasePort + "/" + databaseSchema + "?autoReconnect=true");
                properties.put("javax.persistence.jdbc.user", databaseUsername);
                properties.put("javax.persistence.jdbc.password", databasePassword);
                properties.put("packagesToScan", "sk.westland.core.database");
                properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
                properties.put("datasource.max-active", "2");
            } else {
                properties.put("javax.persistence.jdbc.driver", "org.sqlite.JDBC");
                properties.put("javax.persistence.jdbc.url", "jdbc:sqlite:./" + WestLand.getInstance().getDataFolder().getPath() + "/database" + "?autoReconnect=true");
                properties.put("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
                properties.put("datasource.max-active", "2");
            }

            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit", properties);
        }
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
