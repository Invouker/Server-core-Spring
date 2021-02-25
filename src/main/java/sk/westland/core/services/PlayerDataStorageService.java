package sk.westland.core.services;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.UserData;
import sk.westland.core.database.player.UserOption;
import sk.westland.core.database.player.UserOptionRepository;
import sk.westland.core.database.player.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerDataStorageService implements Listener {

    private static Map<Player, Data> userMap = new HashMap<>();

    public PlayerDataStorageService() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(WestLand.getInstance(), () -> {
            saveAllUsers();
        }, 20*5l,20*60l); // Every 1 minutes will save the userRepository
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserOptionRepository userOptionRepository;

    public Data load(Player player) {
        UUID uuid = player.getUniqueId();
        if(isUserLoaded(player))
            return userMap.get(player);

        if(isUserRegistered(player)) {
            Optional<UserData> userData = userRepository.findByUuid(uuid.toString());
            Optional<UserOption> userOption = userOptionRepository.findByUuid(uuid.toString());
            if(userData.isPresent() && userOption.isPresent()) {
                Data data = new Data(userData.get(), userOption.get());
                userMap.put(player, data);
                Bukkit.getConsoleSender().sendMessage("Loading player from database!");
                return data;
            }
        }

        if(!userRepository.findByUuid(uuid.toString()).isPresent()) {

            UserData userData = new UserData(player.getName(), player.getUniqueId().toString(), 0, 0, 1, 0, -1, -1);
            UserOption userOption = new UserOption(player.getName(), player.getUniqueId().toString(), true, false, true);
            Data data = new Data(userData, userOption);
            userMap.put(player, data);
            Bukkit.getConsoleSender().sendMessage("Registring new user " + player.getName() + " to database!");
            return data;
        }
        return null;
    }

    public void save(Player player) {
        if(userRepository == null || userOptionRepository == null)
            throw new NullPointerException("userRepository == null");

        if(player == null)
            throw new NullPointerException("player == null");

        if(this.getUser(player) == null)
            load(player);

        this.userRepository.save(this.getUser(player).getUserData());
        this.userOptionRepository.save(this.getUser(player).getUserOption());
    }

    public Data getUser(Player player) {
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
        for(PlayerDataStorageService.Data data : userMap.values()) {
            if(data == null)
                data = new Data();

            if(data.getUserData() == null)
                data.setUserData(new UserData());

            if(data.getUserOption() == null)
                data.setUserOption(new UserOption());

            this.userRepository.save(data.getUserData());
        }

        for(PlayerDataStorageService.Data data : userMap.values()) {
            this.userOptionRepository.save(data.getUserOption());
        }
    }

    public class Data {

        private UserData userData;
        private UserOption userOption;

        public Data(UserData userData, UserOption userOption) {
            this.userData = userData;
            this.userOption = userOption;
        }

        public Data() {
            userData = new UserData();
            userOption = new UserOption();
        }

        public UserData getUserData() {
            return userData;
        }

        public void setUserData(UserData userData) {
            this.userData = userData;
        }

        public UserOption getUserOption() {
            return userOption;
        }

        public void setUserOption(UserOption userOption) {
            this.userOption = userOption;
        }
    }


}
