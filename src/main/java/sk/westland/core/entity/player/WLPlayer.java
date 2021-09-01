package sk.westland.core.entity.player;


import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.database.player.PlayerData;
import sk.westland.core.database.player.PlayerOptions;
import sk.westland.core.database.player.UserData;
import sk.westland.core.enums.EBadge;
import sk.westland.core.enums.EPlayerTimeReward;
import sk.westland.core.enums.JobList;
import sk.westland.world.items.Materials;

import java.util.*;

public class WLPlayer {

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

    public void addItemToInventory(ItemStack... itemStacks) {
        for(ItemStack itemStack : itemStacks) {
            if(itemStack == null)
                continue;
            player.getInventory().addItem(itemStack);
        }
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

    public boolean teleport(Location location) { return player.teleport(location); }

    public void setGameMode(GameMode gamemode) { player.setGameMode(gamemode); }

    public void sendTitle(String title, String subTitle) {
        player.sendTitle(title, subTitle, 25, 50, 20);
    }

    /////

    public long getPlayerId() {
        return userData.getId();
    }

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

    public void addShards(double shards) {
        playerData.setShards(playerData.getShards() + shards);
    }

    public double getGems() {
        return playerData.getGems();
    }

    public void setGems(double gems) {
        playerData.setGems(gems);
    }

    public void addGems(double gems) {
        playerData.setGems(playerData.getGems() + gems);
    }

    public void giveShards(double shards) { addShards(shards); }

    public void giveGems(double gems) { addGems(gems); }

    public void addBadge(EBadge... eBadges) {
        for (EBadge badge : eBadges) {
            playerData.getClaimedBadges().add(badge);
        }
    }

    public void removeBadge(EBadge... eBadges) {
        for (EBadge badge : eBadges) {
            playerData.getClaimedBadges().remove(badge);
        }
    }

    public void setActiveBadge(EBadge eBadge) {
        playerData.setActiveBadge(eBadge);
    }

    public EBadge getActiveBadge() {
        return playerData.getActiveBadge();
    }

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

    public long getRewardClaimedTime(EPlayerTimeReward timeReward) {
        switch (timeReward) {
            case Daily: return playerData.getDailyRewardClaimed();
            case Weekly: return playerData.getWeeklyRewardClaimed();
            case Monthly: return playerData.getMonthlyRewardClaimed();
            case PremiumDaily: return playerData.getDailyPremiumRewardClaimed();
            case PremiumWeekly: return playerData.getWeeklyPremiumRewardClaimed();
        }
        return 0L;
    }

    public void setRewardClaimedTime(EPlayerTimeReward timeReward, long time) {
        switch (timeReward) {
            case Daily: {
                playerData.setDailyRewardClaimed(time);
                break;
            }
            case Weekly: {
                playerData.setWeeklyRewardClaimed(time);
                break;
            }
            case Monthly: {
                playerData.setMonthlyRewardClaimed(time);
                break;
            }
            case PremiumDaily: {
                playerData.setDailyPremiumRewardClaimed(time);
                break;
            }
            case PremiumWeekly: {
                playerData.setWeeklyPremiumRewardClaimed(time);
                break;
            }
        }
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
        this.getKnownCraftingRecipes().remove(items.getCraftable().getCraftingRecipe().getNamespacedKey().getKey().toLowerCase());
        this.getPlayer().undiscoverRecipe(items.getCraftable().getCraftingRecipe().getNamespacedKey());
    }

    public void discoverRecipe(String items) {
        this.discoverRecipe(Objects.requireNonNull(Materials.Items.findItemName(items)));
    }

    public void discoverRecipe(Materials.Items items) {
        this.getKnownCraftingRecipes().add(items.getCraftable().getCraftingRecipe().getNamespacedKey().getKey().toLowerCase());
        this.getPlayer().discoverRecipe(items.getCraftable().getCraftingRecipe().getNamespacedKey());
    }

    public boolean hasDiscovered(String items) {
        return this.hasDiscovered(Objects.requireNonNull(Materials.Items.findItemName(items)));
    }

    public boolean hasDiscovered(Materials.Items items) {
        if(!items.isCraftable())
            throw new IllegalArgumentException("Item " + items.getItem().getItemMeta().getDisplayName() + " cannot be as a discovered recipe!");

        return this.getKnownCraftingRecipes().contains(items.getCraftable().getCraftingRecipe().getNamespacedKey().getKey());
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
