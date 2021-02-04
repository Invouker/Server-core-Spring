package sk.westland.world.commands;

import dev.alangomes.springspigot.context.Context;
import dev.alangomes.springspigot.security.HasPermission;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.quest.ESetQuestStateResult;
import sk.westland.core.quest.QuestLogMenu;
import sk.westland.core.quest.QuestState;
import sk.westland.core.services.PlayerService;
import sk.westland.core.services.QuestService;
import sk.westland.world.commands.Converter.PlayerArgConverter;
import sk.westland.world.commands.suggestion.QuestIdSuggestion;

@Component
@CommandLine.Command(name = "qqqqqq")
@HasPermission("quest")
public class QuestCommands implements Runnable {

    @Autowired
    private Context context;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private QuestService questService;

    @Override
    public void run() {
        QuestLogMenu questLogMenu = new QuestLogMenu(playerService, questService);
        questLogMenu.open(context.getPlayer());
    }

    @Component
    @CommandLine.Command(name = "accept")
    @HasPermission(value = {"quest.accept", "quest"}, message = "Unknown command")
    public static class QuestAcceptCommand implements Runnable {

        @Autowired
        private PlayerService playerService;

        @Autowired
        private QuestService questService;

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", completionCandidates = QuestIdSuggestion.class)
        private String quest_id;

        @CommandLine.Parameters(index = "1", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

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


            if(!questService.getQuestIds().contains(quest_id)) {
                ChatInfo.ERROR.send(context.getPlayer(), "Quest nebyl nalezen !");
                return;
            }

            if(!questService.getQuestIds().contains(quest_id)) {
                ChatInfo.ERROR.send(context.getPlayer(), "Quest nebyl nalezen !");
                return;
            }

            if(questService.acceptQuest(quest_id, targetPlayer)) {
                ChatInfo.SUCCESS.send(context.getPlayer(), "Quest Přijat.");
            } else {
                ChatInfo.WARNING.send(context.getPlayer(), "Nelze přijat quest, pravděpodobně ho hráč již má.");
            }
        }
    }

    @Component
    @CommandLine.Command(name = "complete")
    @HasPermission(value = {"quest.complete", "quest"}, message = "Unknown command")
    public static class QuestCompleteCommand implements Runnable {

        @Autowired
        private PlayerService playerService;

        @Autowired
        private QuestService questService;

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", completionCandidates = QuestIdSuggestion.class)
        private String quest_id;

        @CommandLine.Parameters(index = "1", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

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

            WLPlayer wlPlayer = targetPlayer;

            if(!questService.getQuestIds().contains(quest_id)) {
                ChatInfo.ERROR.send(context.getPlayer(), "Quest nebyl nalezen !");
                return;
            }

            questService.acceptQuest(quest_id, targetPlayer);

            ESetQuestStateResult result = this.questService.setQuestState(targetPlayer, quest_id, QuestState.Completed);

            if(result == ESetQuestStateResult.Changed) {
                ChatInfo.SUCCESS.send(context.getPlayer(), result.getMessage());
            } else {
                ChatInfo.WARNING.send(context.getPlayer(), result.getMessage());
            }
        }
    }

    @Component
    @CommandLine.Command(name = "remove")
    @HasPermission(value = {"quest.remove", "quest"}, message = "Unknown command")
    public static class QuestRemoveCommand implements Runnable {

        @Autowired
        private PlayerService playerService;

        @Autowired
        private QuestService questService;

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", completionCandidates = QuestIdSuggestion.class)
        private String quest_id;

        @CommandLine.Parameters(index = "1", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

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

            WLPlayer wlPlayer = targetPlayer;

            if(!questService.getQuestIds().contains(quest_id)) {
                ChatInfo.ERROR.send(context.getPlayer(), "Quest nebyl nalezen !");
                return;
            }

            if(questService.removePlayerQuest(quest_id, targetPlayer)) {
                ChatInfo.SUCCESS.send(context.getPlayer(), "Successfully remove quest!");
            } else {
                ChatInfo.ERROR.send(context.getPlayer(), "Cannot remove quest, probably this player doest not have this quest !");
            }
        }
    }

    @Component
    @CommandLine.Command(name = "removeall")
    @HasPermission(value = {"quest.removeall", "quest"}, message = "Unknown command")
    public static class QuestRemoveAllCommand implements Runnable {

        @Autowired
        private PlayerService playerService;

        @Autowired
        private QuestService questService;

        @Autowired
        private Context context;

        @CommandLine.Parameters(index = "0", defaultValue = "@s", converter = PlayerArgConverter.class)
        private Player targetPlayerArg;

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

            int removedQuestCount = 0;

            for(String quest_id : questService.getQuestIds()) {
                if(questService.removePlayerQuest(quest_id, targetPlayer)) {
                    removedQuestCount++;
                }
            }

            if(removedQuestCount > 0) {
                ChatInfo.SUCCESS.send(context.getPlayer(), "Successfully remove " + removedQuestCount + " quests.");
            } else {
                ChatInfo.WARNING.send(context.getPlayer(), "Player does not have any quest !");
            }
        }
    }

}
