package sk.westland.core.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Utils {

    public static void playSound(Location location, Sound sound, int distance) {
        playSound(location, sound, distance, 1, 1);
    }

    public static void playSound(Location location, Sound sound) {
        playSound(location, sound, 4, 1, 1);
    }

    public static void playSound(Location location, Sound sound, int distance, float a, float b) {
        Bukkit.getOnlinePlayers().stream()
                .filter((player) -> player.getLocation().distance(location) < distance)
                .forEach((player -> player.playSound(location, sound, a, b)));
    }

    public static void playArmAnimation(Player player) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer animation = protocolManager.createPacket(PacketType.Play.Server.ANIMATION, false);

        animation.getEntityModifier(player.getWorld()).write(0, player);
        animation.getIntegers().write(1, 0); // 0 arm swing, 3

        protocolManager.broadcastServerPacket(animation, player.getLocation(), 25);
    }
}
