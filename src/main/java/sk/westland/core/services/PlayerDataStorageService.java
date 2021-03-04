package sk.westland.core.services;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.PlayerOptionsRepository;
import sk.westland.core.database.player.PlayerDataRepository;

import java.util.*;

public class PlayerDataStorageService {

    //private static Map<Player, Data> userMap = new HashMap<>();

    public PlayerDataStorageService() {

    }

    @Autowired
    private PlayerDataRepository playerDataRepository;

    @Autowired
    private PlayerOptionsRepository playerOptionsRepository;


    /*
    public Data load(Player player) {

        ChatInfo.GENERAL_INFO.sendAll("event:player" + player.getName());
        UUID uuid = player.getUniqueId();
        if(isUserLoaded(player))
            return userMap.get(player);

        if(isUserRegistered(player)) {
            Optional<PlayerData> userDataOptional = playerDataRepository.findByUuid(uuid.toString());
            Optional<UserOption> userOptionOptional = userOptionRepository.findByUuid(uuid.toString());
            if(userDataOptional.isPresent() && userOptionOptional.isPresent()) {

                PlayerData playerData = userDataOptional.get();
                if(playerData.getAlreadyJobRewarded() == null)
                    playerData.setAlreadyJobRewarded(new HashMap<>());

                if(playerData.getCraftingRecipe() == null)
                    playerData.setCraftingRecipe(new ArrayList<>());

                Data data = new Data(playerData, userOptionOptional.get());
                userMap.put(player, data);
                Bukkit.getConsoleSender().sendMessage("Loading player from database!");
                return data;
            }
        }

        if(!playerDataRepository.findByUuid(uuid.toString()).isPresent()) {

            PlayerData playerData = new PlayerData(player.getName(), player.getUniqueId().toString(), 0, 0, 1, 0, -1, -1);
            UserOption userOption = new UserOption(player.getName(), player.getUniqueId().toString(), true, false, true);
            Data data = new Data(playerData, userOption);
            userMap.put(player, data);
            Bukkit.getConsoleSender().sendMessage("Registring new user " + player.getName() + " to database!");
            return data;
        }
        return null;
    }

    public void save(Player player) {
        if(playerDataRepository == null || userOptionRepository == null)
            throw new NullPointerException("userRepository == null");

        if(player == null)
            throw new NullPointerException("player == null");

        if(this.getUser(player) == null)
            load(player);

        Data data = this.getUser(player);

        if(data.getUserData().getCraftingRecipe() == null)
            data.getUserData().setCraftingRecipe(new ArrayList<>());

        if(data.getUserData().getAlreadyJobRewarded() == null)
            data.getUserData().setAlreadyJobRewarded(new HashMap<>());

        this.userOptionRepository.save(this.getUser(player).getUserOption());
        this.playerDataRepository.save(this.getUser(player).getUserData());
    }

    public Data getUser(Player player) {
        return userMap.getOrDefault(player, null);
    }

    public boolean isUserLoaded(Player player) {
        return userMap.containsKey(player);
    }

    public boolean isUserRegistered(Player player) {
        return this.playerDataRepository.findByUuid(player.getUniqueId().toString()).isPresent();
    }

    public boolean unloadUser(Player player) {
        return userMap.remove(player) != null;
    }

    public void saveAllUsers() {
        Bukkit.getOnlinePlayers().forEach(this::save);
        /*
        for(PlayerDataStorageService.Data data : userMap.values()) {
            if(data == null)
                data = new Data();

            if(data.getUserData() == null)
                data.setUserData(new UserData());

            if(data.getUserOption() == null)
                data.setUserOption(new UserOption());

            if(data.getUserData().getCraftingRecipe() == null) {
                data.getUserData().setCraftingRecipe(new ArrayList<>());
                System.out.println("== null Crafting");
            }
            if(data.getUserData().getAlreadyJobRewarded() == null) {
                data.getUserData().setAlreadyJobRewarded(new HashMap<>());
                System.out.println("== null Already job rewarded");
            }


            ChatInfo.GENERAL_INFO.sendAll(data.getUserData().getAlreadyJobRewarded().toString());

            this.userRepository.save(data.getUserData());
        }

        for(PlayerDataStorageService.Data data : userMap.values()) {
            this.userOptionRepository.save(data.getUserOption());
        }*
    }

    public class Data {

        private PlayerData playerData;
        private UserOption userOption;

        public Data(PlayerData playerData, UserOption userOption) {
            this.playerData = playerData;
            this.userOption = userOption;
        }

        public Data() {
            playerData = new PlayerData();
            userOption = new UserOption();
        }

        public PlayerData getUserData() {
            return playerData;
        }

        public void setUserData(PlayerData playerData) {
            this.playerData = playerData;
        }

        public UserOption getUserOption() {
            return userOption;
        }

        public void setUserOption(UserOption userOption) {
            this.userOption = userOption;
        }
    }
*/

}
