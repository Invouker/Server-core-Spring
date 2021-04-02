package sk.westland.core.discord.ranksync;

public class Rank {

    private String vaultGroup;
    private String discordGroup;

    public Rank(String vaultGroup, String discordGroup) {
        this.vaultGroup = vaultGroup;
        this.discordGroup = discordGroup;
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
}
