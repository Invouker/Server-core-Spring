package sk.westland.core.enums;

import sk.westland.core.database.player.PlayerOptions;
import sk.westland.core.entity.player.WLPlayer;

public enum EPlayerOptions {

    SHOW_JOIN_MESSAGE,
    SHOW_QUIT_MESSAGE,
    CHAT_REACTION_SOUND,
    SHOW_DEATH_MESSAGE,
    SHOW_SCOREBOARD,
    TELEPORT_EFFECT
    ;

    public boolean getPlayerOptions(WLPlayer wlPlayer) {
        PlayerOptions playerOptions = wlPlayer.getPlayerOptions();
        switch(this) {
            case SHOW_JOIN_MESSAGE:
                return playerOptions.isShowJoinMessage();
            case SHOW_QUIT_MESSAGE:
                return playerOptions.isShowQuitMessage();
            case CHAT_REACTION_SOUND:
                return playerOptions.isChatReactionSound();
            case SHOW_DEATH_MESSAGE:
                return playerOptions.isShowDeathMessage();
            case SHOW_SCOREBOARD:
                return playerOptions.isShowScoreboard();
            case TELEPORT_EFFECT:
                return playerOptions.isTeleportEffect();
        }
        return false;
    }

    public void setPlayerOptions(WLPlayer wlPlayer, boolean option) {
        PlayerOptions playerOptions = wlPlayer.getPlayerOptions();
        switch(this) {
            case SHOW_JOIN_MESSAGE: {
                playerOptions.setShowJoinMessage(option);
                break;
            }
            case SHOW_QUIT_MESSAGE: {
                playerOptions.setShowQuitMessage(option);
                break;
            }
            case CHAT_REACTION_SOUND:{
                playerOptions.setChatReactionSound(option);
                break;
            }
            case SHOW_DEATH_MESSAGE: {
                playerOptions.setShowDeathMessage(option);
                break;
            }
            case SHOW_SCOREBOARD: {
                playerOptions.setShowScoreboard(option);
                break;
            }
            case TELEPORT_EFFECT: {
                playerOptions.setTeleportEffect(option);
                break;
            }
        }

    }
}
