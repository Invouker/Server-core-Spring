package sk.westland.core.items;

import net.minecraft.server.v1_16_R3.Item;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.WestLand;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {

    public static ItemBuilder ItemBuilder(Material material) {
        return new ItemBuilder(material);
    }

    @NotNull
    protected ItemStack is;

    /**
     * Create a new ItemBuilder over an existing ItemStack.
     *
     * @param is The ItemStack to create the ItemBuilder over (clone).
     */
    public ItemBuilder(@NotNull ItemStack is) {
        this.is = is.clone();
    }

    public ItemBuilder(@NotNull ItemStack is, int amount) {
        this.is = is.clone();
        is.setAmount(amount);
    }

    /**
     * Create a new ItemBuilder from Vanilla Material.
     *
     * @param m      The material of the item.
     * @param amount The amount of the item.
     */
    public ItemBuilder(@NotNull Material m, int amount)
    {
        is = new ItemStack(m, amount);
    }

    /**
     * Create a new ItemBuilder from Vanilla Material.
     *
     * @param m The material to create the ItemBuilder with.
     */
    public ItemBuilder(@NotNull Material m)
    {
        this(m, 1);
    }

    public ItemBuilder(@NotNull Material m, short s, @Nullable String name)
    {
        is = new ItemStack(m, s);

        ItemMeta im = is.getItemMeta();
        if (im != null)
        {
            im.setDisplayName(name);
            is.setItemMeta(im);
        }
    }

    public ItemBuilder(@NotNull Material m, @Nullable String name)
    {
        is = new ItemStack(m);

        ItemMeta im = is.getItemMeta();
        if (im != null)
        {
            im.setDisplayName(name);
            is.setItemMeta(im);
        }
    }

    /**
     * Add an enchant to the item.
     *
     * @param ench  The enchant to add
     * @param level The level
     */
    @NotNull
    public ItemBuilder addEnchant(@NotNull Enchantment ench, int level)
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    @NotNull
    public ItemBuilder addEnchGlow(boolean state) {
        if(!state)
            return this;

        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        im.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        addItemFlag(ItemFlag.HIDE_ENCHANTS);

        is.setItemMeta(im);
        return this;
    }

    /**
     * Add multiple enchants at once.
     *
     * @param enchants The enchants to add.
     */
    @NotNull
    public ItemBuilder addEnchantments(HashMap<String, Integer> enchants)
    {
        if ((enchants == null) || (enchants.size() == 0))
            return this;
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        for (Map.Entry<String, Integer> entry : enchants.entrySet())
        {
            if (entry.getKey() == null)
                continue;
            Enchantment ench = Enchantment.getByKey(NamespacedKey.minecraft(entry.getKey()));
            if (ench != null)
                im.addEnchant(ench, entry.getValue(), true);
        }

        is.setItemMeta(im);
        return this;
    }

    /**
     * Add multiple enchants at once.
     *
     * @param enchants The enchants to add.
     */
    @NotNull
    public ItemBuilder addEnchantments(@NotNull Map<Enchantment, Integer> enchants)
    {
        if (enchants.size() == 0)
            return this;
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet())
        {
            if (entry.getKey() == null)
                continue;
            im.addEnchant(entry.getKey(), entry.getValue(), true);
        }

        is.setItemMeta(im);
        return this;
    }

    /**
     * Adds selected ItemFlag on the item
     *
     * @param flag selected ItemFlag
     */
    @NotNull
    public ItemBuilder addItemFlag(@NotNull ItemFlag flag)
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        im.addItemFlags(flag);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Hides all ItemFlags on the item
     *
     * @return ItemBuilder
     */
    @NotNull
    public ItemBuilder hideAllFlags()
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        for (ItemFlag flag : ItemFlag.values())
            im.addItemFlags(flag);

        is.setItemMeta(im);
        return this;
    }

    /**
     * Adds selected ItemFlag on the item by name
     *
     * @param flagName selected ItemFlag
     */
    @NotNull
    public ItemBuilder addItemFlag(String flagName)
    {
        if (flagName == null)
            return this;
        ItemFlag flag;
        try
        {
            flag = ItemFlag.valueOf(flagName);
        }
        catch (IllegalArgumentException ex)
        {
            return this;
        }

        return addItemFlag(flag);
    }

    /**
     * Adds Glowing effect on the item
     */
    @NotNull
    public ItemBuilder setGlowing()
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        im.addEnchant(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 0, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Set type of item spawner
     */
    @NotNull
    public ItemBuilder setSpawnerType(EntityType entityType)
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        ItemMeta meta = is.getItemMeta();
        BlockStateMeta bsm = (BlockStateMeta) meta;
        BlockState bs = bsm.getBlockState();

        CreatureSpawner cs = (CreatureSpawner)bs;
        cs.setSpawnedType(entityType);
        bsm.setBlockState(bs);
        is.setItemMeta(meta);
        return this;
    }

    /**
     * Adds lines of lore on the item
     *
     * @param lines Array of lore
     */
    @NotNull
    public ItemBuilder addLore(String... lines) {
        if (lines == null || lines.length == 0)
            return this;
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        List<String> finalLore = im.getLore();
        if (finalLore == null)
            finalLore = new ArrayList<>();

        finalLore.addAll(Arrays.asList(lines));

        im.setLore(finalLore);
        is.setItemMeta(im);
        return this;
    }

    @NotNull
    public ItemBuilder setAmount(int amount)
    {
        is.setAmount(amount);
        return this;
    }
