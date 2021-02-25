package sk.westland.core.jobs;

import sk.westland.core.enums.JobList;
import sk.westland.core.jobs.rewards.JIReward;

import java.util.HashMap;
import java.util.List;


public class JobStorage extends HashMap<String, List< List<JIReward>> >  {
    // *String* - Job Name
    // 1.st is for level selection
    // 2.nd is list of rewards for level selection

    public List<List<JIReward>> put(JobList name, List< List<JIReward> > lists) {
        return this.put(name.getName(), lists);
    }

    public List< List<JIReward>> getJob(JobList job) {
        return this.get(job.getName());
    }

    public List< List<JIReward>> getJob(String jobName) {
        return this.get(jobName);
    }

    public List<JIReward> getJobRewards(JobList job, int level) {
        if(getJob(job) != null && getJob(job).size() > level)
            return this.getJobRewards(job.getName(), level);
        return null;
    }

    public List<JIReward> getJobRewards(String jobName, int level) {
        return this.get(jobName).get(level);
    }

    public JIReward getJobReward(JobList job, int level, int reward) {
        return this.getJobReward(job, level, reward);
    }

    public JIReward getJobReward(String jobName, int level, int reward) {
        return this.get(jobName).get(level).get(reward);
    }


}
