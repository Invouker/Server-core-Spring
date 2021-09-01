package sk.westland.core.enums;

public enum EBadge {

    Kral("Král", "買", ""),
    Kralovna("Královna", "KA", ""),
    Majster("Majster", "MA", ""),
    Slimefun_Majster("Slimefun-Majster", "東", "Po prekonaní všetých slimefun receptov"),
    Veteran("Veterán", "V", "Najdlhšie hrajúci hráč na našom servery!"),
    Priroda("Náturista", "風", "Hráč má cit s prírodou"),
    Pickaxe("Kopáč", "無", "Najväčší kopáč"),
    Sword("Zabiják", "S", "Najväčší bitkár s monštráma a hráčmi"),
    Farmar("Farmár", "紅", "Farmár po presadení sa na servery!"),
    Creeper("Creeper", "電", "Creeper, SSSsss-hhh B00M"),
    Zombie("Zombie", "時", "Zombie, ZhmmMMmMmmM"),
    Wither("Wither", "愛", "Withererová tajná zbraň"),
    Star("Star", "⺼", "Padajúca hviezda, niečo  si želaj! Hviezda ktorá  nikdy nevyžiari!"),
    Discord_legend("Discord Legenda", "車", "Discordová legenda!"),
    Bot("", "", "Hráč hrá ako by to bol naprogramovaný BOT!"),
    // Pridávať podľa eventov - //TODO THERE
    Event_majster("Event Majster", "E", "Hráč prekonal všetký eventy na servery!"),
    E_Falling_Block("Falling Blocks", "F", "Hráč sa zúčastnil eventu Falling Blocks"),

    Villiger("Villiger", "", "Neúprosný villiger"),
    Trader("Trader", "", "Najväčší obchodník!"),
    Easter("Easter", "", "Hráč sa ukázal na veľkonočnom evente!"),
    Halloween("Halloween", "", "Hráč sa ukázal na halloween evente!"),
    Christmas("Christmas", "", "Hráč sa ukázal na vianočnom evente!"),
    Redstone_Master("Redstone Master", "", "Špecialista na redstone, ak niečo neviete, JE TU pre vás!"),
    Batman("Batman", "", "Batman, nikto ho nikde nevidel!"),
    Captain_America("Captain America", "", "Kapitán Amerika"),
    Flash("Flash", "", "Flash? Kde je?"),
    Aquaman("Aquaman", "", "Voda, všade je voda!");

    private final String name;
    private final String character;
    private final String description;

    EBadge(String name, String character, String description) {
        this.name = name;
        this.character = character;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public String getDescription() {
        return description;
    }
}
