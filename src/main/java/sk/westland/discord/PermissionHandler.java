package sk.westland.discord;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.westland.core.WestLand;
import sk.westland.core.database.player.RankData;
import sk.westland.core.database.player.RankDataRepository;
import sk.westland.core.database.player.UserData;
import sk.westland.core.database.player.UserDataRepository;
import sk.westland.core.discord.DiscordHandler;
import sk.westland.core.discord.ranksync.Rank;
import sk.westland.core.services.APIServices;
import sk.westland.core.services.PlayerService;
import sk.westland.core.utils.RunnableHelper;

import java.util.Optional;

@Component
public class PermissionHandler implements Listener, Runnable {

    @Autowired
    private APIServices apiServices;

    @Autowired
    private RankDataRepository rankDataRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserDataRepository userDataRepository;

    private static PermissionHandler permissionHandler;

    public PermissionHandler() {
        RunnableHelper.runTaskLater(this, 20L);
    }

    @Override
    public void run() {

        permissionHandler = this;

        if(apiServices == null) {
            System.out.println("Error with apiservices");
            return;
        }

        System.out.println();
        EventBus eventBus = apiServices.getLuckPerms().getEventBus();
        eventBus.subscribe(WestLand.getInstance(), NodeAddEvent.class, this::onNodeAdd);
        eventBus.subscribe(WestLand.getInstance(), NodeRemoveEvent.class, this::onNodeRemove);
    }

    public static PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    private synchronized void onNodeAdd(NodeAddEvent e) {
        if (!e.isUser()) {
            return;
        }

        User target = (User) e.getTarget();
        Node node = e.getNode();

        // LuckPerms events are posted async, we want to process on the server thread!
        Bukkit.getServer().getScheduler().runTask(WestLand.getInstance(), () -> {
            Player player = Bukkit.getServer().getPlayer(target.getUniqueId());
            if (node instanceof InheritanceNode) {
                String groupName = ((InheritanceNode) node).getGroupName();

                long playerId = -1;
                if (player == null) { // player is not connected
                    Optional<UserData> userDataOptional = userDataRepository.findByUuid(target.getUniqueId().toString());
                    if(!userDataOptional.isPresent())
                        return;

                    playerId = userDataOptional.get().getId();
                   // return; // Player not online.
                } else playerId = playerService.getWLPlayer(player).getPlayerId();

                updateRole(playerId, groupName);
            }
        });
    }

    public void updateRole(long playerId, String groupName) {
        Rank rank = Rank.getDiscordGroupByVault(groupName);
        String discordGroup = rank.getDiscordGroup();
        DiscordHandler discordHandler = WestLand.getDiscordHandler();
        Optional<RankData> rankDataOptional = rankDataRepository.findByPlayerIdAndIsSynced(playerId, true);

        if(!rankDataOptional.isPresent())
            return;

        RankData rankData = rankDataOptional.get();
        Guild guild = discordHandler.getJda().getGuildById("796403023681290251");

        //TextChannel leaderChannel = guild.getTextChannelById("796421035670896651");
        if(guild == null)
            return;

        guild.loadMembers((member)-> {

            String userId = rankData.getDiscordUuid();

            if (member.getUser().isBot())
                return;

            if (!member.getId().equals(userId))
                return;

            String discordId = rankData.getDiscordUuid();

            //net.dv8tion.jda.api.entities.User user = member.getUser();
            Role role = guild.getRoleById(discordGroup);
            if(role == null)
                return;

            removeAllRoles(guild, discordId);
/*
                    guild.addRoleToMember(discordId, role).queue((i) -> {
                        if(rank.getRankData() == Rank.RankEnum.ADMIN) {
                            String stafflist = Rank.getDiscordGroupByVault("stafflist").getDiscordGroup();
                            Role staffRole = guild.getRoleById(stafflist);
                            if(staffRole == null)
                                return;

                            guild.addRoleToMember(discordId, staffRole).queue();
                        }
                    });*/

            if(rank.getRankData() == Rank.RankEnum.ADMIN)
                addRoleToMember(guild, discordId, "stafflist");
            addRoleToMember(guild, discordId, groupName);
            addRoleToMember(guild, discordId, "default");
        });
    }

    private void addRoleToMember(Guild guild, String discordId, String roleName) {
        for (Rank rank : Rank.getRankList()) {
            if(!rank.getVaultGroup().equalsIgnoreCase(roleName))
                continue;

            Role role = guild.getRoleById(rank.getDiscordGroup());
            if(role == null)
                continue;

            System.out.println("Pridávam rolu:  " + role.getName() + ", hráčovy: " + guild.getMemberById(discordId));
            guild.addRoleToMember(discordId, role).queue();
        }
    }

    private void removeAllRoles(Guild guild, String discordId) {
        for (Rank rank : Rank.getRankList()) {
            Role role = guild.getRoleById(rank.getDiscordGroup());
            if(role == null)
                continue;

            if(rank.getVaultGroup().equalsIgnoreCase("default"))
                continue;

            guild.removeRoleFromMember(discordId, role).queue();
        }
    }

    private void onNodeRemove(NodeRemoveEvent e) {
        if (!e.isUser()) {
            return;
        }

        User target = (User) e.getTarget();
        Node node = e.getNode();

        // LuckPerms events are posted async, we want to process on the server thread!
        Bukkit.getServer().getScheduler().runTask(WestLand.getInstance(), () -> {
            Player player = Bukkit.getServer().getPlayer(target.getUniqueId());
            if (player == null) {
                return; // Player not online.
            }

            if (node instanceof InheritanceNode) {
                String groupName = ((InheritanceNode) node).getGroupName();
                player.sendMessage(ChatColor.DARK_RED + "You are no longer in the " + groupName + " group!");
            }
        });
    }
}
