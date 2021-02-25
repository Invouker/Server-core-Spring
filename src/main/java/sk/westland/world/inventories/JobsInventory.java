package sk.westland.world.inventories;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import net.minecraft.server.v1_16_R3.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.WestLand;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.JobList;
import sk.westland.core.inventory.OwnerItemMenu;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.core.jobs.JobRewards;
import sk.westland.core.jobs.JobStorage;
import sk.westland.core.jobs.rewards.JIReward;
import sk.westland.core.services.MoneyService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;

import java.util.List;

public class JobsInventory extends OwnerItemMenu {


    private JobRewards jobRewards;
    private final MoneyService moneyService;
    private final PlayerService playerService;

    private final int fromLevel;
    private final int untilLevel;
    private final int page;
    private final JobList job;

    private static final String NBT_KEY_LEVE_ID = "LEVEL";

    private final static int[] position = new int[] {9,18,27,28,29,20,11,12,13,22,31,32,33,24,15,16,17,26,35,44,53};

    public JobsInventory(MoneyService moneyService, PlayerService playerService, WLPlayer wlPlayer, JobList job, int page) {
        //super(wlPlayer, Type.Chest6, "§8Skúsenosti: §8§l" + job.getName() + " §8[" + (page +1) + "/" + ((job.getMaxLevel() / 21)+1) + "]" , "");
        super(wlPlayer, Type.Chest6, "§f⻔⻔⻔⻔⻔⻔⻔⻔⼝" , "");

        this.job = job;
        this.moneyService = moneyService;
        this.playerService = playerService;
        this.page = page;
        this.fromLevel = page * 21 == 0 ? 1 : page * 21;
        this.untilLevel = job.getMaxLevel();
        this.jobRewards = new JobRewards(moneyService);

        /*
        jobRewards.getJobStorage().forEach((name, jobRewards) -> {
            System.out.println("Name: " + name);
            jobRewards.forEach((list) -> {
                if(list == null) return;
                    list.forEach( (jiReward -> {
                        if(jiReward != null)
                            System.out.println("Reward: " + jiReward.render());
                    }));
            }
            );
        });*/


        itemInit();
    }

    @Override
    protected void itemInit() {
        for (int i = 0; i < getInventory().getContents().length; i++) {
            if(!isRewardPosition(i)) {
                getInventory().setItem(i, new ItemStack(Material.AIR));
            }
        }

        int level = fromLevel;
        for (int i = 0; i < position.length; i++) {

            if(level > untilLevel) {
                getInventory().setItem(i, STICK_EMPTY);
                continue;
            }

            if(position[i] == 9 && page == 0 && level == 1) {
                getInventory().setItem(9, new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                        .setName("§b§lLevel 1")
                        .setLore("",
                                "§7Začiatočný level",
                                "§7neobnáša odmenu.")
                        .build());
                level++;
                continue;
            }

            if(getWlPlayer().isJobRewardClaimed(job, level)) {
                getInventory().setItem(position[i], alreadyClaimedItem(level++));
            }else {
                if((getJobLevel() != -1 && getJobLevel() <= level) || getWlPlayer().getHighestLevelClaimed(job) < level) {
                    getInventory().setItem(position[i], renderUnlockedItem(level++));
                } else {
                    getInventory().setItem(position[i], renderLockedItem(level++));
                }
            }
        }

        setItem(4, 5, STICK_CLOSE);
        if(page > 0) setItem(3, 5, pageInventory("Vrátiť sa"));
        if((job.getMaxLevel() / 21) >= page) setItem(5, 5, pageInventory("Ďalšia strana"));

        setItem(0, 5, new ItemBuilder(Material.DIAMOND).setName("§fUnnamed").build());
        //setItem(0, 0, ItemBuilder.setStackSize(new ItemBuilder(job.getIcon()).setName("§b" + job.getName()).build(), 100));
        getInventory().setMaxStackSize(100);
        //setItem(0, 0, new ItemBuilder(job.getIcon()).setName("§b" + job.getName()).setMaxStackSize(100).build());
        setItem(1, 0, new ItemBuilder(job.getIcon()).setName("§b" + job.getName()).setAmount(85).build());
        getInventory().setMaxStackSize(30);
        Bukkit.getScheduler().runTask(WestLand.getInstance(), () -> getPlayer().updateInventory());
    }

    private ItemStack pageInventory(String name) {
        return new ItemBuilder(Material.ARROW)
                .setName("§c" + name)
                .build();
    }

    private ItemStack alreadyClaimedItem(int level) {
        return new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                .setName("§b§lLevel " + level)
                .setLore("§8ODOMKNUTÉ",
                        "",
                        "§7Tento level bol",
                        "§7úspešne splnený.",
                        "",
                        "§aOdmena vyzdvihnutá!")
                .setNbt_Int(NBT_KEY_LEVE_ID, level)
                .build();
    }

