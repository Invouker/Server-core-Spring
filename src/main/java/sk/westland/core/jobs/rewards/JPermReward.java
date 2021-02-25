package sk.westland.core.jobs.rewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JPermReward implements JIReward {

    private String permission;
    private String permName;

    public JPermReward(String permission, String permName) {
        this.permission = permission;
        this.permName = permName;
    }

    @Override
    public JIReward reward(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "lp user " + player.getName() + " permission set " + permission + " true"
        );

        return this;
    }

    @Override
    public String render() {
        return "&5Limitovaný príkaz &7(/" + permName + ")";
    }
}
