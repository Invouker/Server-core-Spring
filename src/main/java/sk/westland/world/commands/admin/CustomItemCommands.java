package sk.westland.world.commands.admin;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.utils.ChatInfo;
import sk.westland.world.commands.Converter.PlayerArgConverter;
import sk.westland.world.commands.suggestion.ItemResourceSuggestion;
import sk.westland.world.commands.suggestion.ItemSuggestion;
import sk.westland.world.commands.suggestion.PlayerSuggestion;
import sk.westland.world.items.Materials;

@Component
@CommandLine.Command(name = "customitem")
@HasPermission("commands.customitem")
public class CustomItemCommands implements Runnable {

    @Override
    public void run() {

    }

    @Component
    @CommandLine.Command(name = "resource")
    @HasPermission("commands.customitem.resource")
    public static class Resources implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", completionCandidates = ItemResourceSuggestion.class, arity = "1")
        private String resourceSuggestion;

        @CommandLine.Parameters(index = "1", completionCandidates = PlayerSuggestion.class, converter = PlayerArgConverter.class)
        private Player player;

        @Override
        public void run() {
            Materials.Resources items = Materials.Resources.findItemName(resourceSuggestion);
            if(items == null) {
                ChatInfo.ERROR.send(context.getSender(), "Item sa nenašiel!");
                return;
            }

            if(player == null)
                player = context.getPlayer();

            player.getInventory().addItem(items.getItem());
            ChatInfo.SUCCESS.send(player, "Dostal si §6" + items.getItem().getItemMeta().getDisplayName() + "§f do inventára!");

            if((context.getSender() instanceof Player) && !player.getName().equals(context.getPlayer().getName()))
                ChatInfo.SUCCESS.send(context.getSender(), "Dal si hráčovi §6" + player.getName() + "§f, item §6" + items.getItem().getItemMeta().getDisplayName() + "§f do inventára!");
        }
    }

    @Component
    @CommandLine.Command(name = "items")
    @HasPermission("commands.customitem.items")
    public static class Items implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", completionCandidates = ItemSuggestion.class, arity = "1")
        private String resourceSuggestion;

        @CommandLine.Parameters(index = "1", completionCandidates = PlayerSuggestion.class, converter = PlayerArgConverter.class)
        private Player player;

        @Override
        public void run() {
            Materials.Items items = Materials.Items.findItemName(resourceSuggestion);
            if(items == null) {
                ChatInfo.ERROR.send(context.getSender(), "Item sa nenašiel");
                return;
            }

            if(player == null)
                player = context.getPlayer();

            player.getInventory().addItem(items.getItem());
            ChatInfo.SUCCESS.send(player, "Dostal si §6" + items.getItem().getItemMeta().getDisplayName() + "§f do inventára!");

            if((context.getSender() instanceof Player) && !player.getName().equals(context.getPlayer().getName()))
                ChatInfo.SUCCESS.send(context.getSender(), "Dal si hráčovi §6" + player.getName() + "§f, item §6" + items.getItem().getItemMeta().getDisplayName() + "§f do inventára!");
        }
    }

    @Component
    @CommandLine.Command(name = "wand")
    @HasPermission("commands.customitem.resource")
    public static class SellWand implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", arity = "1")
        private int durability;

        @CommandLine.Parameters(index = "1", completionCandidates = PlayerSuggestion.class, converter = PlayerArgConverter.class)
        private Player player;

        @Override
        public void run() {
            if(player == null)
                player = context.getPlayer();

            Materials.Items items = Materials.Items.WORTH_WAND;

            String WAND_NBT_KEY = "WAND_DURABILITY";
            player.getInventory().addItem(new ItemBuilder(items.getItem())
                    .setNbt_Int(WAND_NBT_KEY, durability)
                    .setLoreLine(6, "§7Počet použití: §f" + durability).build());
            ChatInfo.SUCCESS.send(player, "Dostal si " + items.getItem().getItemMeta().getDisplayName() + " do inventára!");

            if((context.getSender() instanceof Player) && !player.getName().equals(context.getPlayer().getName()))
                ChatInfo.SUCCESS.send(context.getSender(), "Dal si hráčovi " + player.getName() + ", item " + items.getItem().getItemMeta().getDisplayName() + " do inventára!");
        }
    }


}
