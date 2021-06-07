package sk.westland.core.rewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PermReward implements IReward<PermReward> {

    private final String permission;
    private final String permName;
    private PermRenderType permRenderType = PermRenderType.SHOW;
    private boolean permissionState = true;

    public PermReward(String permission, String permName) {
        this.permission = permission;
        this.permName = permName;
    }

    public PermReward(String permission, String permName, PermRenderType permRenderType) {
        this.permission = permission;
        this.permName = permName;
        this.permRenderType = permRenderType;
    }

    public PermReward(String permission, String permName, boolean permissionState) {
        this.permission = permission;
        this.permName = permName;
        this.permissionState = permissionState;
    }

    public PermReward(String permission, String permName, PermRenderType permRenderType, boolean permissionState) {
        this.permission = permission;
        this.permName = permName;
        this.permRenderType = permRenderType;
        this.permissionState = permissionState;
    }

    @Override
    public PermReward reward(Player player) {
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
