package sk.westland.world.commands.admin;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.utils.ChatInfo;
import sk.westland.world.commands.suggestion.ItemResourceSuggestion;
import sk.westland.world.commands.suggestion.ItemSuggestion;
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

        @CommandLine.Parameters(index = "0", completionCandidates = ItemResourceSuggestion.class)
        private String resourceSuggestion;

        @Override
        public void run() {
            Materials.Resources items = Materials.Resources.findItemName(resourceSuggestion);
            if(items == null) {
                ChatInfo.ERROR.send(context.getSender(), "Item sa nenašiel!");
                return;
            }

            if(!(context.getSender() instanceof Player))
                return;

            context.getPlayer().getInventory().addItem(items.getItem());
            ChatInfo.SUCCESS.send(context.getSender(), "Dostal si " + items.getItem().getItemMeta().getDisplayName() + " do inventára!");
        }
    }

    @Component
    @CommandLine.Command(name = "items")
    @HasPermission("commands.customitem.items")
    public static class Items implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", completionCandidates = ItemSuggestion.class)
        private String resourceSuggestion;

        @Override
        public void run() {
            Materials.Items items = Materials.Items.findItemName(resourceSuggestion);
            if(items == null) {
                ChatInfo.ERROR.send(context.getSender(), "Item sa nenašiel");
                return;
            }

            if(!(context.getSender() instanceof Player))
                return;

            context.getPlayer().getInventory().addItem(items.getItem());
            ChatInfo.SUCCESS.send(context.getSender(), "Dostal si " + items.getItem().getItemMeta().getDisplayName() + " do inventára!");
        }
    }


}
