package sk.westland.core.utils.converter;

import sk.westland.core.enums.EBadge;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;

public class EnumSetEBadgeConverter implements AttributeConverter<EnumSet<EBadge>, String> {

    @Override
    public String convertToDatabaseColumn(EnumSet<EBadge> attribute) {
        StringBuilder stringBuilder = new StringBuilder();
        attribute.forEach(enumCLass ->
                stringBuilder.append(enumCLass.name()).append(",")
        );

        return stringBuilder.length() > 2 ? stringBuilder.substring(0, stringBuilder.length()-1) : "";
    }

    @Override
    public EnumSet<EBadge> convertToEntityAttribute(String dbData) {
        if(dbData == null)
            return EnumSet.noneOf(EBadge.class);

        if(dbData.length() <= 0)
            return EnumSet.noneOf(EBadge.class);

        String[] data = dbData.split(",");
        EnumSet<EBadge> enumSet = EnumSet.noneOf(EBadge.class);// = EnumSet.allOf(dbData);
        for (String enumer : data) {
            var value = EBadge.valueOf(enumer);

            enumSet.add(value);
        }
        return enumSet;
    }

}
