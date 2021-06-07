package sk.westland.core.enums;
//sk.westland.core.enums.EPlayerOptions
import sk.westland.core.database.player.PlayerOptions;
import sk.westland.core.entity.player.WLPlayer;

public enum EPlayerOptions {

    SHOW_JOIN_MESSAGE,
    SHOW_QUIT_MESSAGE,
    CHAT_REACTION_SOUND,
    SHOW_DEATH_MESSAGE,
    SHOW_SCOREBOARD,
    TELEPORT_EFFECT,
    SHOW_AUTOMESSAGE,
    SHOW_EVENT_ANNOUNCE
    ;

    public boolean getPlayerOptions(WLPlayer wlPlayer) {
        if(wlPlayer == null)
            return false;

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
            case SHOW_AUTOMESSAGE:
                return playerOptions.isShowAutoMessage();
            case SHOW_EVENT_ANNOUNCE:
                return playerOptions.isShowEventAnnounce();
        }
        return false;
    }

    public void setPlayerOptions(WLPlayer wlPlayer, boolean option) {
        if(wlPlayer == null)
            return;

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
            case SHOW_AUTOMESSAGE: {
                playerOptions.setShowAutoMessage(option);
                break;
            }
            case SHOW_EVENT_ANNOUNCE: {
                playerOptions.setShowEventAnnounce(option);
                break;
            }
        }

    }
}
