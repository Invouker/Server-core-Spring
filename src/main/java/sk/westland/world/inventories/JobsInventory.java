package sk.westland.world.inventories;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobProgression;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.enums.JobList;
import sk.westland.core.inventory.OwnerItemMenu;
import sk.westland.core.items.ItemBuilder;
import sk.westland.core.items.Nbt;
import sk.westland.core.jobs.JobRewards;
import sk.westland.core.jobs.JobStorage;
import sk.westland.core.rewards.IReward;
import sk.westland.core.services.MoneyService;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.ChatInfo;
import sk.westland.core.utils.Utils;

import java.util.List;

public class JobsInventory extends OwnerItemMenu {


    private JobRewards jobRewards;
    private final MoneyService moneyService;
    private final PlayerService playerService;

    private int fromLevel;
    private int untilLevel;
    private int page;
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
        this.untilLevel = job.getMaxLevel()+1;
        this.jobRewards = new JobRewards(moneyService);

        /* DEBUG
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
        int level = fromLevel;
        for (int i = 0; i < position.length; i++) {

            if(level >= untilLevel) {
                setItem(position[i], STICK_EMPTY);
                continue;
            } // zabrať pozicie kde level prekročil limit

            if(position[i] == 9 && page == 0 && level == 1) {
                Material mat = Material.RED_STAINED_GLASS_PANE;

                ItemBuilder itemBuilder =  new ItemBuilder(mat)
                        .setName("§b§lLevel 1")
                        .setLore("",
                                "§7Začiatočný level",
                                "§7neobnáša odmenu.");
                if(isInJob()) {
                    mat = Material.LIME_STAINED_GLASS_PANE;
                    itemBuilder.addLore("", "§aLevel splnený!");
                    itemBuilder.setMaterial(mat);
                }


                getInventory().setItem(9, itemBuilder.build());
                level++;
                continue;
            } // level 1 logika

            if(getWlPlayer().isJobRewardClaimed(job, level)) {
                getInventory().setItem(position[i], alreadyClaimedItem(level++));
            }else {
                if(getJobLevel() != -1 && getJobLevel() >= level ) { // || getWlPlayer().getHighestLevelClaimed(job) < level) {
                    getInventory().setItem(position[i], renderUnlockedItem(level++));
                } else {
                    getInventory().setItem(position[i], renderLockedItem(level++));
                }
            }
        }
        {
            setItemCloseInventory();
            //setItem(4, 5, new ItemBuilder(CLOSE_INVENTORY_ITEM).setName("§cZatvoriť").build());
            if (page > 0) setItem(3, 5, new ItemStack(Material.ARROW));//pageInventory(Direction.Left)
            else setItem(3, 5, new ItemBuilder(Material.RED_BED).setName("§cVrátiť sa").build());

            if ((job.getMaxLevel() / 21) - 1 >= page) setItem(5, 5, new ItemStack(Material.ARROW));
            else setItem(5, 5, STICK_EMPTY);
        } // Next / Previous Pages
        {
            if (isInJob())
                setItem(0, 5, new ItemBuilder(Material.DIAMOND).setName("§bInformácie")
                        .setLore("§8STRANA " + (page + 1),
                                "",
                                PlaceholderAPI.setPlaceholders(getPlayer(), "&7Level: &f%jobsr_user_jlevel_" + job.getName() + "%/%jobsr_user_jmaxlvl_" + job.getName() + "%"),
                                PlaceholderAPI.setPlaceholders(getPlayer(), "&7EXP: &f%jobsr_user_jexp_" + job.getName() + "%/%jobsr_user_jmaxexp_" + job.getName() + "%")
                        )
                        .setAmount(page + 1).build());
            else

                setItem(0, 5, new ItemBuilder(Material.DIAMOND).setName("§bInformácie")
                        .setLore("§8STRANA " + (page + 1),
                                "",
                                "§7Štatistiky sa zobrazia až keď",
                                "§7sa zamestnáš ako §f" + job.getName() + "§7."
                        )
                        .setAmount(page + 1).build());
        } // Informacie

        setItem(0, 0, new ItemBuilder(job.getIcon()).setName("§b" + job.getName()).build());
    }

    private ItemStack alreadyClaimedItem(int level) {
        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
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

        List<IReward> rewardList = jobStorage.getJobRewards(job, level);

        if(rewardList == null)
            return getInvalidRewardItem(level);

        String[] lore = new String[rewardList.size()];
        for (int i = 0; i < rewardList.size(); i++) {
            IReward iReward = rewardList.get(i);
            if(iReward.render() == null) continue;
            lore[i] =  "§6" + iReward.render();
        }

        return new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE)
                .setName("§b§lLevel " + level)
                .setLore("§8ODOMKNUTÉ",
                        "",
                        "§7Odomknutím tohto levelu",
                        "§7si získal prístup k odmene.",
                        "",
                        "§f§lODMENA:")
                .addLore(lore)
                .addLore("", "§aKlikni pre vyzdvihnutie!")
                .setNbt_Int(NBT_KEY_LEVE_ID, level)
                .build();
    }

    private ItemStack renderLockedItem(int level) {
        JobStorage jobStorage = jobRewards.getJobStorage();
        List<IReward> rewardList = jobStorage.getJobRewards(job, level);
        if(rewardList == null)
            return getInvalidRewardItem(level);

        String[] lore = new String[rewardList.size()];
        for (int i = 0; i < rewardList.size(); i++) {
            IReward iReward = rewardList.get(i);
            if(iReward.render() == null) continue;
            lore[i] = "§a" + iReward.render();
        }

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName("§b§lLevel " + level)
                .setLore("§8ZAMKNUTÉ",
                        "",
                        "§7Splň tento level a získaj",
                        "§7svoju zaslúženú odmenu.",
                        "",
                        "§f§lODMENA:")
                .addLore(lore)
                .setNbt_Int(NBT_KEY_LEVE_ID, level)
                .build();
    }

    @Override
    protected void onClick(int slot, @Nullable ItemStack item, @Nullable ItemStack cursor, @NotNull InventoryClickEvent event) {

        if(item != null && item.getType() == Material.RED_BED) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dm open praceOdmeny " + getPlayer().getName());
            return;
        }

        switch (slot) {
            case 48: { // previous page
                if(page > 0) {
                    Utils.playSound(getPlayer(), Sound.UI_BUTTON_CLICK);
                    //close(getPlayer());

                    if (page - 1 >= 0) {
                        this.page = this.page - 1;
                        this.fromLevel = page * 21 == 0 ? 1 : page * 21;
                        this.untilLevel = job.getMaxLevel()+1;

                        itemInit();
                        /*JobsInventory jobsInventory =
                                new JobsInventory(moneyService,
                                        playerService,
                                        getWlPlayer(), job,
                                        (page - 1));
                        jobsInventory.open(getPlayer());

                         */
                    }
                }
                break;
            }

            case 50: { // next page
                if((job.getMaxLevel() / 21)-1 >= page) {
                    Utils.playSound(getPlayer(), Sound.UI_BUTTON_CLICK);
                    //close(getPlayer());

                    if (page >= 0) {
                        /*JobsInventory jobsInventory =
                                new JobsInventory(moneyService,
                                        playerService,
                                        getWlPlayer(), job,
                                        (page + 1));
                        jobsInventory.open(getPlayer());*/
                        this.page = this.page + 1;
                        this.fromLevel = page * 21 == 0 ? 1 : page * 21;
                        this.untilLevel = job.getMaxLevel()+1;

                        itemInit();
                    }
                }
                break;
            }
        }

        if(isRewardPosition(slot)) {
            int level = Nbt.getNbt_Int(item, NBT_KEY_LEVE_ID, -1);

            if(level == -1 || level == 1)
                return;

            Utils.playSound(getPlayer(), Sound.UI_BUTTON_CLICK);

            if(getJobLevel() < level || getJobLevel() == -1)
                return;

            if(getWlPlayer().isJobRewardClaimed(job, level)) {
                ChatInfo.ERROR.send(getPlayer(), "Už si si vybral odmenu pre tento level!");
                return;
            }

            List<IReward> rewards = jobRewards.getJobStorage().getJobRewards(job, level);
            if(rewards == null)
                return;

            for(IReward reward : rewards) {
                if(reward == null)
                    return;
                reward.reward(getPlayer());
            }
            playerService.getWLPlayer(getPlayer()).jobRewardClaim(job, level);
            ChatInfo.SUCCESS.send(getPlayer(), "Úspešne si si vybral odmenu za " + job.getName() + ", pre level " + level);
            Utils.playSound(getPlayer(), Sound.ENTITY_PLAYER_LEVELUP);

            itemInit();
        }
    }

    private boolean isInJob() {
        Job jobs = getJobFromName(job.getOriginalName());
        return Jobs.getPlayerManager().getJobsPlayer(getPlayer()).isInJob(jobs);
    }

    private int getJobLevel() {
        Job jobs = getJobFromName(job.getOriginalName());
        JobProgression jobProgression = Jobs.getPlayerManager().getJobsPlayer(getPlayer()).getJobProgression(jobs);
        if(jobProgression == null)
            return -1;

        return jobProgression.getLevel();
    }

    private com.gamingmesh.jobs.container.Job getJobFromName(@NotNull JobList job) {
        return getJobFromName(job.getOriginalName());
    }

    private com.gamingmesh.jobs.container.Job getJobFromName(String name) {
        List<JobProgression> jobs =
                Jobs.getPlayerManager().getJobsPlayer(getPlayer()).getJobProgression();

        for(JobProgression jobProgression : jobs) {
            com.gamingmesh.jobs.container.Job job = jobProgression.getJob();
            if(job.getName().contains(name))
                return job;
        }
        return null;
    }

    private @NotNull ItemStack getInvalidRewardItem(int level) {
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
