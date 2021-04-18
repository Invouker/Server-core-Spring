package sk.westland.core.database.player;

import sk.westland.core.utils.converter.ListConverter;
import sk.westland.core.utils.converter.StringConverter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table(name = "wl_player_data")
@Entity
public class PlayerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long playerId;

    private double gems = 0;
    private double shards = 0;

    private int level = 1;
    private int exp = 0;

    private long dailyRewardClaimed = 0L;
    private long dailyPremiumRewardClaimed = 0L;
    private long weeklyRewardClaimed = 0L;
    private long weeklyPremiumRewardClaimed = 0L;
    private long monthlyRewardClaimed = 0L;

    @Column(columnDefinition = "text")
    @Convert(converter = StringConverter.class)
    private List<String> craftingRecipe;

    @Column(columnDefinition = "text")
    @Convert(converter = ListConverter.class)
    private Map<String, List<Integer>> alreadyJobRewarded;

    private int activeJoinMessage = -1;
    private int activeQuitMessage = -1;

    //@Convert(converter = QuestProgressStorageConverter.class)
    //private LinkedList<QuestProgressStorage> progressStorageMap;

   // @Convert(converter = QuestProgressStorageConverter.class)
    //private LinkedList<QuestProgressStorage> activeQuestProgressMap;

    public PlayerData() { }

    public PlayerData(long playerId) {
        this.playerId = playerId;
    }

    public int getActiveJoinMessage() {
        return activeJoinMessage;
    }

    public void setActiveJoinMessage(int activeJoinMessage) {
        this.activeJoinMessage = activeJoinMessage;
    }

    public int getActiveQuitMessage() {
        return activeQuitMessage;
    }

    public void setActiveQuitMessage(int activeQuitMessage) {
        this.activeQuitMessage = activeQuitMessage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getGems() {
        return gems;
    }

    public void setGems(double gems) {
        this.gems = gems;
    }

    public double getShards() {
        return shards;
    }

    public void setShards(double shards) {
        this.shards = shards;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public List<String> getCraftingRecipe() {
        return craftingRecipe;
    }

    public void setCraftingRecipe(List<String> craftingRecipe) {
        this.craftingRecipe = craftingRecipe;
    }

    public Map<String, List<Integer>> getAlreadyJobRewarded() {
        return alreadyJobRewarded == null ? new HashMap<>() : alreadyJobRewarded;
    }

    public void setAlreadyJobRewarded(Map<String, List<Integer>> alreadyJobRewarded) {
        this.alreadyJobRewarded = alreadyJobRewarded;
    }

    public long getDailyRewardClaimed() {
        return dailyRewardClaimed;
    }

    public void setDailyRewardClaimed(long dailyRewardClaimed) {
        this.dailyRewardClaimed = dailyRewardClaimed;
    }

    public long getWeeklyRewardClaimed() {
        return weeklyRewardClaimed;
    }

    public void setWeeklyRewardClaimed(long weeklyRewardClaimed) {
        this.weeklyRewardClaimed = weeklyRewardClaimed;
    }

    public long getMonthlyRewardClaimed() {
        return monthlyRewardClaimed;
    }

    public void setMonthlyRewardClaimed(long monthlyRewardClaimed) {
        this.monthlyRewardClaimed = monthlyRewardClaimed;
    }

    public long getDailyPremiumRewardClaimed() {
        return dailyPremiumRewardClaimed;
    }

    public void setDailyPremiumRewardClaimed(long dailyPremiumRewardClaimed) {
        this.dailyPremiumRewardClaimed = dailyPremiumRewardClaimed;
    }

    public long getWeeklyPremiumRewardClaimed() {
        return weeklyPremiumRewardClaimed;
    }

    public void setWeeklyPremiumRewardClaimed(long weeklyPremiumRewardClaimed) {
        this.weeklyPremiumRewardClaimed = weeklyPremiumRewardClaimed;
    }
}
