package sk.westland.core.services;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.UserData;
import sk.westland.core.database.player.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerDataStorageService implements Listener {

    private static Map<Player, UserData> userMap = new HashMap<>();

    public PlayerDataStorageService() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(WestLand.getInstance(), () -> {
            saveAllUsers();
        }, 20*5l,20*60l); // Every 1 minutes will save the userRepository
    }

    @Autowired
    private UserRepository userRepository;

    public UserData load(Player player) {
        UUID uuid = player.getUniqueId();
        if(isUserLoaded(player))
            return userMap.get(player);

        if(isUserRegistered(player)) {
            Optional<UserData> userData = userRepository.findByUuid(uuid.toString());
            if(userData.isPresent()) {
                userMap.put(player, userData.get());
                Bukkit.getConsoleSender().sendMessage("Loading player from database!");
                return userData.get();
            }
        }

        if(!userRepository.findByUuid(uuid.toString()).isPresent()) {
            UserData userData = new UserData(player.getName(), player.getUniqueId().toString(), 0);
            userMap.put(player, userData);
            Bukkit.getConsoleSender().sendMessage("Registring new user " + player.getName() + " to database!");
            return userData;
        }
        return null;
    }

    public UserData save(Player player) {
        if(userRepository == null)
            throw new NullPointerException("userRepository == null");

        if(player == null)
            throw new NullPointerException("player == null");

        if(this.getUser(player) == null)
            load(player);

        return this.userRepository.save(this.getUser(player));
    }

    public UserData getUser(Player player) {
        return this.userMap.getOrDefault(player, null);
    }

    public boolean isUserLoaded(Player player) {
        return this.userMap.containsKey(player);
    }

    public boolean isUserRegistered(Player player) {
        return this.userRepository.findByUuid(player.getUniqueId().toString()).isPresent();
    }

    public boolean unloadUser(Player player) {
        return this.userMap.remove(player) != null;
    }

    public void saveAllUsers() {
        this.userRepository.saveAll(userMap.values());
    }

}
