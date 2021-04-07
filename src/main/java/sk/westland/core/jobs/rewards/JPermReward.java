package sk.westland.core.jobs.rewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JPermReward implements JIReward {

    private final String permission;
    private final String permName;
    private PermRenderType permRenderType = PermRenderType.SHOW;
    private boolean permissionState = true;

    public JPermReward(String permission, String permName) {
        this.permission = permission;
        this.permName = permName;
    }

    public JPermReward(String permission, String permName, PermRenderType permRenderType) {
        this.permission = permission;
        this.permName = permName;
        this.permRenderType = permRenderType;
    }

    public JPermReward(String permission, String permName, boolean permissionState) {
        this.permission = permission;
        this.permName = permName;
        this.permissionState = permissionState;
    }

    public JPermReward(String permission, String permName, PermRenderType permRenderType, boolean permissionState) {
        this.permission = permission;
        this.permName = permName;
        this.permRenderType = permRenderType;
        this.permissionState = permissionState;
    }

    @Override
    public JIReward reward(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "lp user " + player.getName() + " permission set " + permission + " " + permissionState
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
                return "Limitovaný príkaz §7(/" + permName + ")";
        }
    }

    public enum PermRenderType {
        SHOW,
        HIDE
    }
}
