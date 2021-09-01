package sk.westland.world;

import org.bukkit.event.Listener;

@org.springframework.stereotype.Component
public class Test implements Listener {
/*
    private List<Player> playersWithRP;
    private List<WLEntityPlayer> playerTabList;
    private ProtocolManager protocolManager;
    private EntityPlayer[] playersNMS;

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPluginEnable(PluginEnableEvent event) {
        this.protocolManager = WestLand.getProtocolManager();
        playersWithRP = new ArrayList<>();
        playerTabList = new ArrayList<>();

        Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), () -> {
            EventBus eventBus = App.getService(APIServices.class).getLuckPerms().getEventBus();
            eventBus.subscribe(WestLand.getInstance(), NodeMutateEvent.class, (nodeMutateEvent) -> updatePlayerTabForAll());

            updatePlayerTabForAll();
            }, 20L);
    }



    @EventHandler
    private void onResourcePackStatusEvent(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), () -> {
            if(event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
                playersWithRP.add(event.getPlayer());
                //sendTabListFooterHeader(player, true);
            }
            //updatePlayerTabForAll();
        }, 13L);
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerQuit(PlayerQuitEvent event) {

    }

    private void updatePlayerNMSArray() {
        this.playersNMS = null;
        Collection<? extends Player> playersBukkit = Bukkit.getOnlinePlayers();
        this.playersNMS = new EntityPlayer[playersBukkit.size()];
        int current = 0;
        for (Player _player : playersBukkit) {
            this.playersNMS[current] = ((CraftPlayer) _player).getHandle();
            current++;
        }
    }

    private void sendPacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction playerInfoAction, EntityPlayer entityPlayer) {
        Bukkit.getOnlinePlayers().forEach(player_ -> sendPacketPlayOutPlayerInfo(player_, playerInfoAction, entityPlayer));
    }

    private void sendPacketPlayOutPlayerInfo(Player player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction playerInfoAction) {
        updatePlayerNMSArray();
        sendPacketPlayOutPlayerInfo(player, playerInfoAction, playersNMS);
    }

    private void sendPacketPlayOutPlayerInfo(Player player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction playerInfoAction, EntityPlayer... players) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(playerInfoAction, players);
        getPlayerConnectionFrom(player).playerConnection.sendPacket(packet);
    }

    private void removePlayerFromTab(Player player, Player target) {
        EntityPlayer entityPlayer = null;
        for (Player _player : Bukkit.getOnlinePlayers()) {
            var ePlayer = ((CraftPlayer) _player).getHandle();
            if(_player.getName().equalsIgnoreCase(ePlayer.getName())) {
                entityPlayer = ePlayer;
                break;
            }
        }

        if(entityPlayer == null)
            return;

        var playerConnection = ((CraftPlayer) player).getHandle();
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer);
        playerConnection.playerConnection.sendPacket(packet);
    }

    private void updatePlayerTab(Player player) {
        updatePlayerNMSArray();

        for(EntityPlayer entityPlayer : playersNMS) {
            var bukkitPlayer = Bukkit.getPlayer(entityPlayer.getName());
            var primaryGroup = App.getService(VaultService.class).getPerms().getPrimaryGroup(bukkitPlayer);
            var wlEntityPlayer = new WLEntityPlayer(entityPlayer, bukkitPlayer, Group.findByName(primaryGroup));
            playerTabList.add(wlEntityPlayer);

            Group group = Group.findByName(primaryGroup);
            var groupPrefix = "";
            if(!playersWithRP.contains(player)) {
                groupPrefix = group.getPrefixColor() + group.getPrefix() + "§f ";
            } else groupPrefix = group.getRpPrefix();

            entityPlayer.listName = new ChatComponentText(groupPrefix + " " + entityPlayer.getName());
        }

        //sortWLPlayers();
        var players = new EntityPlayer[playerTabList.size()];
        for (int i = 0; i < playerTabList.size(); i++) {
            players[i] = playerTabList.get(i).getEntityPlayer;
            System.out.println("playerTabList.get(i).getEntityPlayer: " + playerTabList.get(i).getEntityPlayer.getName());
        }

        //TODO: Sort WLEntityPlayer and display it as NMS packet
        //sendPacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, players);
        addPlayerToTab(player, players);
    }


    private void addPlayerToTab(Player player, EntityPlayer... players){
        PacketPlayOutPlayerInfo packetA = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, players);
        getPlayerConnectionFrom(player).playerConnection.sendPacket(packetA);
    }

    private void sortWLPlayers() {
        playerTabList = playerTabList.stream()
                .sorted(Comparator.comparing((wlEntityPlayer -> wlEntityPlayer.getGroup().getPrioritySorting()))).toList();
    }

    private void updatePlayerTabForAll() {
        Bukkit.getOnlinePlayers().forEach(this::updatePlayerTab);
    }

    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), () -> {
            updatePlayerNMSArray();

            sendTabListFooterHeader(player, false);
            sendPacketPlayOutPlayerInfo(player, PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
            updatePlayerTabForAll();
        }, 13L);
    }


    private void onPlayerQuits(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(WestLand.getInstance(), () -> {
            playersWithRP.remove(player);
            playerTabList.remove(findWLEntityByPlayer(player));

            updatePlayerTabForAll();

            sendPacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                    getPlayerConnectionFrom(event.getPlayer()));
        }, 13L);

    }

    private EntityPlayer getPlayerConnectionFrom(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    private void sendTabListFooterHeader(Player player, boolean withResourcePack) {
        if(withResourcePack) {
            player.sendPlayerListHeaderAndFooter(
                    Component.text("\n         ⻲⻔⻱   §r      \n\n§fplay.westland.sk\n")
                            .asComponent(), Component.text("\n").asComponent());

        } else {
            player.sendPlayerListHeaderAndFooter(
                    Component.text("\n         §e§lWESTLAND   §r      \n§fplay.westland.sk\n")
                            .asComponent(), Component.text("\n").asComponent());
        }
    }

    private WLEntityPlayer findWLEntityByPlayer(Player player) {
        return playerTabList.stream()
                .filter(wlEntityPlayer -> wlEntityPlayer.player.getName().equalsIgnoreCase(player.getName()))
                .findFirst().orElse(null);
    }

    record WLEntityPlayer(EntityPlayer getEntityPlayer, Player player, Group getGroup) {
        @Override
        public EntityPlayer getEntityPlayer() {
            return getEntityPlayer;
        }

        @Override
        public Group getGroup() {
            return getGroup;
        }
    }*/


}

