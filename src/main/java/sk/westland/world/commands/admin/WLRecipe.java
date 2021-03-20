package sk.westland.world.commands.admin;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.world.commands.Converter.PlayerArgConverter;
import sk.westland.world.commands.suggestion.CraftingSuggestion;
import sk.westland.world.items.Materials;

@Component
@CommandLine.Command(name = "wlrecipe", aliases = {"vyskum"})
@HasPermission(value = {"admin.commands.crafting"}, message = "Unknown command")
public class WLRecipe {

    @Component
    @CommandLine.Command(name = "list")
    @HasPermission(value = {"admin.commands.crafting.list"}, message = "Unknown command")
    public static class ListCommand implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            if(targetPlayerArg == context.getPlayer()) {
                Player player = context.getPlayer();
                if(player == null) {
                    ChatInfo.ERROR.send(context.getSender(), "Príkaz iba pre  hráčov");
                    return;
                }
                targetPlayer = playerService.getWLPlayer(player);
            } else if(targetPlayerArg == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            } else
                targetPlayer = playerService.getWLPlayer(targetPlayerArg);

            if(targetPlayer == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();

            int size = targetPlayer.getKnownCraftingRecipes().size();
            for(int i = 0; i < size; i++) {
                stringBuilder.append(targetPlayer.getKnownCraftingRecipes().get(i));

                if(size-1 != i)
                    stringBuilder.append(",");
            }

            ChatInfo.GENERAL_INFO.send(context.getSender(), "Hráč pozná tieto recepty: " + stringBuilder.toString());

        }
    }

    @Component
    @CommandLine.Command(name = "add")
    @HasPermission(value = {"admin.commands.crafting.add"}, message = "Unknown command")
    public static class AddCommand implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "1", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

        @CommandLine.Parameters(index = "0", completionCandidates = CraftingSuggestion.class)
        private String crafting;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            if(targetPlayerArg == context.getPlayer()) {
                Player player = context.getPlayer();
                if(player == null) {
                    ChatInfo.ERROR.send(context.getSender(), "Príkaz iba pre  hráčov");
                    return;
                }
                targetPlayer = playerService.getWLPlayer(player);
            } else if(targetPlayerArg == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            } else
                targetPlayer = playerService.getWLPlayer(targetPlayerArg);

            if(targetPlayer == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            }

            Materials.Items craftingByName  = Materials.Items.findItemName(crafting);

            if(craftingByName == null) {
                ChatInfo.ERROR.send(context.getSender(), "Neplatný crafting");
                return;
            }

            if(targetPlayer.hasDiscovered(craftingByName)) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč už má tento recept vyskúmaný!");
                return;
            }

            if(!craftingByName.isCraftable()) {
                ChatInfo.ERROR.send(context.getSender(), "Zlý názov itemu!");
                return;
            }

            targetPlayer.discoverRecipe(craftingByName);
            ChatInfo.SUCCESS.send(context.getSender(), "Úspešne si pridal hráčovy §l" + crafting);

        }
    }

    @Component
    @CommandLine.Command(name = "remove")
    @HasPermission(value = {"admin.commands.crafting.remove"}, message = "Unknown command")
    public static class RemoveCommand implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "1", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

        @CommandLine.Parameters(index = "0", completionCandidates = CraftingSuggestion.class)
        private String crafting;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            if(targetPlayerArg == context.getPlayer()) {
                Player player = context.getPlayer();
                if(player == null) {
                    ChatInfo.ERROR.send(context.getSender(), "Príkaz iba pre  hráčov");
                    return;
                }
                targetPlayer = playerService.getWLPlayer(player);
            } else if(targetPlayerArg == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            } else
                targetPlayer = playerService.getWLPlayer(targetPlayerArg);

            if(targetPlayer == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            }

            Materials.Items craftingByName  = Materials.Items.findItemName(crafting);

            if(craftingByName == null) {
                ChatInfo.ERROR.send(context.getSender(), "Neplatný crafting");
                return;
            }

            if(!targetPlayer.hasDiscovered(craftingByName)) {
                ChatInfo.ERROR.send(context.getSender(), "Daný hráč nemá vyskúmaný recept!");
                return;
            }

            if(!craftingByName.isCraftable()) {
                ChatInfo.ERROR.send(context.getSender(), "Zlý názov itemu!");
                return;
            }

            targetPlayer.uncraftingRecipe(craftingByName);
            ChatInfo.SUCCESS.send(context.getSender(), "Úspešne si odobral hráčovy §l" + crafting);

        }
    }

    @Component
    @CommandLine.Command(name = "removeall")
    @HasPermission(value = {"admin.commands.crafting.removeall"}, message = "Unknown command")
    public static class RemoveAllCommand implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            if(targetPlayerArg == context.getPlayer()) {
                Player player = context.getPlayer();
                if(player == null) {
                    ChatInfo.ERROR.send(context.getSender(), "Príkaz iba pre  hráčov");
                    return;
                }
                targetPlayer = playerService.getWLPlayer(player);
            } else if(targetPlayerArg == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            } else
                targetPlayer = playerService.getWLPlayer(targetPlayerArg);

            if(targetPlayer == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            }

            for(Materials.Items crafting : Materials.Items.values()) {
                if(crafting.isCraftable())
                if(targetPlayer.hasDiscovered(crafting))
                    targetPlayer.uncraftingRecipe(crafting);
            }

            ChatInfo.SUCCESS.send(context.getSender(), "Úspešne si odobral hráčovy všetký recepty!");

        }
    }

    @Component
    @CommandLine.Command(name = "addall")
    @HasPermission(value = {"admin.commands.crafting.addall"}, message = "Unknown command")
    public static class AddAllCommand implements Runnable {

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

        @Autowired
        private PlayerService playerService;

        @Override
        public void run() {
            WLPlayer targetPlayer;
            if(targetPlayerArg == context.getPlayer()) {
                Player player = context.getPlayer();
                if(player == null) {
                    ChatInfo.ERROR.send(context.getSender(), "Príkaz iba pre  hráčov");
                    return;
                }
                targetPlayer = playerService.getWLPlayer(player);
            } else if(targetPlayerArg == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            } else
                targetPlayer = playerService.getWLPlayer(targetPlayerArg);

            if(targetPlayer == null) {
                ChatInfo.ERROR.send(context.getSender(), "Hráč nebyl nalezen!");
                return;
            }

            for(Materials.Items crafting : Materials.Items.values()) {
                if(crafting.isCraftable())
                if(!targetPlayer.hasDiscovered(crafting))
                    targetPlayer.discoverRecipe(crafting);
            }

            ChatInfo.SUCCESS.send(context.getSender(), "Úspešne si pridal hráčovy všetký recepty!");

        }
    }
}
