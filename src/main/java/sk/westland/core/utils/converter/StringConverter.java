package sk.westland.core.utils.converter;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

public class StringConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");

        for(int i = 0; i < attribute.size(); i++) {
            String val = attribute.get(i).toLowerCase();

            if(i != 0) stringBuilder.append(",");

            stringBuilder.append(val);
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        List<String> list = new ArrayList<>();

        if(dbData == null)
            return list;

        if(dbData.length() <= 0 || dbData == null)
            return list;

        dbData = dbData.substring(1, dbData.length() - 1);

        for(String recipe : dbData.split(",")) {
            if(recipe.length() > 0)
                list.add(recipe.toLowerCase());
        }
        return list;
    }
}