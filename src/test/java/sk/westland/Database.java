package sk.westland;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {


    public Database() {

    }

    @Test
    public void test() {
        Map<String, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        map.put("test", list);
        map.put("test2", list);

        String testedData = convertToDatabaseColumn(map);
        System.out.println(testedData);

        Map<String, List<Integer>> entity = convertToEntityAttribute(testedData);

        AtomicInteger i = new AtomicInteger();
        entity.forEach((name, integerList) -> {
            StringBuilder out = new StringBuilder(name);
            out.append(" ");
            integerList.forEach((integer) -> out.append(integer + ","));

            System.out.println("\nOut ID: " + i.getAndIncrement() + ": " + out);

        });
    }


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

    public Map<String, List<Integer>> convertToEntityAttribute(String dbData) {
        Map<String, List<Integer>> map = new HashMap<>();

        String[] data = dbData.split("\\$");
        for(String jobData : data) {
            List<Integer> collected = new ArrayList<>();
            String collectedData = jobData.replace("]", "").replace("[", "");
            String[] collect = collectedData.split(":");
            String jobName = collect[0];
            String integers = collect[1];

            for(String str : integers.split(","))
                collected.add(Integer.valueOf(str));

            map.put(jobName, collected);
        }
        return map;
    }

}
