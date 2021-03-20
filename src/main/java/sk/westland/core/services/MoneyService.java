package sk.westland.core.services;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.MoneyType;

@Service
public class MoneyService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private VaultService vaultService;

    public boolean remove(Player player, MoneyType moneyType, double amount) {
        return this.remove(playerService.getWLPlayer(player), moneyType, amount);
    }

    public boolean give(Player player, MoneyType moneyType, double amount) {
        return this.give(playerService.getWLPlayer(player), moneyType, amount);
    }

    public boolean set(Player player, MoneyType moneyType, double amount) {
        return this.set(playerService.getWLPlayer(player), moneyType, amount);
    }

    public boolean pay(Player player, MoneyType moneyType, double amount) {
        return this.pay(playerService.getWLPlayer(player), moneyType, amount);
    }

    public double get(Player player, MoneyType moneyType) {
        return this.get(playerService.getWLPlayer(player), moneyType);
    }

    public boolean hasPay(Player player, MoneyType moneyType, double amount) {
        return this.hasPay(playerService.getWLPlayer(player), moneyType, amount);
    }


    public boolean hasPay(WLPlayer wlPlayer, MoneyType moneyType, double amount) {
        switch (moneyType) {
            case Gems:
                return wlPlayer.getGems() >= amount;
            case Shard:
                return wlPlayer.getShards() >= amount;
            case Money:
                return vaultService.getEconomy().has(getOfflinePlayer(wlPlayer), amount);
        }
        return false;
    }

    public double get(WLPlayer wlPlayer, MoneyType moneyType) {
        switch (moneyType) {
            case Gems:
                return wlPlayer.getGems();
            case Shard:
                return wlPlayer.getShards();
            case Money:
                return vaultService.getEconomy().getBalance(getOfflinePlayer(wlPlayer));
        }
        return 0d;
    }

    public boolean pay(WLPlayer wlPlayer, MoneyType moneyType, double amount) {
        if(this.hasPay(wlPlayer, moneyType, amount)) {
            switch (moneyType) {
                case Gems: {
                    wlPlayer.setGems(wlPlayer.getGems() - amount);
                    return true;
                }
                case Shard: {
                    wlPlayer.setShards(wlPlayer.getShards() - amount);
                    return true;
                }
                case Money: {
                    vaultService.getEconomy().depositPlayer(getOfflinePlayer(wlPlayer), amount);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean set(WLPlayer wlPlayer, MoneyType moneyType, double amount) {
        if(this.hasPay(wlPlayer, moneyType, amount)) {
            switch (moneyType) {
                case Gems: {
                    wlPlayer.setGems(amount);
                    return true;
                }
                case Shard: {
                    wlPlayer.setShards(amount);
                    return true;
                }
                case Money: {
                    double money = vaultService.getEconomy().getBalance(getOfflinePlayer(wlPlayer));
                    EconomyResponse economyResponse = vaultService.getEconomy().withdrawPlayer(getOfflinePlayer(wlPlayer), money);
                    if(!economyResponse.transactionSuccess())
                        throw new NullPointerException("Economy response, player cannot withdraw money!");

                    return vaultService.getEconomy().depositPlayer(getOfflinePlayer(wlPlayer), amount).transactionSuccess();
                }
            }
        }
        return false;
    }

    public boolean give(WLPlayer wlPlayer, MoneyType moneyType, double amount) {
        switch (moneyType) {
            case Gems: {
                wlPlayer.giveGems(amount);
                return true;
            }
            case Shard: {
                wlPlayer.giveShards(amount);
                return true;
            }
            case Money: {
                return vaultService.getEconomy().depositPlayer(getOfflinePlayer(wlPlayer), amount).transactionSuccess();

            }
        }
        return false;
    }

    public boolean remove(WLPlayer wlPlayer, MoneyType moneyType, double amount) {
        if(this.hasPay(wlPlayer, moneyType, amount)) {
            switch (moneyType) {
                case Gems: {
                    wlPlayer.giveGems(-amount);
                    return true;
                }
                case Shard: {
                    wlPlayer.giveShards(-amount);
                    return true;
                }
                case Money: {
                    return vaultService.getEconomy().withdrawPlayer(getOfflinePlayer(wlPlayer), amount).transactionSuccess();
                }
            }
            return true;
        }
        return false;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    private OfflinePlayer getOfflinePlayer(WLPlayer wlPlayer) {
        return Bukkit.getOfflinePlayer(wlPlayer.getPlayer().getUniqueId());
    }

    public VaultService getVaultService() {
        return vaultService;
    }

    private OfflinePlayer getOfflinePlayer(Player player) {
        return Bukkit.getOfflinePlayer(player.getUniqueId());
    }
}
