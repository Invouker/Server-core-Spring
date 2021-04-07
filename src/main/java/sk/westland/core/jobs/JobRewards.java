package sk.westland.core.jobs;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import sk.westland.core.enums.JobList;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.jobs.rewards.*;
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(new ItemBuilder(Material.STONE_AXE).addEnchant(Enchantment.DIG_SPEED, 1)),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JItemReward(Material.OAK_SAPLING, 3),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JItemReward(Material.ACACIA_SAPLING, 3),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JItemReward(Material.OAK_LOG, 32),
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JItemReward(Material.OAK_LOG, 64),
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JItemReward(new ItemBuilder(Material.STONE_AXE).addEnchant(Enchantment.DIG_SPEED, 3)),
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                                new JItemReward(Material.JUNGLE_SAPLING, 3),
                                new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JItemReward(Material.DARK_OAK_SAPLING, 3),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                                ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService),
                                new JItemReward(Material.BIRCH_SAPLING, 64),
                                new JItemReward(Material.DARK_OAK_SAPLING, 64)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 4)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JItemReward(Material.DIAMOND, 12),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105),
                                new JItemReward(new ItemBuilder(Material.DIAMOND_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 2)
                                        .addEnchant(Enchantment.DURABILITY, 1))
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService),
                                new JItemReward(Material.ACACIA_SAPLING, 32),
                                new JItemReward(Material.BIRCH_SAPLING, 32)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService),
                                new JSoundReward(Sound.BLOCK_FIRE_EXTINGUISH)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(new ItemBuilder(Material.DIAMOND_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 4)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService),
                                new JItemReward(Material.DARK_OAK_SAPLING, 32),
                                new JItemReward(Material.JUNGLE_SAPLING, 32)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.CHUNK_COLLECTOR)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(Material.DIAMOND, 64),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1)),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 320, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JMoneyReward(MoneyType.Money, 335, moneyService),
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JItemReward(Material.OAK_LOG, 32),
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JItemReward(new ItemBuilder(Material.STONE_AXE).addEnchant(Enchantment.DIG_SPEED, 3)),
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                                new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_SWORD)
                                        .addEnchant(Enchantment.DAMAGE_ALL, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService),
                                new JItemReward(Material.BOW)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 4)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService),
                                new JItemReward(Material.DIAMOND, 12)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_BOOTS)
                                        .addEnchant(Enchantment.THORNS, 5)
                                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                                        .addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5)
                                        .addEnchant(Enchantment.PROTECTION_FALL, 5)
                                        .addEnchant(Enchantment.PROTECTION_FIRE, 5)
                                        .addEnchant(Enchantment.PROTECTION_PROJECTILE, 5)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95),
                                new JItemReward(new ItemBuilder(Material.DIAMOND_SWORD)
                                        .addEnchant(Enchantment.DAMAGE_ALL, 4)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService),
                                new JItemReward(Material.NETHER_STAR, 1)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105),
                                new JItemReward(new ItemBuilder(Material.DIAMOND_SWORD)
                                        .addEnchant(Enchantment.DAMAGE_ALL, 5)
                                        .addEnchant(Enchantment.DURABILITY, 1))
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_HELMET)
                                        .addEnchant(Enchantment.THORNS, 5)
                                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                                        .addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5)
                                        .addEnchant(Enchantment.PROTECTION_FALL, 5)
                                        .addEnchant(Enchantment.PROTECTION_FIRE, 5)
                                        .addEnchant(Enchantment.PROTECTION_PROJECTILE, 5)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService),
                                new JItemReward(Material.ARROW, 64),
                                new JItemReward(Material.SPECTRAL_ARROW, 64)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService),
                                new JItemReward(Material.NETHER_STAR, 3)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService),
                                new JSoundReward(Sound.BLOCK_FIRE_AMBIENT)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(new ItemBuilder(Material.CROSSBOW)
                                        .addEnchant(Enchantment.DAMAGE_ALL, 4)
                                        .addEnchant(Enchantment.MULTISHOT, 1)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_LEGGINGS)
                                        .addEnchant(Enchantment.THORNS, 5)
                                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                                        .addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5)
                                        .addEnchant(Enchantment.PROTECTION_FALL, 5)
                                        .addEnchant(Enchantment.PROTECTION_FIRE, 5)
                                        .addEnchant(Enchantment.PROTECTION_PROJECTILE, 5)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService),
                                new JItemReward(Material.DARK_OAK_SAPLING, 32),
                                new JItemReward(Material.JUNGLE_SAPLING, 32)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.CHUNK_COLLECTOR)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_SWORD)
                                        .addEnchant(Enchantment.DAMAGE_ALL, 7)
                                        .addEnchant(Enchantment.DAMAGE_ARTHROPODS, 6)
                                        .addEnchant(Enchantment.LOOT_BONUS_MOBS, 3)
                                        .addEnchant(Enchantment.FIRE_ASPECT, 2)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_CHESTPLATE)
                                        .addEnchant(Enchantment.THORNS, 5)
                                        .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                                        .addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 5)
                                        .addEnchant(Enchantment.PROTECTION_FALL, 5)
                                        .addEnchant(Enchantment.PROTECTION_FIRE, 5)
                                        .addEnchant(Enchantment.PROTECTION_PROJECTILE, 5)
                                        .setUnbreakable()),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(Material.STONE, 128),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JItemReward(Material.STONE_BRICKS, 128),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                               new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JItemReward(Material.DIAMOND, 48),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(Material.DIAMOND, 64),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(Material.STONE, 128),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JItemReward(Material.STONE_BRICKS, 128),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                               new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JItemReward(Material.DIAMOND, 48),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(Material.DIAMOND, 64),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(Material.STONE, 128),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JItemReward(Material.STONE_BRICKS, 128),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                               new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JItemReward(Material.DIAMOND, 48),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(Material.DIAMOND, 64),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(Material.STONE, 128),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JItemReward(Material.STONE_BRICKS, 128),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                               new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JItemReward(Material.DIAMOND, 48),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(Material.DIAMOND, 64),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(Material.STONE, 128),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JItemReward(Material.STONE_BRICKS, 128),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                                new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JItemReward(Material.DIAMOND, 48),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(Material.DIAMOND, 64),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(Material.STONE, 128),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JItemReward(Material.STONE_BRICKS, 128),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                                new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JItemReward(Material.DIAMOND, 48),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(Material.DIAMOND, 64),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(Material.STONE, 128),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JItemReward(Material.STONE_BRICKS, 128),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                                new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JItemReward(Material.DIAMOND, 48),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(Material.DIAMOND, 64),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(Material.STONE, 128),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JItemReward(Material.STONE_BRICKS, 128),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                                new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JItemReward(Material.DIAMOND, 48),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(Material.DIAMOND, 64),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
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
                                new JMoneyReward(MoneyType.Money, 170, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 180, moneyService),
                                new JItemReward(Material.STONE, 128),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Money, 200, moneyService),
                                new JItemReward(Material.STONE_BRICKS, 128),
                                new JExpReward(20),
                                new JSoundReward(Sound.ENTITY_FIREWORK_ROCKET_LAUNCH)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 260, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 350, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JMoneyReward(340, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 10
                                new JMoneyReward(580, moneyService),
                                new JMoneyReward(MoneyType.Gems,5, moneyService)
                        ),
                        Arrays.asList( // level 11
                                new JMoneyReward(420, moneyService)
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 450, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 460, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JMoneyReward(MoneyType.Money, 482, moneyService),
                                new JExpReward(25)
                        ),
                        Arrays.asList( // level 15
                                new JMoneyReward(MoneyType.Money, 500, moneyService),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 16
                                new JMoneyReward(MoneyType.Money, 510, moneyService),
                                new JExpReward(34)
                        ),
                        Arrays.asList( // level 17
                                new JMoneyReward(MoneyType.Money, 530, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 18
                                new JMoneyReward(MoneyType.Money, 550, moneyService),
                                new JExpReward(44)
                        ),
                        Arrays.asList( // level 19
                                new JMoneyReward(MoneyType.Money, 580, moneyService),
                                new JExpReward(48)
                        ),
                        Arrays.asList( // level 20
                                new JMoneyReward(MoneyType.Money, 700, moneyService),
                                new JMoneyReward(MoneyType.Gems, 5, moneyService),
                                new JExpReward(50)
                        ),
                        Arrays.asList( // level 21
                                new JMoneyReward(MoneyType.Money, 650, moneyService),
                                new JExpReward(52)
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 690, moneyService),
                                new JExpReward(56)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 710, moneyService),
                                new JExpReward(60)
                        ),
                        Arrays.asList( // level 24
                                new JMoneyReward(MoneyType.Money, 745, moneyService),
                                new JExpReward(64)
                        ),
                        Arrays.asList( // level 25
                                new JMoneyReward(MoneyType.Money, 758, moneyService),
                                new JExpReward(68)
                        ),
                        Arrays.asList( // level 26
                                new JMoneyReward(MoneyType.Money, 765, moneyService),
                                new JExpReward(72),
                                new JItemReward(new ItemBuilder(Material.IRON_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 3)
                                        .addEnchant(Enchantment.DURABILITY, 2))
                        ),
                        Arrays.asList( // level 27
                                new JMoneyReward(MoneyType.Money, 780, moneyService),
                                new JExpReward(76)
                        ),
                        Arrays.asList( // level 28
                                new JMoneyReward(MoneyType.Money, 795, moneyService)
                        ),
                        Arrays.asList( // level 29
                                new JMoneyReward(MoneyType.Money, 800, moneyService),
                                new JItemReward(Material.DIAMOND, 32)
                        ),
                        Arrays.asList( // level 30
                                new JMoneyReward(MoneyType.Money, 814, moneyService),
                                new JItemReward(Material.DIAMOND, 48),
                                new JMoneyReward(MoneyType.Gems, 25, moneyService)
                        ),
                        Arrays.asList( // level 31
                                new JMoneyReward(MoneyType.Money, 827, moneyService)
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 835, moneyService),
                                new JPermReward("cmi.command.condense", "condense")
                        ),
                        Arrays.asList( // level 33
                                new JMoneyReward(MoneyType.Money, 844, moneyService)
                        ),
                        Arrays.asList( // level 34
                                new JMoneyReward(MoneyType.Money, 850, moneyService)
                        ),
                        Arrays.asList( // level 35
                                new JMoneyReward(MoneyType.Money, 862, moneyService)
                        ),
                        Arrays.asList( // level 36
                                new JMoneyReward(MoneyType.Money, 870, moneyService),
                                new JExpReward(80)
                        ),
                        Arrays.asList( // level 37
                                new JMoneyReward(MoneyType.Money, 880, moneyService),
                                new JExpReward(84)
                        ),
                        Arrays.asList( // level 38
                                new JMoneyReward(MoneyType.Money, 900, moneyService),
                                new JExpReward(88)
                        ),
                        Arrays.asList( // level 39
                                new JMoneyReward(MoneyType.Money, 985, moneyService),
                                new JExpReward(85)
                        ),
                        Arrays.asList( // level 40
                                new JMoneyReward(MoneyType.Money, 1000, moneyService)
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 1050, moneyService)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 1100, moneyService)
                        ),
                        Arrays.asList( // level 43
                                new JMoneyReward(MoneyType.Money, 1230, moneyService),
                                new JExpReward(90)
                        ),
                        Arrays.asList( // level 44
                                new JMoneyReward(MoneyType.Money, 1290, moneyService),
                                new JExpReward(95)
                        ),
                        Arrays.asList( // level 45
                                new JMoneyReward(MoneyType.Money, 1310, moneyService),
                                new JExpReward(100)
                        ),
                        Arrays.asList( // level 46
                                new JMoneyReward(MoneyType.Money, 1440, moneyService)
                        ),
                        Arrays.asList( // level 47
                                new JMoneyReward(MoneyType.Money, 1680, moneyService)
                        ),
                        Arrays.asList( // level 48
                                new JMoneyReward(MoneyType.Money, 1800, moneyService)
                        ),
                        Arrays.asList( // level 49
                                new JMoneyReward(MoneyType.Money, 2000, moneyService)
                        ),
                        Arrays.asList( // level 50
                                new JMoneyReward(MoneyType.Money, 5000, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 5100, moneyService)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 5110, moneyService)
                        ),
                        Arrays.asList( // level 53
                                new JMoneyReward(MoneyType.Money, 5205, moneyService),
                                new JPermReward("cmi.command.back", "back")
                        ),
                        Arrays.asList( // level 54
                                new JMoneyReward(MoneyType.Money, 5260, moneyService)
                        ),
                        Arrays.asList( // level 55
                                new JMoneyReward(MoneyType.Money, 5300, moneyService)
                        ),
                        Arrays.asList( // level 56
                                new JMoneyReward(MoneyType.Money, 5320, moneyService),
                                new JExpReward(105)
                        ),
                        Arrays.asList( // level 57
                                new JMoneyReward(MoneyType.Money, 5490, moneyService),
                                new JExpReward(110)
                        ),
                        Arrays.asList( // level 58
                                new JMoneyReward(MoneyType.Money, 5630, moneyService),
                                new JExpReward(120)
                        ),
                        Arrays.asList( // level 59
                                new JMoneyReward(MoneyType.Money, 5880, moneyService)
                        ),
                        Arrays.asList( // level 60
                                new JMoneyReward(MoneyType.Money, 6023, moneyService),
                                new JMoneyReward(MoneyType.Gems, 60, moneyService)
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 6175, moneyService)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 6220, moneyService)
                        ),
                        Arrays.asList( // level 63
                                new JMoneyReward(MoneyType.Money, 6293, moneyService)
                        ),
                        Arrays.asList( // level 64
                                new JMoneyReward(MoneyType.Money, 6322, moneyService)
                        ),
                        Arrays.asList( // level 65
                                new JMoneyReward(MoneyType.Money, 6457, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService)
                        ),
                        Arrays.asList( // level 66
                                new JMoneyReward(MoneyType.Money, 6590, moneyService)
                        ),
                        Arrays.asList( // level 67
                                new JMoneyReward(MoneyType.Money, 6800, moneyService)
                        ),
                        Arrays.asList( // level 68
                                new JMoneyReward(MoneyType.Money, 6905, moneyService)
                        ),
                        Arrays.asList( // level 69
                                new JMoneyReward(MoneyType.Money, 6969, moneyService)
                        ),
                        Arrays.asList( // level 70
                                new JMoneyReward(MoneyType.Money, 7200, moneyService),
                                new JMoneyReward(MoneyType.Gems, 70, moneyService)
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 7360, moneyService)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 7430, moneyService),
                                new JExpReward(130),
                                new JItemReward(Materials.Items.BLOCK_PLACER)
                        ),
                        Arrays.asList( // level 73
                                new JMoneyReward(MoneyType.Money, 7550, moneyService),
                                new JExpReward(134)
                        ),
                        Arrays.asList( // level 74
                                new JMoneyReward(MoneyType.Money, 7669, moneyService),
                                new JExpReward(138)
                        ),
                        Arrays.asList( // level 75
                                new JMoneyReward(MoneyType.Money, 7777, moneyService),
                                new JMoneyReward(MoneyType.Gems, 40, moneyService),
                                new JExpReward(150)
                        ),
                        Arrays.asList( // level 76
                                new JMoneyReward(MoneyType.Money, 7832, moneyService),
                                new JExpReward(160),
                                new JItemReward(Materials.Items.MAGIC_SPAWNER)
                        ),
                        Arrays.asList( // level 77
                                new JMoneyReward(MoneyType.Money, 7950, moneyService),
                                new JExpReward(165)
                        ),
                        Arrays.asList( // level 78
                                new JMoneyReward(MoneyType.Money, 8110, moneyService)
                        ),
                        Arrays.asList( // level 79
                                new JMoneyReward(MoneyType.Money, 8220, moneyService)
                        ),
                        Arrays.asList( // level 80
                                new JMoneyReward(MoneyType.Money, 8330, moneyService)
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 8400, moneyService)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 8556, moneyService)
                        ),
                        Arrays.asList( // level 83
                                new JMoneyReward(MoneyType.Money, 8620, moneyService),
                                new JMoneyReward(MoneyType.Shard, 6, moneyService)
                        ),
                        Arrays.asList( // level 84
                                new JMoneyReward(MoneyType.Money, 8740, moneyService)
                        ),
                        Arrays.asList( // level 85
                                new JMoneyReward(MoneyType.Money, 8855, moneyService),
                                new JItemReward(Material.NETHERITE_INGOT, 16)
                        ),
                        Arrays.asList( // level 86
                                new JMoneyReward(MoneyType.Money, 8905, moneyService)
                        ),
                        Arrays.asList( // level 87
                                new JMoneyReward(MoneyType.Money, 9055, moneyService),
                                new JItemReward(Materials.Items.BLOCK_BREAKER)
                        ),
                        Arrays.asList( // level 88
                                new JMoneyReward(MoneyType.Money, 9504, moneyService),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 5)
                                        .addEnchant(Enchantment.DURABILITY, 3))
                        ),
                        Arrays.asList( // level 89
                                new JMoneyReward(MoneyType.Money, 9804, moneyService)
                        ),
                        Arrays.asList( // level 90
                                new JMoneyReward(MoneyType.Money, 11026, moneyService),
                                new JExpReward(200)
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 12024, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 92
                                new JMoneyReward(MoneyType.Money, 14480, moneyService),
                                new JExpReward(225)
                        ),
                        Arrays.asList( // level 93
                                new JMoneyReward(MoneyType.Money, 16873, moneyService),
                                new JExpReward(230)
                        ),
                        Arrays.asList( // level 94
                                new JMoneyReward(MoneyType.Money, 18224, moneyService),
                                new JExpReward(240)
                        ),
                        Arrays.asList( // level 95
                                new JMoneyReward(MoneyType.Money, 20770, moneyService),
                                new JExpReward(250)
                        ),
                        Arrays.asList( // level 96
                                new JMoneyReward(MoneyType.Money, 22795, moneyService),
                                new JExpReward(260)
                        ),
                        Arrays.asList( // level 97
                                new JMoneyReward(MoneyType.Money, 24498, moneyService),
                                new JExpReward(265),
                                new JItemReward(new ItemBuilder(Material.NETHERITE_AXE)
                                        .addEnchant(Enchantment.DIG_SPEED, 6)
                                        .setUnbreakable())
                        ),
                        Arrays.asList( // level 98
                                new JMoneyReward(MoneyType.Money, 26557, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 99
                                new JMoneyReward(MoneyType.Money, 28334, moneyService),
                                new JExpReward(220)
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 54321, moneyService),
                                new JExpReward(300),
                                new JItemReward(Material.DIAMOND, 64),
                                new JMoneyReward(MoneyType.Shard, 15, moneyService)
                        )
                )
        );
    }

    private void addJob(JobList job, List<List<JIReward>> rewards) {
        jobStorage.put(job, rewards);
    }
}
