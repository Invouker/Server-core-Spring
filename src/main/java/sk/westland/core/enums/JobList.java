package sk.westland.core.enums;

import org.bukkit.Material;

public enum JobList {
    LOVEC("Lovec", 100, Material.CROSSBOW),
    STAVITEL("Staviteľ", 100, Material.BRICK),
    RYBAR("Rybár", 100, Material.FISHING_ROD),
    CRAFTER("Crafter", 100, Material.CRAFTING_TABLE),
    KOVAR("Kovár", 100, Material.ANVIL),

    FARMAR("Farmár", 100, Material.NETHERITE_HOE),
    KOPAC("Kopáč", 100, Material.IRON_SHOVEL),
    DREVORUBAC("Drevorúbač", 100, Material.NETHERITE_AXE),
    ALCHEMISTA("Alchemista", 100, Material.BREWING_STAND),
    HORNIK("Horník", 100, Material.NETHERITE_PICKAXE),

    ENCHANTER("Enchanter", 100, Material.BOOK)
    ;

    private String name;
    private int maxLevel;
    private Material icon;

    JobList(String name, int maxLevel, Material icon) {
        this.name = name;
        this.maxLevel = maxLevel;
        this.icon = icon;
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
