package sk.westland.core.utils.converter;

import org.bukkit.inventory.ItemStack;
import sk.westland.core.items.ItemSerializer;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Converter
public class InventoryItemConverter implements AttributeConverter<Map<Integer, ItemStack>, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(Map<Integer, ItemStack> items) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            try (DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(gzipOutputStream))) {
                dataoutputstream.writeInt(items.size());

                for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
                    dataoutputstream.writeByte(entry.getKey());
                    ItemSerializer.save(entry.getValue(), dataoutputstream);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public Map<Integer, ItemStack> convertToEntityAttribute(byte[] dbData) {
        Map<Integer, ItemStack> items = new HashMap<>();

        if(dbData == null) {
            return items;
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dbData);

        try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
            try (DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(gzipInputStream))) {
                int itemCount = datainputstream.readInt();

                for (int i = 0; i < itemCount; i++) {
                    int slot = datainputstream.readByte();
                    ItemStack itemStack = ItemSerializer.load(datainputstream);

                    if(itemStack == null) {
                        System.out.println("StashItemsConverter.convertToEntityAttribute() : ItemStack == null");
                        continue;
                    }

                    items.put(slot, itemStack);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return items;
    }
}
