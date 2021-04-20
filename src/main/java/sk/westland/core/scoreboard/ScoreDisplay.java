package sk.westland.core.scoreboard;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreDisplay {
    public static final int maxLines = 14;

    private Player player;
    private Scoreboard board;
    private Objective objective;
    private ArrayList<Integer> linePositions = new ArrayList<>();
    private ArrayList<Line> lineUnits = new ArrayList<>();
    private boolean cleared = true;
    private String title = "EasyScoreboard";

    public class Line {
        private final String key;
        private final Team team;
        private final int scoreValue;
        private boolean removed = false;

        public Line(String keyString, int sValue) {
            key = keyString;
            team = board.registerNewTeam(key);
            team.addEntry(key);
            scoreValue = sValue;
        }

        public void setLine(String text) {
            if (text.isEmpty())
                text = " ";

            String first = text.substring(0, Math.min(text.length(), 32));
            String color = org.bukkit.ChatColor.getLastColors(first);
            team.setPrefix(first);
            if(text.length() > 31)
                team.setSuffix(color + text.substring(32));

            displayLine();
        }

        public void remove() {
            if (!removed) {
                board.resetScores(key);
                removed = true;
            }
        }

        public void cleanup() {
            remove();
            team.unregister();
        }

        private void displayLine() {
            removed = false;
            objective.getScore(key).setScore(scoreValue);
        }
    }

    private void setupLines() {
        int i = 0;
        linePositions = new ArrayList<>();
        lineUnits = new ArrayList<>();
        for(ChatColor chatColor : ChatColor.values()) {
            if (chatColor != ChatColor.RESET) {

                lineUnits.add(new Line("" + chatColor, maxLines-i));
                if (++i > maxLines) {
                    break;
                }
            }
        }
    }

    public ScoreDisplay(Player p, String newTitle) {
        player = p;
        if (newTitle.isEmpty()) {
            title = " ";
        } else {
            title = newTitle;
        }
        setupBoard();
        setupLines();
    }

    private void setupBoard() {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective("westland", "dummy", Component.empty().toBuilder().asComponent());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(board);
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
        board.getObjective(DisplaySlot.SIDEBAR).setDisplayName(title);
    }

    public void clearScoreBoard() {
        if (!cleared) {
            for (Line line : lineUnits) {
                line.cleanup();
            }
            board = Bukkit.getScoreboardManager().getNewScoreboard();
            player.setScoreboard(board);
            cleared = true;
        }
    }

    private boolean checkPosition(int newPosition) {
        if (newPosition > maxLines || newPosition < 0) {
            throw new IndexOutOfBoundsException("Line numbers range is 0-" + maxLines + ". " + newPosition + " was given instead.");
        }
        return true;
    }

    private void updateLists() {
        Collections.sort(linePositions);
        if (!linePositions.isEmpty()) {
            int lastLineIndex = linePositions.get(linePositions.size()-1);
            for (int line = 0; line < lastLineIndex; line++) {
                if (!linePositions.contains(line)) {
                    lineUnits.get(line).setLine(" ");
                }
            }
            for (int line = lastLineIndex+1; line < maxLines; line++) {
                lineUnits.get(line).remove();
            }
        }
    }

    public void removeEntry(int position) {
        if (checkPosition(position)) {
            if (linePositions.contains(position)) {
                linePositions.remove(linePositions.indexOf(position));
                lineUnits.get(position).remove();
            }
            updateLists();
        };
    }

    public void setLine(String text, int position) {
        if (checkPosition(position)) {
            if (cleared) {
                setupBoard();
                setupLines();
                cleared = false;
            }
            if (!linePositions.contains(position)) {
                linePositions.add(position);
            }
            lineUnits.get(position).setLine(PlaceholderAPI.setPlaceholders(player, text));
            updateLists();
        };
    }
}