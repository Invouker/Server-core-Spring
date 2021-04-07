package sk.westland.core.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.items.ItemBuilder;

import java.util.Locale;
import java.util.Random;

public class Utils {

    public static void playSound(Sound sound) {
        Bukkit.getOnlinePlayers().forEach((player) -> {
            player.playSound(player.getLocation(), sound, 1f ,1f);
        });
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1f ,1f);
    }

    public static void playSound(Location location, Sound sound, int distance) {
        playSound(location, sound, distance, 1, 1);
    }

    public static void playSound(Location location, Sound sound) {
        playSound(location, sound, 4, 1, 1);
    }

    public static void playSound(Location location, Sound sound, int distance, float a, float b) {
        Bukkit.getOnlinePlayers().stream()
                .filter((player) -> player.getLocation().getWorld().getName().equals(location.getWorld().getName()) && player.getLocation().distance(location) < distance)
                .forEach((player -> player.playSound(location, sound, a, b)));
    }

    public static void playArmAnimation(Player player) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer animation = protocolManager.createPacket(PacketType.Play.Server.ANIMATION, false);

        animation.getEntityModifier(player.getWorld()).write(0, player);
        animation.getIntegers().write(1, 0); // 0 arm swing, 3

        protocolManager.broadcastServerPacket(animation, player.getLocation(), 25);
    }

    public static boolean locationResPermission(Player player, Location location, ResFlag resFlag) {
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);

        if(res != null) {
            ResidencePermissions perms = res.getPermissions();
            return perms.playerHas(player, resFlag.getFlagName(), false);
        }

        return true;
    }

    public static boolean locationResPermission(String player, Location location, ResFlag resFlag) {
        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(location);

        if(res != null) {
            ResidencePermissions perms = res.getPermissions();
            return perms.playerHas(player, resFlag.getFlagName(), false);
        }

        return true;
    }

    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz123456789".toCharArray();

    public static String generateRandomChars(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(
                    Utils.BaseMath.getRandomBoolean() ?
                            alphabet[Utils.BaseMath.getRandomInt(alphabet.length-1)] :
                            Character.toUpperCase(alphabet[Utils.BaseMath.getRandomInt(alphabet.length-1)])
            );
        }
        return stringBuilder.toString();
    }

    public static void addPlayerSpawner(Player player, EntityType entityType) {
        addPlayerSpawner(player, entityType, 1);
    }

    public static void addPlayerSpawner(Player player, EntityType entityType, int amount) {
        String name = entityType.toString().replace("_", " ").toLowerCase(Locale.ROOT);
        String capitalizedName = StringUtils.capitalize(name);
        ItemStack spawner = new ItemBuilder(Material.SPAWNER)
                .setName("§e" + capitalizedName + " Spawner")
                .setSpawnerType(entityType)
                .setAmount(amount)
                .build();

        player.getInventory().addItem(spawner);
    }

    public static class BaseMath {

        private static final Random random = new Random();

        public static Random getRandom() {
            return random;
        }

        public static int getRandomInt() {
            return random.nextInt();
        }

        public static int getRandomInt(int bound) {
            return random.nextInt(bound);
        }

        public static int getRandomMinMaxInt(int min, int max) {
            return random.nextInt(max - min) + min;
        }

        public static boolean getRandomBoolean() {
            return random.nextBoolean();
        }
    }

}
