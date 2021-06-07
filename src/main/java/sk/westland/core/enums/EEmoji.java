package sk.westland.core.enums;

public enum EEmoji {

    GEMS(":gems:", "⻳", true, false),
    SHARD(":shards:", "⺞", true, false),
    MONEY(":money:", "⺚", true, false),
    SMILE(":smile:", "你", false, false),
    COOL(":cool:", "年", false, false),
    CLOWN(":clown:", "生", false, false),
    LAUGH(":laugh:", "可", false, false),
    MINDFUCK(":mindfuck:", "得", false, false),
    PLEASE(":please:", "下", false, false),
    POOP(":poop:", "就", false, false),
    SAD(":sad:", "于", false, false),
    SKULL(":skull:", "要", false, false),
    PEPE_CRINGE(":pepecringe:", "而", false, false),
    PEPE_DAB(":pepedab:", "出", false, false),
    PEPE_FINE(":pepefine:", "道", false, false),
    WESTLAND(":wl:", "时", false, false),
    LOVE(":love:", "子", false, false),
    ;

    private final String text;
    private final String replacement;
    private final boolean admin;
    private final boolean premium;

    EEmoji(String text, String replacement, boolean admin, boolean premium) {
        this.text = text;
        this.replacement = replacement;
        this.admin = admin;
        this.premium = premium;
    }

    public String getText() {
        return text;
    }

    public String getReplacement() {
        return replacement;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isPremium() {
        return premium;
    }
}
