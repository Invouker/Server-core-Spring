package sk.westland.core.jobs;

import org.bukkit.Material;
import sk.westland.core.enums.JobList;
import sk.westland.core.enums.MoneyType;
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
        for(JobList job : JobList.values()) {
            addDrevorubacJob(job);
        }
    }
    private void addIDKJob(JobList job) {
        addJob(job,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 25, moneyService),
                                new JItemReward(Material.IRON_AXE),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 6, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Shard, 2, moneyService),
                                new JMoneyReward(MoneyType.Money, 50, moneyService),
                                new JItemReward(Material.ACACIA_SAPLING, 3),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 26, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 29, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 10
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 11
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 15
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 16
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 17
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 18
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 19
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 20
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 21
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 24
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 25
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 26
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 27
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 28
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 29
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 30
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 31
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 33
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 34
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 35
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 36
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 37
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 38
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 39
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 40
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 43
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 44
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 45
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 46
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 47
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 48
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 49
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 50
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 53
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 54
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 55
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 56
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 57
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 58
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 59
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 60
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 63
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 64
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 65
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 66
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 67
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 68
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 69
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 70
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 73
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 74
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 75
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 76
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 77
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 78
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 79
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 80
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 83
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 84
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 85
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 86
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 87
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 88
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 89
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 90
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 92
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 93
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 94
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 95
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 96
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 97
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 98
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 99
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        )
                )
        );
    }

    private void addDrevorubacJob(JobList job) {
        addJob(job,

                Arrays.asList(
                        null,
                        null,
                        Arrays.asList( // level 2
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 3
                                new JMoneyReward(MoneyType.Money, 25, moneyService),
                                new JItemReward(Material.IRON_AXE),
                                new JExpReward(30)
                        ),
                        Arrays.asList( // level 4
                                new JMoneyReward(MoneyType.Gems, 6, moneyService),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 5
                                new JMoneyReward(MoneyType.Shard, 2, moneyService),
                                new JMoneyReward(MoneyType.Money, 50, moneyService),
                                new JItemReward(Material.ACACIA_SAPLING, 3),
                                new JExpReward(40)
                        ),
                        Arrays.asList( // level 6
                                new JMoneyReward(MoneyType.Money, 26, moneyService)
                        ),
                        Arrays.asList( // level 7
                                new JMoneyReward(MoneyType.Money, 29, moneyService)
                        ),
                        Arrays.asList( // level 8
                                new JPermReward("permission.dot.idk", "Pre pokrok k dalšej urovni!", JPermReward.PermRenderType.HIDE),
                                new JItemReward(Material.BAMBOO, 6)
                        ),
                        Arrays.asList( // level 9
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 10
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 11
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 12
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 13
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 14
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 15
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 16
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 17
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 18
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 19
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 20
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 21
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 22
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 23
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 24
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 25
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 26
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 27
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 28
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 29
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 30
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 31
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 32
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 33
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 34
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 35
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 36
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 37
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 38
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 39
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 40
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 41
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 42
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 43
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 44
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 45
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 46
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 47
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 48
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 49
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 50
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 51
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 52
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 53
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 54
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 55
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 56
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 57
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 58
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 59
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 60
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 61
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 62
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 63
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 64
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 65
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 66
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 67
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 68
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 69
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 70
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 71
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 72
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 73
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 74
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 75
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 76
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 77
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 78
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 79
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 80
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 81
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 82
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 83
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 84
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 85
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 86
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 87
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 88
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 89
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 90
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 91
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 92
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 93
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 94
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 95
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 96
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 97
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 98
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 99
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        ),
                        Arrays.asList( // level 100
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        )
                )
        );
    }

    private void addJob(JobList job, List<List<JIReward>> rewards) {
        jobStorage.put(job, rewards);
    }
}
