package sk.westland.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import sk.westland.core.entity.player.WLPlayer;

public class LocationHelper {

    public static Location getLocationFromString(String data, String regex) {
        String[] loc = data.split(regex);
        int x = Integer.parseInt(loc[0]);
        int y = Integer.parseInt(loc[1]);
        int z = Integer.parseInt(loc[2]);
        return new Location(getWorld("world"), x, y, z);
    }

    public static Location getLocationFromString(String data) {
        return getLocationFromString(data, ":");
    }

    public static String locationToString(Location loc) {
        return loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
    }

    public static World getWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);

        if(world == null)
            throw new NullPointerException("World doesnt exists");

        return world;
    }

    public static Location getLocation(String world, float x, float y, float z) {
        return new Location(getWorld(world), x, y, z);
    }

    public static Location getLocation(String world, double x, double y, double z) {
        return new Location(getWorld(world), x, y, z);
    }

    public static Location getLocation(String world, float x, float y, float z, float yaw) {
        Location location = new Location(getWorld(world), x, y, z);
        location.setYaw(yaw);
        return location;
    }

    public static Location getLocation(String world, float x, float y, float z, float yaw, float pitch) {
        Location location = new Location(getWorld(world), x, y, z);
        location.setYaw(yaw);
        location.setPitch(pitch);
        return location;
    }

    public static Location getLocation(String world, double x, double y, double z, float yaw, float pitch) {
        Location location = new Location(getWorld(world), x, y, z);
        location.setYaw(yaw);
        location.setPitch(pitch);
        return location;
    }

    public static boolean equalsBlockLocation(Location loc1, Location loc2) {
        return loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY() && loc1.getBlockZ() == loc2.getBlockZ();
    }

    public static boolean equalsBlockLocationIgnoreY(Location loc1, Location loc2) {
        return loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockZ() == loc2.getBlockZ();
    }

    public static String toBlockString(Location location) {
        return "x=" + location.getBlockX() + ",y=" + location.getBlockY() + ",z=" + location.getBlockZ();
    }

    public static String serialize(Location location) {
        return location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
    }

    public static Location clone(Location location) {
        return new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
    }

    public static String blockToString(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }

    public static double getDistanceSquared(WLPlayer p1, WLPlayer p2){
        return p1.getLocation().distanceSquared(p2.getLocation());
    }

}
