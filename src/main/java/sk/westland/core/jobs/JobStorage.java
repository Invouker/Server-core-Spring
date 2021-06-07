package sk.westland.core.jobs;

import sk.westland.core.enums.JobList;
import sk.westland.core.rewards.IReward;

import java.util.HashMap;
import java.util.List;


public class JobStorage extends HashMap<String, List< List<IReward>> >  {
    // *String* - Job Name
    // 1.st is for level selection
    // 2.nd is list of rewards for level selection

    public List<List<IReward>> put(JobList name, List< List<IReward> > lists) {
        return this.put(name.getName(), lists);
    }

    public List< List<IReward>> getJob(JobList job) {
        return this.get(job.getName());
    }

    public List< List<IReward>> getJob(String jobName) {
        return this.get(jobName);
    }

    public List<IReward> getJobRewards(JobList job, int level) {
        if(getJob(job) != null && getJob(job).size() > level)
            return this.getJobRewards(job.getName(), level);
        return null;
    }

    public List<IReward> getJobRewards(String jobName, int level) {
        return this.get(jobName).get(level);
    }

    public IReward getJobReward(JobList job, int level, int reward) {
        return this.getJobReward(job, level, reward);
    }

    public IReward getJobReward(String jobName, int level, int reward) {
        return this.get(jobName).get(level).get(reward);
    }


}
