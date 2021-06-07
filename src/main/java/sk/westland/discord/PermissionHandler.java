package sk.westland.discord;

import dev.alangomes.springspigot.util.Synchronize;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
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
import sk.westland.core.services.APIServices;
import sk.westland.core.services.PlayerService;
import sk.westland.discord.ranksync.Rank;

import java.util.ArrayList;
import java.util.List;
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
        Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), this, 10L);
        //runnableService.runTaskLater(this, 20L);
    }

    @Override
    public void run() {
        permissionHandler = this;

        if(apiServices == null) {
            System.out.println("Error with apiservices");
            return;
        }

        EventBus eventBus = apiServices.getLuckPerms().getEventBus();
        eventBus.subscribe(WestLand.getInstance(), NodeAddEvent.class, this::onNodeAdd);
        eventBus.subscribe(WestLand.getInstance(), NodeRemoveEvent.class, this::onNodeRemove);
    }

    public static PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    @Synchronize
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

                long playerId;
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

    @Synchronize
    public synchronized void updateRole(long playerId, String groupName) {
        Rank rank = Rank.getDiscordGroupByVault(groupName);
        String discordGroup = rank.getDiscordGroup();
        DiscordHandler discordHandler = WestLand.getDiscordHandler();
        Optional<RankData> rankDataOptional = rankDataRepository.findByPlayerId(playerId);

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

            //removeAllRoles(guild, discordId, member.getRoles());

            if(rank.getRankData() == Rank.RankEnum.ADMIN)
                addRoleToMember(guild, member, discordId, "stafflist");
            addRoleToMember(guild, member,discordId, groupName);
            addRoleToMember(guild, member,discordId, "default");
        });
    }

    private void addRoleToMember(Guild guild, Member member, String discordId, String roleName) {
        final List<Role> roles = new ArrayList<>();
        for (Rank rank : Rank.getRankList()) {
            if(!rank.getVaultGroup().equalsIgnoreCase(roleName))
                continue;

            Role role = guild.getRoleById(rank.getDiscordGroup());
            if(role == null)
                continue;

            if(member.getRoles().contains(role))
                continue;

            guild.addRoleToMember(discordId, role).queue();
            roles.add(role);
            System.out.println("Pridávam rolu:  " + role.getName() + ", hráčovy: " + member.getAsMention());
        }

        { // remove other roles
            main: for (Role role : member.getRoles()) {

                if(roles.contains(role))
                    continue;

                for(Rank rank : Rank.getRankList()) {
                    if(rank.getDiscordGroup().equalsIgnoreCase(role.getId()))
                        continue;

                    continue main;
                }

                System.out.println("Vymazávam rolu: " + role.getName());
                guild.removeRoleFromMember(discordId, role).queue();

            }

            roles.clear();
        }
        /*
        { // remove other roles
            main: for(Role role : member.getRoles()) {
                for(Role cannotRemoveRole : roles) {

                    if(cannotRemoveRole.getId().equals(role.getId()))
                        continue main;

                    if(!listContainsRank(role.getId()))
                        continue main;

                    System.out.println("Vymazavam rolu: " + role.getName());
                    guild.removeRoleFromMember(discordId, role).queue();

                }
            }
        }*/
    }

    private boolean listContainsRank(String rankId) {
        for(Rank rank : Rank.getRankList()) {
            if (rank.getDiscordGroup().equalsIgnoreCase(rankId))
                return true;
        }
        return false;
    }

    private void removeAllRoles(Guild guild, String discordId, List<Role> roles) {
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
