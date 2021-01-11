package sk.westland.core.player;


import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.database.player.UserData;
import sk.westland.core.database.player.UserOption;
import sk.westland.core.services.PlayerDataStorageService;

import java.util.*;

public class WLPlayer  {

    private Player player;
    private PlayerDataStorageService.Data userData;

    public WLPlayer(Player player, PlayerDataStorageService.Data userData) {
        this.player = player;
        this.userData = userData;
    }

    public Player getPlayer() {
        return player;
    }

    // METHOD OVERRIDING

    public void addItemToInventory(ItemStack... itemStack) {
        player.getInventory().addItem(itemStack);
    }

    private UUID getUUID() {
        return player.getUniqueId();
    }

    public UserData getUserData() {
        return userData.getUserData();
    }

    public void setUserData(UserData userData) {
        this.userData.setUserData(userData);
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public String getName() {
        return player.getName();
    }

    public boolean hasPermission(String permission) { return player.hasPermission(permission); }


    /////

    public UserOption getUserOption() {
        return userData.getUserOption();
    }

    ///////////////

    // USER DATA

    public double getCoin() {
        return getUserData().getCoin();
    }

    public void setCoin(double coins) {
        getUserData().setCoin(coins);
    }

    public int getActiveJoinMessage() {
        return getUserData().getActiveJoinMessage();
    }

    public void setActiveJoinMessage(int activeJoinMessage) {
        getUserData().setActiveJoinMessage(activeJoinMessage);
    }

    public int getActiveQuitMessage() {
        return getUserData().getActiveQuitMessage();
    }

    public void setActiveQuitMessage(int activeQuitMessage) {
        getUserData().setActiveQuitMessage(activeQuitMessage);
    }

    //////////////
}