    private ItemStack renderUnlockedItem(int level) {
        JobStorage jobStorage = jobRewards.getJobStorage();

        List<JIReward> rewardList = jobStorage.getJobRewards(job, level);

        if(rewardList == null)
            return getInvalidRewardItem(level);

        String[] lore = new String[rewardList.size()];
        for (int i = 0; i < rewardList.size(); i++) {
            JIReward jiReward = rewardList.get(i);
            lore[i] = jiReward.render();
        }

        return new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE)
                .setName("§b§lLevel " + level)
                .setLore("§8ODOMKNUTÉ",
                        "",
                        "§7Splň tento level a získaj",
                        "§7následovnú odmenu:",
                        "",
                        "§f§lODMENA")
                .addLore(lore)
                .addLore("", "§aKlikni pre vyzdvihnutie!")
                .setNbt_Int(NBT_KEY_LEVE_ID, level)
                .build();
    }

    private ItemStack renderLockedItem(int level) {
        JobStorage jobStorage = jobRewards.getJobStorage();
        List<JIReward> rewardList = jobStorage.getJobRewards(job, level);
        if(rewardList == null)
            return getInvalidRewardItem(level);

        String[] lore = new String[rewardList.size()];
        for (int i = 0; i < rewardList.size(); i++) {
            JIReward jiReward = rewardList.get(i);
            lore[i] = jiReward.render();
        }

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName("§b§lLevel " + level)
                .setLore("§8UZAMKNUTÉ",
                        "",
                        "§7Splň tento level a získaj",
                        "§7následovnú odmenu:",
                        "",
                        "§f§lODMENA:")
                .addLore(lore)
                .setNbt_Int(NBT_KEY_LEVE_ID, level)
                .build();
    }

    @Override
    protected void onClick(int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {

        switch (slot) {
            case 48: { // previous page
                if(page > 0) {
                    close(getPlayer());

                    if (page - 1 >= 0) {
                        JobsInventory jobsInventory =
                                new JobsInventory(moneyService,
                                        playerService,
                                        getWlPlayer(), job,
                                        (page - 1));
                        jobsInventory.open(getPlayer());
                    }
                }
                break;
            }

            case 50: { // next page
                if((job.getMaxLevel() / 21) >= page) {
                    close(getPlayer());

                    if (page >= 0) {
                        JobsInventory jobsInventory =
                                new JobsInventory(moneyService,
                                        playerService,
                                        getWlPlayer(), job,
                                        (page + 1));
                        jobsInventory.open(getPlayer());

                    }
                }
                break;
            }
        }

        if(isRewardPosition(slot)) {
            int level = Nbt.getNbt_Int(item, NBT_KEY_LEVE_ID, -1);

            if(level == -1 || level == 1)
                return;

            if(getJobLevel() < level || getJobLevel() == -1)
                return;

            if(getWlPlayer().isJobRewardClaimed(job, level)) {
                ChatInfo.ERROR.sendAll("Already claimed");
                return;
            }

            List<JIReward> rewards = jobRewards.getJobStorage().getJobRewards(job, level);
            if(rewards == null)
                return;

            for(JIReward reward : rewards) {
                if(reward == null)
                    return;
                reward.reward(getPlayer());
            }
            playerService.getWLPlayer(getPlayer()).jobRewardClaim(job, level);
            ChatInfo.SUCCESS.sendAll("Claimed a reward for " + job.getName() + " for level " + level);

            itemInit();
        }
    }

    private int getJobLevel() {
        Job jobs = getJobFromName(job.getName());
        JobProgression jobProgression = Jobs.getPlayerManager().getJobsPlayer(getPlayer()).getJobProgression(jobs);
        if(jobProgression == null)
            return -1;

        return jobProgression.getLevel();
    }

    private com.gamingmesh.jobs.container.Job getJobFromName(JobList job) {
        return getJobFromName(job.getName());
    }

    private com.gamingmesh.jobs.container.Job getJobFromName(String name) {
        List<JobProgression> jobs = Jobs.getPlayerManager().getJobsPlayer(getPlayer()).getJobProgression();
        for(JobProgression jobProgression : jobs) {
            com.gamingmesh.jobs.container.Job job = jobProgression.getJob();
            if(job.getName().contains(name))
                return job;
        }
        return null;
    }

    private ItemStack getInvalidRewardItem(int level) {
        return new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .setName("§b§lLevel " + level)
                .setLore("§8UZAMKNUTÉ",
                        "",
                        "§7Splň tento level a získaj",
                        "§7následovnú odmenu:",
                        "",
                        "§7§oŽiadna odmena")
                .setNbt_Int(NBT_KEY_LEVE_ID, level)
                .build();
    }

    @Override
    protected void onOpen(@NotNull Player player) {

    }

    @Override
    protected void onClose(@NotNull Player player) {

    }

    private boolean isRewardPosition(int pos) {
        for (int i = 0; i < position.length; i++) {
            if(position[i] == pos)
                return true;
        }
        return false;
    }
}
