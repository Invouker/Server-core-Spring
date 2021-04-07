package sk.westland.discord.ranksync;

import java.util.ArrayList;
import java.util.List;

public class Rank {

    public enum RankEnum {
        VIP, ADMIN, PLAYER;
    }

    private static List<Rank> rankList = new ArrayList<>();

    static {
        addRank("leader", "796422876910714890", RankEnum.ADMIN);
        addRank("admin", "796428089247662181", RankEnum.ADMIN);
        addRank("dev", "804327837385097216", RankEnum.ADMIN);
        addRank("mod", "796428096282427483", RankEnum.ADMIN);
        addRank("builder", "809740387182444574", RankEnum.ADMIN);
        addRank("support", "796428097040941097", RankEnum.ADMIN);
        addRank("eventer", "804331327105138688", RankEnum.ADMIN);
        addRank("twitch", "804330290755010590", RankEnum.VIP);
        addRank("youtube", "804330750723227658", RankEnum.VIP);
        addRank("legion", "798224771611230228", RankEnum.VIP);
        addRank("hero", "798224981464842250", RankEnum.VIP);
        addRank("elite", "798225299741081600", RankEnum.VIP);
        addRank("default", "796428231196409857", RankEnum.PLAYER); // 796428231196409857
        addRank("stafflist", "796423506178998274", RankEnum.ADMIN);
    }

    private static void addRank(String vaultGroup, String discordGroup, RankEnum rankEnum) {
        rankList.add(new Rank(vaultGroup, discordGroup, rankEnum));
    }

    public static Rank getDiscordGroupByVault(String vaultGroup) {
        for (Rank rank : rankList) {
            if(rank.getVaultGroup().equals(vaultGroup))
                return rank;
        }

        throw new NullPointerException("Group named " + vaultGroup + " doesnt exist.");
    }

    private String vaultGroup;
    private String discordGroup;
    private RankEnum rankData;

    public Rank(String vaultGroup, String discordGroup, RankEnum rankData) {
        this.vaultGroup = vaultGroup;
        this.discordGroup = discordGroup;
        this.rankData = rankData;
    }

    public static List<Rank> getRankList() {
        return rankList;
    }

    public static void setRankList(List<Rank> rankList) {
        Rank.rankList = rankList;
    }

    public String getVaultGroup() {
        return vaultGroup;
    }

    public void setVaultGroup(String vaultGroup) {
        this.vaultGroup = vaultGroup;
    }

    public String getDiscordGroup() {
        return discordGroup;
    }

    public void setDiscordGroup(String discordGroup) {
        this.discordGroup = discordGroup;
    }

    public RankEnum getRankData() {
        return rankData;
    }

    public void setRankData(RankEnum rankData) {
        this.rankData = rankData;
    }
}
