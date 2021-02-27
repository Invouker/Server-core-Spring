package sk.westland.core.jobs.rewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JPermReward implements JIReward {

    private String permission;
    private String permName;
    private PermRenderType permRenderType;

    public JPermReward(String permission, String permName) {
        this.permission = permission;
        this.permName = permName;
    }

    public JPermReward(String permission, String permName, PermRenderType permRenderType) {
        this.permission = permission;
        this.permName = permName;
        this.permRenderType = permRenderType;
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
        switch (permRenderType) {
            case HIDE:
                return permName;

            default:
            case SHOW:
                return "Limitovaný príkaz &7(/" + permName + ")";
        }
    }

    public enum PermRenderType {
        SHOW,
        HIDE
    }
}
