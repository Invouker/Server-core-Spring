package sk.westland.core.services;

import org.springframework.stereotype.Service;
import sk.westland.core.MoneyType;
import sk.westland.core.player.WLPlayer;

@Service
public class MoneyService {

    public boolean hasPay(WLPlayer wlPlayer, MoneyType moneyType, double amount) {
        switch (moneyType) {
            case Gems:
                return wlPlayer.getGems() >= amount;
            case Shard:
                return wlPlayer.getShards() >= amount;
        }
        return false;
    }

    public double get(WLPlayer wlPlayer, MoneyType moneyType) {
        switch (moneyType) {
            case Gems:
                return wlPlayer.getGems();
            case Shard:
                return wlPlayer.getShards();
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
            }
        }
        return false;
    }

    public void set(WLPlayer wlPlayer, MoneyType moneyType, double amount) {
        if(this.hasPay(wlPlayer, moneyType, amount)) {
            switch (moneyType) {
                case Gems: {
                    wlPlayer.setGems(amount);
                    break;
                }
                case Shard: {
                    wlPlayer.setShards(amount);
                    break;
                }
            }
        }
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
            }
            return true;
        }
        return false;
    }

}