/*
* /*
        protocolManager.addPacketListener(new PacketAdapter(WestLand.getInstance(),
                ListenerPriority.HIGHEST,
                PacketType.Play.Server.PLAYER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {

                PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);

                //var action = event.getPacket().getPlayerInfoAction().read(0);


                var playerInfoDataList = packetContainer.getPlayerInfoDataLists().read(0);

                if(playerInfoDataList.size() <= 0)
                    return;

                var chatComponent = playerInfoDataList.get(0).getDisplayName();
                // var x = packetContainer.getPlayerInfoAction().read(0);
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject json = (JSONObject) jsonParser.parse(chatComponent.getJson());
                    if(event.getPlayer().getName().equals("XpresS"))
                        System.out.println("if Leader: " + json.get("text"));
                    json.put("text",  "CH: " + event.getPlayer().getName());
                    chatComponent.setJson(json.toJSONString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                packetContainer.getPlayerInfoDataLists().write(0, playerInfoDataList);

                var test = packetContainer.getPlayerInfoDataLists().read(0);
                var name = test.get(0).getDisplayName();


                //event.setPacket(packetContainer);
                //event.setCancelled(true);
                try {
                    protocolManager.sendServerPacket(event.getPlayer(), packetContainer);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                //protocolManager.broadcastServerPacket(packetContainer);
                /*System.out.println("Player" + event.getPlayer().getName() + ", Data: " + test);
                    System.out.println("playerInfoAction.name(): " + playerInfoAction.name());
*
            }
        });
* */
