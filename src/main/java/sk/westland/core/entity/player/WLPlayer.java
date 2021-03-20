package sk.westland.core.entity.player;


import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.PlayerData;
import sk.westland.core.database.player.PlayerOptions;
import sk.westland.core.database.player.UserData;
import sk.westland.core.enums.JobList;
import sk.westland.world.items.Materials;

import java.util.*;

public class WLPlayer  {

    private final Player player;
    private UserData userData;
    private PlayerData playerData;
    private PlayerOptions playerOptions;

    public WLPlayer(Player player, UserData userData, PlayerData playerData, PlayerOptions playerOptions) {
        this.player = player;
        this.userData = userData;
        this.playerData = playerData;
        this.playerOptions = playerOptions;
    }

    public Player getPlayer() {
        return player;
    }

    // METHOD OVERRIDING

    public void addItemToInventory(ItemStack... itemStack) {
        player.getInventory().addItem(itemStack);
    }

    private UUID getUUID() {
        return player.getUniqueId();
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public String getName() {
        return player.getName();
    }

    public boolean hasPermission(String permission) { return player.hasPermission(permission); }

    public Inventory getInventory() { return player.getInventory(); }

    public Location getLocation() { return player.getLocation(); }

    /////

    public UserData getUserData() {
        return userData;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public PlayerOptions getPlayerOptions() {
        return playerOptions;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public void setPlayerOptions(PlayerOptions playerOptions) {
        this.playerOptions = playerOptions;
    }

    ///////////////

    // USER DATA

    public double getShards() {
        return playerData.getShards();
    }

    public void setShards(double shards) {
        playerData.setShards(shards);
    }

    public double getGems() {
        return playerData.getGems();
    }

    public void setGems(double gems) {
        playerData.setGems(gems);
    }

    public void giveShards(double shards) { setShards(getShards() + shards); }

    public void giveGems(double gems) { setGems(getGems() + gems); }

    public int getActiveJoinMessage() {
        return playerData.getActiveJoinMessage();
    }

    public void setActiveJoinMessage(int activeJoinMessage) {
        playerData.setActiveJoinMessage(activeJoinMessage);
    }

    public int getActiveQuitMessage() {
        return playerData.getActiveQuitMessage();
    }

    public void setActiveQuitMessage(int activeQuitMessage) {
        playerData.setActiveQuitMessage(activeQuitMessage);
    }

    public List<String> getCraftingRecipes() {
        return playerData.getCraftingRecipe();
    }

    public int getHighestLevelClaimed(JobList job) {
        Map<String, List<Integer>> rewarded = playerData.getAlreadyJobRewarded();

        if(!rewarded.containsKey(job.getName()))
            return -1;

        int highest = 0;
        for (Integer integer : rewarded.get(job.getName())) {
            if(integer > highest)
                highest = integer;
        }
        return highest;
    }

    public boolean isJobRewardClaimed(JobList job, int level) {
        Map<String, List<Integer>> rewarded = playerData.getAlreadyJobRewarded();

        if(rewarded.containsKey(job.getName()))
            return rewarded.get(job.getName()).contains(level);

        return false;
    }

    public void jobRewardClaim(JobList job, int levelClaimed, int... levelClaimeds) {
        Map<String, List<Integer>> rewarded = playerData.getAlreadyJobRewarded();
        if(rewarded == null)
            rewarded = new HashMap<>();

        List<Integer> integerList = null;
        if(rewarded.containsKey(job.getName())) {
            integerList = rewarded.get(job.getName());
        }

        if(integerList == null)
            integerList = new ArrayList<>();

        if(levelClaimeds.length > 0)
            for (int pos = 0; pos < levelClaimed; pos++) {
                if(integerList.contains(levelClaimeds[pos])) continue;
                integerList.add(levelClaimeds[pos]);
            }

        if(integerList.contains(levelClaimed))
            return;
        integerList.add(levelClaimed);
        rewarded.put(job.getName(), integerList);

        playerData.setAlreadyJobRewarded(rewarded);
    }

    //////////////

    public void uncraftingRecipe(String items) {
        this.uncraftingRecipe(Objects.requireNonNull(Materials.Items.findItemName(items)));
    }

    public void uncraftingRecipe(Materials.Items items) {
        this.getKnownCraftingRecipes().remove(items.getCraftable().getNamespacedKey(WestLand.getInstance()).getKey().toLowerCase());
        this.getPlayer().undiscoverRecipe(items.getCraftable().getNamespacedKey(WestLand.getInstance()));
    }

    public void discoverRecipe(String items) {
        this.discoverRecipe(Objects.requireNonNull(Materials.Items.findItemName(items)));
    }

    public void discoverRecipe(Materials.Items items) {
        this.getKnownCraftingRecipes().add(items.getCraftable().getNamespacedKey(WestLand.getInstance()).getKey().toLowerCase());
        this.getPlayer().discoverRecipe(items.getCraftable().getNamespacedKey(WestLand.getInstance()));
    }

    public boolean hasDiscovered(String items) {
        return this.hasDiscovered(Objects.requireNonNull(Materials.Items.findItemName(items)));
    }

    public boolean hasDiscovered(Materials.Items items) {
        if(!items.isCraftable())
            throw new IllegalArgumentException("Item " + items.getItem().getItemMeta().getDisplayName() + " cannot be as a discovered recipe!");

        return this.getKnownCraftingRecipes().contains(items.getCraftable().getNamespacedKey(WestLand.getInstance()).getKey());
    }

    public List<String> getKnownCraftingRecipes() {
        if(this.playerData.getCraftingRecipe() == null)
            this.playerData.setCraftingRecipe(new ArrayList<>());

        return this.playerData.getCraftingRecipe();
    }


    public void playXPSound() {
        this.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    }

    public void playSound(Sound sound) {
        player.playSound(player.getLocation(), sound, 2f, 2f);
    }
}
