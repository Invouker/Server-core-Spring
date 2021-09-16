package sk.westland.core.jobs;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import sk.westland.core.enums.JobList;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.rewards.*;
import sk.westland.core.services.MoneyService;
import sk.westland.world.items.Materials;

import java.util.Arrays;
import java.util.List;

public class JobRewards {

    private MoneyService moneyService;

    private final JobStorage jobStorage = new JobStorage();

    public JobStorage getJobStorage() {
        return jobStorage;
    }

    public JobRewards(MoneyService moneyService) {
        this.moneyService = moneyService;
       /* List<JIReward> rewardList = jobStorage.getJobRewards("Drevorúbač", 3);
        for (JIReward jiReward : rewardList) {
            jiReward.reward(Bukkit.getPlayer("XpresS"));
        }

        */
        addDrevorubac();
        addLovec();
        addStavitel();
        addRybar();
        addCrafter();
        addKovar();
        addFarmar();
        addKopac();
        addAlchemista();
        addHornik();
        addEnchanter();


    }

    private void addDrevorubac() {
        addJob(JobList.DREVORUBAC,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(new ItemBuilder(Material.STONE_AXE).addEnchant(Enchantment.DIG_SPEED, 1)),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ItemReward(Material.OAK_SAPLING, 3),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                               new ItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ItemReward(Material.ACACIA_SAPLING, 3),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new ItemReward(Material.OAK_LOG, 32),
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new ItemReward(Material.OAK_LOG, 64),
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new ItemReward(new ItemBuilder(Material.STONE_AXE).addEnchant(Enchantment.DIG_SPEED, 3)),
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                                new ItemReward(Material.JUNGLE_SAPLING, 3),
                                new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ItemReward(Material.DARK_OAK_SAPLING, 3),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                                ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService),
                                new ItemReward(Material.BIRCH_SAPLING, 64),
                                new ItemReward(Material.DARK_OAK_SAPLING, 64)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 4)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new ItemReward(Material.DIAMOND, 12),
                                new MoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105),
                                new ItemReward(new ItemBuilder(Material.DIAMOND_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 2)
                                        .addEnchant(Enchantment.DURABILITY, 1))
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService),
                                new ItemReward(Material.ACACIA_SAPLING, 32),
                                new ItemReward(Material.BIRCH_SAPLING, 32)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService),
                                new SoundReward(Sound.BLOCK_FIRE_EXTINGUISH)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(new ItemBuilder(Material.DIAMOND_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 7)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService),
                                new ItemReward(Material.DARK_OAK_SAPLING, 32),
                                new ItemReward(Material.JUNGLE_SAPLING, 32)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.CHUNK_COLLECTOR)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 4)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(Material.DIAMOND, 64),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addLovec() {
        addJob(JobList.LOVEC,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1)),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 320, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new MoneyReward(MoneyType.Money, 335, moneyService)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new ItemReward(Material.OAK_LOG, 32),
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new ItemReward(new ItemBuilder(Material.STONE_AXE).addEnchant(Enchantment.DIG_SPEED, 3)),
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                                new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_SWORD)
                                        .addEnchant(Enchantment.DAMAGE_ALL, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService),
                                new ItemReward(Material.BOW)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 4)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new MoneyReward(MoneyType.Gems, 25, moneyService),
                                new ItemReward(Material.DIAMOND, 12)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_BOOTS)
                                        .addEnchant(Enchantment.THORNS, 5)
                                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                                        .addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5)
                                        .addEnchant(Enchantment.PROTECTION_FALL, 5)
                                        .addEnchant(Enchantment.PROTECTION_FIRE, 5)
                                        .addEnchant(Enchantment.PROTECTION_PROJECTILE, 5)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95),
                                new ItemReward(new ItemBuilder(Material.DIAMOND_SWORD)
                                        .addEnchant(Enchantment.DAMAGE_ALL, 4)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService),
                                new ItemReward(Material.NETHER_STAR, 1)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105),
                                new ItemReward(new ItemBuilder(Material.DIAMOND_SWORD)
                                        .addEnchant(Enchantment.DAMAGE_ALL, 5)
                                        .addEnchant(Enchantment.DURABILITY, 1))
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_HELMET)
                                        .addEnchant(Enchantment.THORNS, 5)
                                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                                        .addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5)
                                        .addEnchant(Enchantment.PROTECTION_FALL, 5)
                                        .addEnchant(Enchantment.PROTECTION_FIRE, 5)
                                        .addEnchant(Enchantment.PROTECTION_PROJECTILE, 5)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService),
                                new ItemReward(Material.ARROW, 64),
                                new ItemReward(Material.SPECTRAL_ARROW, 64)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService),
                                new ItemReward(Material.NETHER_STAR, 3)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService),
                                new SoundReward(Sound.BLOCK_FIRE_AMBIENT)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(new ItemBuilder(Material.CROSSBOW)
                                        .addEnchant(Enchantment.DAMAGE_ALL, 4)
                                        .addEnchant(Enchantment.MULTISHOT, 1)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_LEGGINGS)
                                        .addEnchant(Enchantment.THORNS, 5)
                                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                                        .addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5)
                                        .addEnchant(Enchantment.PROTECTION_FALL, 5)
                                        .addEnchant(Enchantment.PROTECTION_FIRE, 5)
                                        .addEnchant(Enchantment.PROTECTION_PROJECTILE, 5)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService),
                                new ItemReward(Material.DARK_OAK_SAPLING, 32),
                                new ItemReward(Material.JUNGLE_SAPLING, 32)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.CHUNK_COLLECTOR)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_SWORD)
                                        .addEnchant(Enchantment.DAMAGE_ALL, 7)
                                        .addEnchant(Enchantment.DAMAGE_ARTHROPODS, 6)
                                        .addEnchant(Enchantment.LOOT_BONUS_MOBS, 3)
                                        .addEnchant(Enchantment.FIRE_ASPECT, 2)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_CHESTPLATE)
                                        .addEnchant(Enchantment.THORNS, 5)
                                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                                        .addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5)
                                        .addEnchant(Enchantment.PROTECTION_FALL, 5)
                                        .addEnchant(Enchantment.PROTECTION_FIRE, 5)
                                        .addEnchant(Enchantment.PROTECTION_PROJECTILE, 5)
                                        .setUnbreakable()),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addStavitel() {
        addJob(JobList.STAVITEL,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(Material.STONE, 128),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ItemReward(Material.STONE_BRICKS, 128),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new ItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                               new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new ItemReward(Material.DIAMOND, 48),
                                new MoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(Material.DIAMOND, 64),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addRybar() {
        addJob(JobList.RYBAR,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(Material.STONE, 128),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ItemReward(Material.STONE_BRICKS, 128),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8 new ItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                               new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new ItemReward(Material.DIAMOND, 48),
                                new MoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(Material.DIAMOND, 64),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addCrafter() {
        addJob(JobList.CRAFTER,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(Material.STONE, 128),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ItemReward(Material.STONE_BRICKS, 128),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new ItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                               new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new ItemReward(Material.DIAMOND, 48),
                                new MoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(Material.DIAMOND, 64),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addKovar() {
        addJob(JobList.KOVAR,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(Material.STONE, 128),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ItemReward(Material.STONE_BRICKS, 128),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new ItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                               new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new ItemReward(Material.DIAMOND, 48),
                                new MoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(Material.DIAMOND, 64),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addFarmar() {
        addJob(JobList.FARMAR,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(Material.STONE, 128),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ItemReward(Material.STONE_BRICKS, 128),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new ItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                                new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new ItemReward(Material.DIAMOND, 48),
                                new MoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(Material.DIAMOND, 64),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addKopac() {
        addJob(JobList.KOPAC,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(Material.STONE, 128),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ItemReward(Material.STONE_BRICKS, 128),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new ItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                                new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new ItemReward(Material.DIAMOND, 48),
                                new MoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(Material.DIAMOND, 64),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addAlchemista() {
        addJob(JobList.ALCHEMISTA,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(Material.STONE, 128),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ItemReward(Material.STONE_BRICKS, 128),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                               new ItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                                new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new ItemReward(Material.DIAMOND, 48),
                                new MoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(Material.DIAMOND, 64),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addHornik() {
        addJob(JobList.HORNIK,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(Material.STONE, 128),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ItemReward(Material.STONE_BRICKS, 128),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new ItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                                new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new ItemReward(Material.DIAMOND, 48),
                                new MoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(Material.DIAMOND, 64),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addEnchanter() {
        addJob(JobList.ENCHANTER,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new MoneyReward(MoneyType.Money, 170, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new MoneyReward(MoneyType.Money, 180, moneyService),
                                new ItemReward(Material.STONE, 128),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new MoneyReward(MoneyType.Money, 200, moneyService),
                                new ItemReward(Material.STONE_BRICKS, 128),
                                new ExpReward(20),
                                new SoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new MoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new MoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                               new ItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new MoneyReward(340, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new MoneyReward(580, moneyService),
                                new MoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new MoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new MoneyReward(MoneyType.Money, 450, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new MoneyReward(MoneyType.Money, 460, moneyService),
                                new ExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new MoneyReward(MoneyType.Money, 482, moneyService),
                                new ExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new MoneyReward(MoneyType.Money, 500, moneyService),
                                new ExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new MoneyReward(MoneyType.Money, 510, moneyService),
                                new ExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new MoneyReward(MoneyType.Money, 530, moneyService),
                                new ExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new MoneyReward(MoneyType.Money, 550, moneyService),
                                new ExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new MoneyReward(MoneyType.Money, 580, moneyService),
                                new ExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new MoneyReward(MoneyType.Money, 700, moneyService),
                                new MoneyReward(MoneyType.Gems, 5, moneyService),
                                new ExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new MoneyReward(MoneyType.Money, 650, moneyService),
                                new ExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new MoneyReward(MoneyType.Money, 690, moneyService),
                                new ExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new MoneyReward(MoneyType.Money, 710, moneyService),
                                new ExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new MoneyReward(MoneyType.Money, 745, moneyService),
                                new ExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new MoneyReward(MoneyType.Money, 758, moneyService),
                                new ExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new MoneyReward(MoneyType.Money, 765, moneyService),
                                new ExpReward(72),
                                new ItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new MoneyReward(MoneyType.Money, 780, moneyService),
                                new ExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new MoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new MoneyReward(MoneyType.Money, 800, moneyService),
                                new ItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new MoneyReward(MoneyType.Money, 814, moneyService),
                                new ItemReward(Material.DIAMOND, 48),
                                new MoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new MoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new MoneyReward(MoneyType.Money, 835, moneyService),
                                new PermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new MoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new MoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new MoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new MoneyReward(MoneyType.Money, 870, moneyService),
                                new ExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new MoneyReward(MoneyType.Money, 880, moneyService),
                                new ExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new MoneyReward(MoneyType.Money, 900, moneyService),
                                new ExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new MoneyReward(MoneyType.Money, 985, moneyService),
                                new ExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new MoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new MoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new MoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new MoneyReward(MoneyType.Money, 1230, moneyService),
                                new ExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new MoneyReward(MoneyType.Money, 1290, moneyService),
                                new ExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new MoneyReward(MoneyType.Money, 1310, moneyService),
                                new ExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new MoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new MoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new MoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new MoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new MoneyReward(MoneyType.Money, 5000, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new MoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new MoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new MoneyReward(MoneyType.Money, 5205, moneyService),
                                new PermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new MoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new MoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new MoneyReward(MoneyType.Money, 5320, moneyService),
                                new ExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new MoneyReward(MoneyType.Money, 5490, moneyService),
                                new ExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new MoneyReward(MoneyType.Money, 5630, moneyService),
                                new ExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new MoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new MoneyReward(MoneyType.Money, 6023, moneyService),
                                new MoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new MoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new MoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new MoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new MoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new MoneyReward(MoneyType.Money, 6457, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new MoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new MoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new MoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new MoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new MoneyReward(MoneyType.Money, 7200, moneyService),
                                new MoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new MoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new MoneyReward(MoneyType.Money, 7430, moneyService),
                                new ExpReward(130),
                                new ItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new MoneyReward(MoneyType.Money, 7550, moneyService),
                                new ExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new MoneyReward(MoneyType.Money, 7669, moneyService),
                                new ExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new MoneyReward(MoneyType.Money, 7777, moneyService),
                                new MoneyReward(MoneyType.Gems, 40, moneyService),
                                new ExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new MoneyReward(MoneyType.Money, 7832, moneyService),
                                new ExpReward(160),
                                new ItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new MoneyReward(MoneyType.Money, 7950, moneyService),
                                new ExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new MoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new MoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new MoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new MoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new MoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new MoneyReward(MoneyType.Money, 8620, moneyService),
                                new MoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new MoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new MoneyReward(MoneyType.Money, 8855, moneyService),
                                new ItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new MoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new MoneyReward(MoneyType.Money, 9055, moneyService),
                                new ItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new MoneyReward(MoneyType.Money, 9504, moneyService),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new MoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new MoneyReward(MoneyType.Money, 11026, moneyService),
                                new ExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new MoneyReward(MoneyType.Money, 12024, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new MoneyReward(MoneyType.Money, 14480, moneyService),
                                new ExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new MoneyReward(MoneyType.Money, 16873, moneyService),
                                new ExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new MoneyReward(MoneyType.Money, 18224, moneyService),
                                new ExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new MoneyReward(MoneyType.Money, 20770, moneyService),
                                new ExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new MoneyReward(MoneyType.Money, 22795, moneyService),
                                new ExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new MoneyReward(MoneyType.Money, 24498, moneyService),
                                new ExpReward(265),
                                new ItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new MoneyReward(MoneyType.Money, 26557, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new MoneyReward(MoneyType.Money, 28334, moneyService),
                                new ExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new MoneyReward(MoneyType.Money, 54321, moneyService),
                                new ExpReward(300),
                                new ItemReward(Material.DIAMOND, 64),
                                new MoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addJob(JobList job, List<List<IReward>> rewards) {
        jobStorage.put(job, rewards);
    }
}
