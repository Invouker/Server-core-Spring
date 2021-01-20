package sk.westland.core.utils.converter;

import org.bukkit.Location;
import sk.westland.core.utils.LocationHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocationConverter implements AttributeConverter<Location, String> {

    @Override
    public String convertToDatabaseColumn(Location location) {
        return location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getWorld().getName();
    }

    @Override
    public Location convertToEntityAttribute(String dbData) {
        String[] exp = dbData.split(",");

        if(exp.length != 4)
            return null;

        try {
            double x = Double.parseDouble(exp[0]);
            double y = Double.parseDouble(exp[1]);
            double z = Double.parseDouble(exp[2]);
            String world = exp[3];

            return LocationHelper.getLocation(world, x, y, z);
        } catch (NumberFormatException ex) {
            return null;
        }

    }
}