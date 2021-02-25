package sk.westland.core.utils.converter;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListConverter implements AttributeConverter<Map<String, List<Integer>>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, List<Integer>> attribute) {
        String finalString = "";

        int x = 0;
        for(Map.Entry<String, List<Integer>> entry : attribute.entrySet()) {
            String jobName = entry.getKey();
            List<Integer> list = entry.getValue();

            finalString += "[" + jobName +":";
            for (int i = 0; i < list.size(); i++) {

                finalString += String.valueOf(list.get(i));

                if(i < list.size()-1)
                    finalString += ",";
                else finalString += "]";
            }
            if(attribute.size()-1 != x)
                finalString += "$";
            x++;
        }
        return finalString;
    }

    @Override
    public Map<String, List<Integer>> convertToEntityAttribute(String dbData) {
        Map<String, List<Integer>> map = new HashMap<>();

        String[] data = dbData.split("\\$");
        for(String jobData : data) {
            List<Integer> collected = new ArrayList<>();
            String collectedData = jobData.replace("]", "").replace("[", "");
            String[] collect = collectedData.split(":");

            String jobName;
            if(collect.length > 0)
                jobName = collect[0];
            else continue;

            String integers;
            if(collect.length > 1)
                integers = collect[1];
            else continue;

            for(String str : integers.split(","))
                collected.add(Integer.valueOf(str));

            map.put(jobName, collected);
        }
        return map;
    }
}