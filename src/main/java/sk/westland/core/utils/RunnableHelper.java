package sk.westland.core.utils;

import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.scheduler.BukkitRunnable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;

import java.util.List;
import java.util.function.Consumer;

@Component
public class RunnableHelper {

    public static void runTask(Runnable runnable) {

        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(WestLand.getInstance());
    }

    public static void runTaskAsynchronously(Runnable runnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskAsynchronously(WestLand.getInstance());
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLater(WestLand.getInstance(), delay);
    }

    public static void runTaskLater(Runnable runnable) {
        runTaskLater(runnable, RunnableDelay.DELAY());
    }

    public static void runTaskLaterAsynchronously(Runnable runnable, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLaterAsynchronously(WestLand.getInstance(), delay);
    }

    public static void runTaskLaterAsynchronously(Runnable runnable) {
        runTaskLaterAsynchronously(runnable, 1);
    }

    public static void runTaskTimer(Runnable runnable, long delay, long period) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimer(WestLand.getInstance(), (period <= 20 * 10) ? (delay + RunnableDelay.DELAY()) : delay, period);
    }

    public static void runTaskTimer(Runnable runnable, long period) {
        runTaskTimer(runnable, 0, period);
    }

    public static void runTaskTimerAsynchronously(Runnable runnable, long delay, long period) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimerAsynchronously(WestLand.getInstance(), (period <= 20 * 10) ? (delay + RunnableDelay.DELAY()) : delay, period);
    }

    public static void runTaskTimerAsynchronously(Runnable runnable, long period) {
        runTaskTimerAsynchronously(runnable, 0, period);
    }

    public static <T, ID> void save(JpaRepository<T, ID> repository, T entity, Consumer<T> doneListener, boolean aSync) {
        if(repository == null) {
            Log.error("RunnableHelper.save = Repository is null");
            return;
        }

        if(entity == null) {
            return;
        }

        if(aSync)
        runTaskAsynchronously(() -> {
            T newEntity = repository.save(entity);

            if(doneListener == null) {
                return;
            }

            runTask(() -> {
                doneListener.accept(newEntity);
            });
        });
        else
            runTask(() -> {
                T newEntity = repository.save(entity);

                if(doneListener == null) {
                    return;
                }

                runTask(() -> {
                    doneListener.accept(newEntity);
                });
            });
    }

    public static <T, ID> void save(JpaRepository<T, ID> repository, T entity) {
        if(repository == null) {
            Log.error("RunnableHelper.save = Repository is null");
            return;
        }

        if(entity == null) {
            return;
        }

        runTaskAsynchronously(() -> {
            T newEntity = repository.save(entity);
        });
    }

    public static <T, ID> void saveAll(JpaRepository<T, ID> repository, List<T> entities, Consumer<List<T>> doneListener) {
        runTaskAsynchronously(() -> {
            List<T> newEntities = entities.size() > 0 ? repository.saveAll(entities) : entities;

            if(doneListener == null) {
                return;
            }

            runTask(() -> {
                doneListener.accept(newEntities);
            });
        });
    }

    public static <T, ID> void saveAll(JpaRepository<T, ID> repository, List<T> entities) {
        runTaskAsynchronously(() -> {
            List<T> newEntities = entities.size() > 0 ? repository.saveAll(entities) : entities;
        });
    }

}
