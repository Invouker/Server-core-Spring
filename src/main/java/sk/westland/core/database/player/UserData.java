package sk.westland.core.database.player;

import org.bukkit.entity.Player;
import sk.westland.core.quest.storage.QuestProgressStorage;
import sk.westland.core.utils.converter.ListConverter;
import sk.westland.core.utils.converter.QuestProgressStorageConverter;
import sk.westland.core.utils.converter.StringConverter;

import javax.persistence.*;
import java.util.*;

@Table(name = "wl_player_data")
@Entity
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nickname")
    private String name;

    private String uuid;

    private double gems = 0;
    private double shards = 0;

    private int level = 1;
    private int exp = 0;

    @Column(columnDefinition = "text", nullable = false)
    @Convert(converter = StringConverter.class)
    private List<String> craftingRecipe;

    @Column(columnDefinition = "text", nullable = false)
    @Convert(converter = ListConverter.class)
    private Map<String, List<Integer>> alreadyJobRewarded;

    private int activeJoinMessage = -1;
    private int activeQuitMessage = -1;

    //@Convert(converter = QuestProgressStorageConverter.class)
    //private LinkedList<QuestProgressStorage> progressStorageMap;

   // @Convert(converter = QuestProgressStorageConverter.class)
    //private LinkedList<QuestProgressStorage> activeQuestProgressMap;

    public UserData() { }

    public UserData(String name, String uuid, double gems, double shards, int level, int exp, int activeJoinMessage, int activeQuitMessage) {
        this.name = name;
        this.uuid = uuid;
        this.gems = gems;
        this.shards = shards;
        this.level = level;
        this.exp = exp;
        this.activeJoinMessage = activeJoinMessage;
        this.activeQuitMessage = activeQuitMessage;
    }

    public long getId() {
        return id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}
