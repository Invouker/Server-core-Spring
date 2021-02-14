package sk.westland.core.utils.converter;


import org.apache.commons.lang3.SerializationUtils;
import sk.westland.core.quest.QuestTask;
import sk.westland.core.quest.action.ActionDataStorage;
import sk.westland.core.quest.storage.QuestProgressStorage;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.LinkedList;
import java.util.List;

@Converter
public class QuestProgressStorageConverter implements AttributeConverter<List<QuestProgressStorage>, String> {

    @Override
    public String convertToDatabaseColumn(List<QuestProgressStorage> attribute) {

        String questString;

        for(QuestProgressStorage quest : attribute) {

            QuestTask questTask = quest.getActiveTasks().get(0);
            ActionDataStorage test = quest.getTaskStorage(questTask);


            String questID = quest.getQuest().getId();
            String questState = quest.getQuestState().toString();



        }

        return null;
    }

    @Override
    public List<QuestProgressStorage> convertToEntityAttribute(String dbData) {
        return null;
    }




    public byte[] convertToDatabaseColumn(LinkedList<QuestProgressStorage> attribute) {
        return SerializationUtils.serialize(attribute);
    }


    public LinkedList<QuestProgressStorage> convertToEntityAttribute(byte[] dbData) {
        if(dbData == null || dbData.length <= 0)
            return new LinkedList<>();

        return SerializationUtils.deserialize(dbData);
    }


}
