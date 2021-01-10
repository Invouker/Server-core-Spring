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

    private WLPlayer WLPlayer;
    private Player player;
    private UserData userData;

    public WLPlayer(Player player, UserData userData) {
        this.player = player;
        this.userData = userData;

        if(userData == null)
            Bukkit.getLogger().log(Level.WARNING, "UserData == null");
    }

    public Player getPlayer() {
        return player;
    }

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

    public double getCoin() {
        if(userData == null)
            throw new NullPointerException("Userdata is not loaded!");
        return userData.getCoin();
    }

    public void setCoin(double coins) {
        if(userData == null)
            throw new NullPointerException("Userdata is not loaded!");
        userData.setCoin(coins);
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }
}
