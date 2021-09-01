package sk.westland.core.enums;

import org.bukkit.Material;

public enum JobList {
    LOVEC("Lovec", "Hunter", 100, Material.CROSSBOW),
    STAVITEL("Staviteľ", "Builder",  100, Material.BRICK),
    RYBAR("Rybár", "Fisherman",100, Material.FISHING_ROD),
    CRAFTER("Crafter", "Crafter",100, Material.CRAFTING_TABLE),
    KOVAR("Kovár", "Weaponsmith",100, Material.ANVIL),

    FARMAR("Farmár", "Farmer",100, Material.NETHERITE_HOE),
    KOPAC("Kopáč", "Digger",100, Material.IRON_SHOVEL),
    DREVORUBAC("Drevorúbač", "Woodcutter",100, Material.NETHERITE_AXE),
    ALCHEMISTA("Alchemista", "",100, Material.BREWING_STAND),
    HORNIK("Horník", "Miner",100, Material.NETHERITE_PICKAXE),

    ENCHANTER("Enchanter", "Enchanter",100, Material.BOOK)
    ;

    private String name;
    private String originalName;
    private int maxLevel;
    private Material icon;

    JobList(String name, String originalName, int maxLevel, Material icon) {
        this.name = name;
        this.originalName = originalName;
        this.maxLevel = maxLevel;
        this.icon = icon;
    }

    public String getOriginalName() {
        return originalName;
    }

    public Material getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public static JobList findByName(String name) {
        for (JobList job : values()) {
            System.out.println("Jobs: " + job.getName());
            if(job.getName().equalsIgnoreCase(name))
                return job;
        }
        return null;
    }
}
