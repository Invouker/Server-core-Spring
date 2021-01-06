package sk.wildwest.core;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import sk.wildwest.core.items.Nbt;

public class ComponentBuilder  {

    public static ComponentBuilder empty() {
        return new ComponentBuilder(new TextComponent());
    }

    public static ComponentBuilder text( String text) {
        return new ComponentBuilder(new TextComponent(text));
    }

    public static ComponentBuilder legacy(@NotNull String plainText) {
        TextComponent component = new TextComponent();
        for(BaseComponent w : TextComponent.fromLegacyText(plainText))
            if(w != null)
                component.addExtra(w);
        return new ComponentBuilder(component);
    }

    public static ComponentBuilder item(@NotNull ItemStack is) {
        String name_s = Nbt.getNbt_String(is, "display.Name");

        BaseComponent[] name_bc = ComponentSerializer.parse(name_s);

        BaseComponent component = name_bc.length == 0 || name_bc[0] == null ? new TranslatableComponent("item." + is.getType().getKey().getNamespace() + "." + is.getType().getKey().getKey()) : name_bc[0];
        for(int i = 1; i < name_bc.length; i++)
            component.addExtra(name_bc[i]);

        component.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_ITEM,
                new BaseComponent[] { new TextComponent(convertItemStackToJson(is)) }
        ));

        return new ComponentBuilder(component);
    }

    public static ComponentBuilder key(@NotNull String key) {
        return new ComponentBuilder(new KeybindComponent(key));
    }


    public static String convertItemStackToJson(ItemStack itemStack) {
        net.minecraft.server.v1_16_R2.ItemStack nms = CraftItemStack.asNMSCopy(itemStack);
        return "{ \"id\":\"" + itemStack.getType().getKey().toString() + "\" \"tag\":" + (nms.getTag() == null ? "{}" : nms.getTag().asString()) + "}";
    }

    public ComponentBuilder(BaseComponent component) {
        this.component = component;
    }

    private BaseComponent component;

    public ComponentBuilder color(ChatColor color) {
        component.setColor(color);
        return this;
    }
    public ComponentBuilder bold(boolean value) {
        component.setBold(value);
        return this;
    }
    public ComponentBuilder italic(boolean value) {
        component.setItalic(value);
        return this;
    }
    public ComponentBuilder underlined(boolean value) {
        component.setUnderlined(value);
        return this;
    }
    public ComponentBuilder strikethrough(boolean value) {
        component.setStrikethrough(value);
        return this;
    }

    public ComponentBuilder insertion(String value) {
        component.setInsertion(value);
        return this;
    }

    public ComponentBuilder click(ClickEvent event) {
        component.setClickEvent(event);
        return this;
    }
    public ComponentBuilder hover(HoverEvent event) {
        component.setHoverEvent(event);
        return this;
    }

    public ComponentBuilder extra(BaseComponent extraComponent) {
        component.addExtra(extraComponent);
        return this;
    }

    public BaseComponent build() {
        return component;
    }
}
