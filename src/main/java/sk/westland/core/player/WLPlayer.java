package sk.westland.core.player;


import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.UserData;
import sk.westland.core.database.player.UserOption;
import sk.westland.core.services.PlayerDataStorageService;
import sk.westland.world.items.Materials;

import java.util.*;

public class WLPlayer  {

    private Player player;
    private PlayerDataStorageService.Data userData;

    public WLPlayer(Player player, PlayerDataStorageService.Data userData) {
        this.player = player;
        this.userData = userData;
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

    public UserData getUserData() {
        return userData.getUserData();
    }

    public void setUserData(UserData userData) {
        this.userData.setUserData(userData);
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public String getName() {
        return player.getName();
    }

    public boolean hasPermission(String permission) { return player.hasPermission(permission); }

    public Inventory getInventory() { return player.getInventory(); }

    /////

    public UserOption getUserOption() {
        return userData.getUserOption();
    }

    ///////////////

    // USER DATA

    public double getCoin() {
        return getUserData().getCoin();
    }

    public void setCoin(double coins) {
        getUserData().setCoin(coins);
    }

    public int getActiveJoinMessage() {
        return getUserData().getActiveJoinMessage();
    }

    public void setActiveJoinMessage(int activeJoinMessage) {
        getUserData().setActiveJoinMessage(activeJoinMessage);
    }

    public int getActiveQuitMessage() {
        return getUserData().getActiveQuitMessage();
    }

    public void setActiveQuitMessage(int activeQuitMessage) {
        getUserData().setActiveQuitMessage(activeQuitMessage);
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
        if(this.getUserData().getCraftingRecipe() == null)
            this.getUserData().setCraftingRecipe(new ArrayList<>());

        return this.getUserData().getCraftingRecipe();
    }


    public void playXPSound() {
        this.playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    }

    public void playSound(Sound sound) {
        player.playSound(player.getLocation(), sound, 2f, 2f);
    }
}
