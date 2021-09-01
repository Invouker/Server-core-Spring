package sk.westland.core.items;

import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTReadLimiter;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ItemSerializer {

    public static void save(@NotNull ItemStack itemStack, DataOutput dataOutput) {
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = new NBTTagCompound();
        nmsItemStack.save(tag);

        try {
            NBTCompressedStreamTools.a(tag, dataOutput);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Nullable
    public static byte[] save(@NotNull ItemStack itemStack) {
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        NBTTagCompound tag = new NBTTagCompound();
        nmsItemStack.save(tag);

        try {
            NBTCompressedStreamTools.a(tag, byteArrayOutputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return byteArrayOutputStream.toByteArray();
    }

    @Nullable
    public static String saveToBase64(@NotNull ItemStack itemStack) {
        return Base64.getEncoder().encodeToString(save(itemStack));
    }

    @Nullable
    public static byte[] saveAll(@NotNull Map<Integer, ItemStack> items) {
        if (items.size() == 0)
            return null;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(gzipOutputStream))
        ) {
            // Size
            dataoutputstream.writeInt(items.size());

            // Content
            for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
                // Item index
                dataoutputstream.writeInt(entry.getKey());
                // ItemStack
                ItemSerializer.save(entry.getValue(), dataoutputstream);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        // Do not place into the try-catch block
        return byteArrayOutputStream.toByteArray();
    }

    @Nullable
    public static byte[] saveAll(@NotNull List<ItemStack> items) {
        return saveAll(items.toArray(new ItemStack[0]));
    }

    @Nullable
    public static byte[] saveAll(@NotNull Inventory inventory) {
        return saveAll(inventory.getContents());
    }

    @Nullable
    public static byte[] saveAll(@NotNull ItemStack... items) {
        // Convert to map
        // Remove empty items
        Map<Integer, ItemStack> map = new HashMap<>();
        {
            for (int i = 0; i < items.length; i++) {
                @Nullable
                ItemStack is = items[i];
                if (is == null || is.getType() == Material.AIR)
                    continue;
                map.put(i, is);
            }
        }

        return saveAll(map);
    }

    @Nullable
    public static ItemStack load(DataInput dataInput) {
        try {
            NBTTagCompound readedTag = NBTCompressedStreamTools.a(dataInput, NBTReadLimiter.a);
            net.minecraft.world.item.ItemStack newItemStak = net.minecraft.world.item.ItemStack.a(readedTag);
            return CraftItemStack.asBukkitCopy(newItemStak);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static ItemStack load(@NotNull byte[] data, int offset, int length)
    {
        try(ByteArrayInputStream byteInput = new ByteArrayInputStream(data, offset, length)) {
            NBTTagCompound readedTag = NBTCompressedStreamTools.a(byteInput);
            net.minecraft.world.item.ItemStack newItemStak = net.minecraft.world.item.ItemStack.a(readedTag);
            return CraftItemStack.asBukkitCopy(newItemStak);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    @Nullable
    public static ItemStack load(@NotNull byte[] data, int offset) {
        return load(data, offset, data.length - offset);
    }
    @Nullable
    public static ItemStack load(@NotNull byte[] data) {
        return load(data, 0, data.length);
    }

    @Nullable
    public static ItemStack loadFromBase64(@NotNull String base64) {
        return load(Base64.getDecoder().decode(base64));
    }

    @NotNull
    public static Map<Integer, ItemStack> loadAll(@NotNull byte[] data, int offset) {

        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data, offset, data.length - offset);
                GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
                DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(gzipInputStream))
        ) {
            int itemCount = datainputstream.readInt();
            assert itemCount >= 0;
            if (itemCount == 0)
                return new HashMap<>(); // Should not happen as NULL would be in database

            @NotNull final Map<Integer, ItemStack> items = new HashMap<>();

            for (int i = 0; i < itemCount; i++) {
                int index = datainputstream.readInt();
                assert index >= 0;

                ItemStack itemStack = ItemSerializer.load(datainputstream);

                items.put(index, itemStack);
            }

            return items;
        } catch (IOException ex) {
            ex.printStackTrace();
            return new HashMap<>();
        }
    }
    @NotNull
    public static Map<Integer, ItemStack> loadAll(@NotNull byte[] data) {
        return loadAll(data, 0);
    }

    /**
     * Load all items into Inventory.
     * It is recommended to clear the inventory before loading into it - some items will be overwritten but in case amount of items in database does not match inventory size (ie. slots are skipped or index is outside of the inventory), those are returned from this function.
     *
     * @param data      Stored ItemStack data
     * @param inventory Inventory to load into
     * @return Items which failed to fit into the inventory.
     */
    @NotNull
    public static Map<Integer, ItemStack> loadAllInto(@NotNull byte[] data, int offset, @NotNull Inventory inventory) {
        @NotNull
        Map<Integer, ItemStack> items = loadAll(data, offset);
        @NotNull
        Map<Integer, ItemStack> overflowItems = new HashMap<>();

        int inventorySize = inventory.getSize();

        for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            int index = entry.getKey();
            assert index >= 0;

            if (index > inventorySize)
                overflowItems.put(index, entry.getValue());
            else
                inventory.setItem(index, entry.getValue());
        }

        return overflowItems;
    }
    public static Map<Integer, ItemStack> loadAllInto(@NotNull byte[] data, @NotNull Inventory inventory) {
        return loadAllInto(data, 0, inventory);
    }
}
