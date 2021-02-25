package sk.westland.core.jobs;

import sk.westland.core.enums.JobList;
import sk.westland.core.enums.MoneyType;
import sk.westland.core.jobs.rewards.JExpReward;
import sk.westland.core.jobs.rewards.JIReward;
import sk.westland.core.jobs.rewards.JItemReward;
import sk.westland.core.jobs.rewards.JMoneyReward;
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
                                new JMoneyReward(MoneyType.Money, 10, moneyService),
                                new JExpReward(20)
                        ),
                        Arrays.asList( // level 4
                                new JItemReward(Materials.Items.BETTER_HOE.getItem()),
                                new JItemReward(Materials.Items.CROWBAR.getItem())
                        )
                )
        );
    }

    private void addJob(JobList job, List<List<JIReward>> rewards) {
        jobStorage.put(job, rewards);
    }
}