/*
    public static void setSize(ItemStack stack, int size) {
        Material material = stack.getType();
        try {
            Class<?> cbclass = getNMSClass("org.bukkit.craftbukkit", "util.CraftMagicNumbers");
            Method m = cbclass.getDeclaredMethod("getItem", Material.class);
            Class<?> result = m.invoke(stack, material).getClass();
            if (!result.getCanonicalName().split("\\.")[result.getCanonicalName().split("\\.").length-1].toLowerCase().equalsIgnoreCase("item")) {
                while(!result.getCanonicalName().split("\\.")[result.getCanonicalName().split("\\.").length-1].toLowerCase().equalsIgnoreCase("item")) {
                    result = result.getSuperclass();
                }
            }
            Object myItem = getNMSClass("net.minecraft.server", "Items").getDeclaredField(material.name()).get(null);
            Field f = result.getDeclaredField("maxStackSize");
            f.setAccessible(true);
            f.setInt(myItem, size);
        } catch (Exception e) {e.printStackTrace();}

        //return CraftItemStack.asBukkitCopy(myItem);
    }

    private static Class<?> getNMSClass(String path, String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName(path + "." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setMaxStackSize(Material m, int amount) {
        try {
            String packageVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            Class<?> magicClass = Class.forName("org.bukkit.craftbukkit." + packageVersion + ".util.CraftMagicNumbers");
            Method method = magicClass.getDeclaredMethod("getItem", Material.class);
            Object item = method.invoke(null, m);
            Class<?> itemClass = Class.forName("net.minecraft.server." + packageVersion + ".Item");
            Field field = itemClass.getDeclaredField("maxStackSize");
            field.setAccessible(true);
            field.setInt(item, amount);
            Field mf = Material.class.getDeclaredField("maxStack");
            mf.setAccessible(true);
            mf.setInt(m, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ItemStack setStackSize(ItemStack itemStack, int size) {
        net.minecraft.server.v1_16_R3.ItemStack nmsIS = null;
        try {
            itemStack.getType().getMaxStackSize();
            nmsIS = CraftItemStack.asNMSCopy(itemStack);

            Field maxStackSizeField = Item.class.getDeclaredField("maxStackSize");
            maxStackSizeField.setAccessible(true);
            maxStackSizeField.setInt(Item., size);
            System.out.println("Max Stack size: " + maxStackSizeField.getInt(item));
        }catch (Exception e) {
            e.printStackTrace();
        }


            CraftItemStack craftstack = (CraftItemStack) items;
            net.minecraft.server.v1_16_R3.ItemStack nmsStack = craftstack.getMaxStackSize()

            Field maxStack = nmsStack.getItem().getClass().getDeclaredField("maxStackSize");
            maxStack.setAccessible(true);
            maxStack.setInt(nmsStack, amount);

            int nmsStackSize = maxStack.invoke(nmsStack.getItem());
        }
        catch(Throwable t)    {    }

        return CraftItemStack.asBukkitCopy(nmsIS);
    }


    public ItemBuilder setMaxStackSize(int amount) {
        net.minecraft.server.v1_16_R3.ItemStack otherItem = CraftItemStack.asNMSCopy(this.is);
        otherItem.setCount(amount);
        is = CraftItemStack.asBukkitCopy(otherItem);
        return this;
    }*/

    public static void modifyMaxStack(Item item, int amount) {
        try {
            Field f = Item.class.getDeclaredField("maxStackSize");
            f.setAccessible(true);
            f.setInt(item, amount);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Change the durability of the item.
     *
     * @param dur The durability to set it to.
     */
    @NotNull
    public ItemBuilder setDurability(short dur)
    {

        ItemMeta im = is.getItemMeta();
        if (im instanceof Damageable)
        {
            ((Damageable) im).setDamage(dur);
            is.setItemMeta(im);
        }
        return this;
    }

    /**
     * Sets infinity durability on the item by setting the durability to Short.MAX_VALUE.
     */
    @NotNull
    public ItemBuilder setInfinityDurability()
    {
        ItemMeta im = is.getItemMeta();
        if (im instanceof Damageable)
        {
            ((Damageable) im).setDamage(Short.MAX_VALUE);
            is.setItemMeta(im);
        }
        return this;
    }

    @NotNull
    public ItemBuilder setUnbreakable()
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        im.setUnbreakable(true);

        is.setItemMeta(im);
        return this;
    }

    /**
     * Sets the armor color of a leather armor piece. Works only on leather armor pieces.
     *
     * @param color The color to set it to.
     */
    @NotNull
    public ItemBuilder setLeatherArmorColor(@NotNull Color color)
    {
        ItemMeta im = is.getItemMeta();
        if (im instanceof LeatherArmorMeta)
        {
            ((LeatherArmorMeta) im).setColor(color);
            is.setItemMeta(im);
        }
        return this;
    }

    @NotNull
    public ItemBuilder setLeatherArmorColor(@NotNull DyeColor color)
    {
        return setLeatherArmorColor(color.getColor());
    }


    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    @NotNull
    public ItemBuilder setLore(@Nullable List<String> lore)
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    @NotNull
    public ItemBuilder setLore(@NotNull String... lore)
    {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder removeLoreLine(final String line) {
        ItemMeta im = is.getItemMeta();
        if (im == null || im.getLore() == null)
            return this;

        List<String> lore = new ArrayList<>(im.getLore());
        if (!lore.contains(line)) {
            return this;
        }
        lore.remove(line);
        im.setLore((List)lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(final int index) {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        List<String> lore = new ArrayList<>(im.getLore());
        if (index < 0 || index > lore.size()) {
            return this;
        }
        lore.remove(index);
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    /**
     * Set the lore line of the item.
     *
     * @param text The lore to change it to.
     * @param pos The position of line to change.
     */
    public ItemBuilder setLoreLine(int pos, String text) {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        if (im.getLore() == null)
            im.setLore(Collections.singletonList(""));

        List<String> lore = new ArrayList<>(im.getLore());
        for (int i = 0; i <= pos + 1 && lore.size() <= pos; ++i) {
            lore.add("§f ");
        }
        lore.set(pos, "§f" + text);
        im.setLore(lore);
        is.setItemMeta(im);

        return this;
    }

    /**
     * Set the displayname of the item.
     *
     * @param name The name to change it to.
     */
    @NotNull
    public ItemBuilder setName(@Nullable String name)
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }


    @NotNull
    public ItemBuilder setAttackDamageBonus(double amount)
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        // Remove old
        im.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE);

        // Set new
        im.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));

        return this;
    }

    @NotNull
    public ItemBuilder setAttackSpeedBonus(double amount)
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        // Remove old
        im.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);

        // Set new
        im.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", amount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));

        return this;
    }

    public static final String Attribute_CanPlaceOn = "CanPlaceOn";

    @NotNull
    public ItemBuilder addCanPlaceOn(Material... materials)
    {
        String[] existing = Nbt.getNbt_StringArray(is, Attribute_CanPlaceOn);

        List<String> newList = new ArrayList<>(Arrays.asList(existing));
        for (Material mat : materials)
            newList.add(mat.name());

        is = Nbt.setNbt_StringArray(is, Attribute_CanPlaceOn, newList);
        return this;
    }

    @NotNull
    public ItemBuilder setCanPlaceOn(Material... materials)
    {
        List<String> list = new ArrayList<>();
        for (Material mat : materials)
            list.add(mat.name());

        is = Nbt.setNbt_StringArray(is, Attribute_CanPlaceOn, list);
        return this;
    }

    public static final String Attribute_CanDestroy = "CanDestroy";

    @NotNull
    public ItemBuilder addCanDestroy(Material... materials)
    {
        String[] existing = Nbt.getNbt_StringArray(is, Attribute_CanDestroy);

        List<String> newList = new ArrayList<>(Arrays.asList(existing));
        for (Material mat : materials)
            newList.add(mat.name());

        is = Nbt.setNbt_StringArray(is, Attribute_CanDestroy, newList);
        return this;
    }

    @NotNull
    public ItemBuilder setCanDestroy(Material... materials)
    {
        List<String> list = new ArrayList<>();
        for (Material mat : materials)
            list.add(mat.name());

        is = Nbt.setNbt_StringArray(is, Attribute_CanDestroy, list);
        return this;
    }

    @NotNull
    public ItemBuilder setArmor(int amount)
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        // Remove old
        im.removeAttributeModifier(Attribute.GENERIC_ARMOR);

        // Set new
        im.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", amount, AttributeModifier.Operation.ADD_NUMBER));

        return this;
    }

    @NotNull
    public ItemBuilder setArmorToughness(int amount)
    {
        ItemMeta im = is.getItemMeta();
        if (im == null)
            return this;

        // Remove old
        im.removeAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS);

        // Set new
        im.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", amount, AttributeModifier.Operation.ADD_NUMBER));

        return this;
    }

    @NotNull
    public ItemBuilder setNbt_String(String key, String value)
    {
        is = Nbt.setNbt_String(is, key, value);
        return this;
    }

    @NotNull
    public ItemBuilder setNbt_Int(String key, int value)
    {
        is = Nbt.setNbt_Int(is, key, value);
        return this;
    }

    @NotNull
    public ItemBuilder setNbt_Bool(String key, boolean value)
    {
        is = Nbt.setNbt_Bool(is, key, value);
        return this;
    }

    public ItemBuilder setNbt_Double(String key, double value) {
        is = Nbt.setNbt_Double(is, key, value);
        return this;
    }

    @NotNull
    public ItemBuilder setNbt_Long(String key, long value)
    {
        is = Nbt.setNbt_Long(is, key, value);
        return this;
    }

    @NotNull
    public ItemBuilder setModelId(int modelId)
    {
        is = Nbt.setNbt_Int(is, "CustomModelData", modelId);

        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        is.setType(material);
        return this;
    }

    public ItemBuilder setCustomItem(CustomItem item) {
        final NamespacedKey NBT_PER_KEY = new NamespacedKey(WestLand.getInstance(), "ITEM_ID_NAME");
        ItemMeta itemMeta = is.getItemMeta();
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        data.set(NBT_PER_KEY, PersistentDataType.STRING, item.itemID());
        is.setItemMeta(itemMeta);

        setModelId(item.getModelID());
        return this;
    }

    public ItemBuilder applyDurability(short damage) {

        if(damage == 0)
            return this;

        ItemMeta im = is.getItemMeta();

        if(im instanceof Damageable) {
            Damageable damageable = (Damageable) im;

            if(damageable.getDamage()+damage > is.getType().getMaxDurability()) {
                is.setType(Material.AIR);
                playBreakSound();
            }

            damageable.setDamage(damageable.getDamage() + damage);

            if(damageable.getDamage() > is.getType().getMaxDurability()) {
                is.setType(Material.AIR);
                playBreakSound();
            }


            is.setItemMeta((ItemMeta) damageable);
        }
        return this;
    }

    private void playBreakSound() {
        Bukkit.getOnlinePlayers()
                .forEach((target)-> {
                    target.playSound(target.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
                });
    }

    public static int getModelId(@NotNull ItemStack is)
    {
        return Nbt.getNbt_Int(is, "CustomModelData", 0);
    }


    /**
     * Build Item from ItemBuilder to ItemStack
     *
     * @return ItemStack of create item
     */
    @NotNull
    public ItemStack build()
    {
        return is;
    }


}