package sk.westland.core.player;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.openjsse.sun.security.ssl.SSLLogger;
import sk.westland.core.ChatInfo;
import sk.westland.core.database.player.UserData;

import java.util.*;
import java.util.logging.Level;

public class WLPlayer  {

    private Player player;
    private UserData userData;

    public WLPlayer(Player player, UserData userData) {
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
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public String getName() {
        return player.getName();
    }

    public boolean hasPermission(String permission) { return player.hasPermission(permission); }

    ///////////////

    // USER DATA

    public double getCoin() {
        return userData.getCoin();
    }

    public void setCoin(double coins) {
        userData.setCoin(coins);
    }

    public int getActiveJoinMessage() {
        return userData.getActiveJoinMessage();
    }

    public void setActiveJoinMessage(int activeJoinMessage) {
        userData.setActiveJoinMessage(activeJoinMessage);
    }

    public int getActiveQuitMessage() {
        return userData.getActiveQuitMessage();
    }

    public void setActiveQuitMessage(int activeQuitMessage) {
        userData.setActiveQuitMessage(activeQuitMessage);
    }

    //////////////
}
