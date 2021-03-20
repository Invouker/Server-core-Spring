package sk.westland.core.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.westland.core.WestLand;
import sk.westland.core.entity.player.WLPlayer;
import sk.westland.core.items.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomInventory implements InventoryHolder, Listener {

    protected static final ItemStack CLOSE_INVENTORY_ITEM = new ItemBuilder(Material.STRUCTURE_VOID).setName("§cClose Inventory").build();
    protected static final ItemStack GRAY_GLASS = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§r ").build();

    protected static final ItemStack STICK_EMPTY = new ItemBuilder(Material.STICK).setModelId(10).setName("§r ").build();
    protected static final ItemStack STICK_OK = new ItemBuilder(Material.STICK).setModelId(11).setName("§r ").build();
    protected static final ItemStack STICK_RIGHT = new ItemBuilder(Material.STICK).setModelId(16).setName("§r ").build();
    protected static final ItemStack STICK_LEFT = new ItemBuilder(Material.STICK).setModelId(15).setName("§r ").build();
    protected static final ItemStack STICK_UP = new ItemBuilder(Material.STICK).setModelId(13).setName("§r ").build();
    protected static final ItemStack STICK_DOWN = new ItemBuilder(Material.STICK).setModelId(14).setName("§r ").build();
    protected static final ItemStack STICK_CLOSE = new ItemBuilder(Material.STICK).setModelId(12).setName("§r ").build();
    /**
     * Crete new empty inventory.
     */
    public CustomInventory(@NotNull Type type, @NotNull String title)
    {
        this.type = type;
        this.inv = type.createInventory(this, title);
    }

    /**
     * Create copy of existing inventory (clones items).
     */
    public CustomInventory(@NotNull Inventory inventory, @NotNull String title)
    {
        this.type = Type.fromInventory(inventory);

        this.inv = type.createInventory(this, title);
        for (int i = 0; i < inventory.getSize(); i++)
        {
            @Nullable
            ItemStack is = inventory.getItem(i);
            if (is == null)
                inv.setItem(i, null);
            else
                inv.setItem(i, is.clone());
        }
    }

    @NotNull
    private final Type type;
    @NotNull
    private Inventory inv;

    @NotNull
    public Type getType()
    {
        return this.type;
    }


    protected enum Direction {
        Down,
        Up,
        Right,
        Left;
    }

    protected ItemStack pageInventory(Direction direction) {
        switch (direction) {
            case Left:
                return new ItemBuilder(STICK_LEFT)
                        .setName("§cVrátiť sa")
                        .build();
            case Right:
                return new ItemBuilder(STICK_RIGHT)
                        .setName("§cĎalšia strana")
                        .build();
            case Up:
                return new ItemBuilder(STICK_UP)
                        .setName("§cHore")
                        .build();
            default:
            case Down:
                return new ItemBuilder(STICK_DOWN)
                        .setName("§cDole")
                        .build();
        }
    }

    /**
     * Do not use this method to open the inventory, use show(Player) instead.
     */
    @NotNull
    @Override
    public Inventory getInventory()
    {
        return this.inv;
    }

    public int getSize()
    {
        return this.inv.getSize();
    }

    private static final int DefaultColumns = 9;

    public enum Type
    {
        Chest1(1, new InventoryCreateFunc()
        {
            @Override
            Inventory createInventory(@NotNull InventoryHolder holder, @NotNull String title)
            {
                return Bukkit.createInventory(holder, 9 * 1, title);
            }
        }),
        Chest2(2, new InventoryCreateFunc()
        {
            @Override
            Inventory createInventory(@NotNull InventoryHolder holder, @NotNull String title)
            {
                return Bukkit.createInventory(holder, 9 * 2, title);
            }
        }),
        Chest3(3, new InventoryCreateFunc()
        {
            @Override
            Inventory createInventory(@NotNull InventoryHolder holder, @NotNull String title)
            {
                return Bukkit.createInventory(holder, 9 * 3, title);
            }
        }),
        Chest4(4, new InventoryCreateFunc()
        {
            @Override
            Inventory createInventory(@NotNull InventoryHolder holder, @NotNull String title)
            {
                return Bukkit.createInventory(holder, 9 * 4, title);
            }
        }),
        Chest5(5, new InventoryCreateFunc()
        {
            @Override
            Inventory createInventory(@NotNull InventoryHolder holder, @NotNull String title)
            {
                return Bukkit.createInventory(holder, 9 * 5, title);
            }
        }),
        Chest6(6, new InventoryCreateFunc()
        {
            @Override
            Inventory createInventory(@NotNull InventoryHolder holder, @NotNull String title)
            {
                return Bukkit.createInventory(holder, 9 * 6, title);
            }
        }),
        Hopper(5, 1, new InventoryCreateFunc()
        {
            @Override
            Inventory createInventory(@NotNull InventoryHolder holder, @NotNull String title)
            {
                return Bukkit.createInventory(holder, InventoryType.HOPPER, title);
            }
        }),
        Dispenser(9, 1, new InventoryCreateFunc()
        {
            @Override
            Inventory createInventory(@NotNull InventoryHolder holder, @NotNull String title)
            {
                return Bukkit.createInventory(holder, InventoryType.DISPENSER, title);
            }
        });

        public final int columns;
        public final int rows;
        public final int size;
        @NotNull
        private final InventoryCreateFunc createFunc;

        Type(int columns, int rows, @NotNull InventoryCreateFunc createFunc)
        {
            this.columns = columns;
            this.rows = rows;
            this.size = columns * rows;
            this.createFunc = createFunc;
        }

        Type(int rows, @NotNull InventoryCreateFunc createFunc)
        {
            this(DefaultColumns, rows, createFunc);
        }

        public Inventory createInventory(@NotNull InventoryHolder holder, @NotNull String title)
        {
            return createFunc.createInventory(holder, title);
        }

        abstract static class InventoryCreateFunc
        {
            abstract Inventory createInventory(@NotNull InventoryHolder holder, @NotNull String title);
        }

        @NotNull
        public static Type fromInventory(@NotNull Inventory inventory)
        {
            switch (inventory.getType())
            {
                case CHEST:
                    switch (inventory.getSize())
                    {
                        case 9 * 1:
                            return Type.Chest1;
                        case 9 * 2:
                            return Type.Chest2;
                        case 9 * 3:
                            return Type.Chest3;
                        case 9 * 4:
                            return Type.Chest4;
                        case 9 * 5:
                            return Type.Chest5;
                        case 9 * 6:
                            return Type.Chest6;
                        default:
                            throw new IllegalArgumentException("Chest inventory has invalid size");
                    }
                case DISPENSER:
                case DROPPER:
                    return Type.Dispenser;
                case HOPPER:
                    return Type.Hopper;
                default:
                    throw new IllegalArgumentException("Inventory is not of supported type");
            }
        }
    }

    protected abstract void onOpen(@NotNull Player player);

    protected abstract void onClose(@NotNull Player player);

    @NotNull
    private final List<Player> viewingPlayers = new ArrayList<>();

    public boolean hasViewingPlayers()
    {
        return !viewingPlayers.isEmpty();
    }

    public int getViewingPlayerCount()
    {
        return viewingPlayers.size();
    }

    @NotNull
    public List<Player> getViewingPlayers()
    {
        return new ArrayList<>(viewingPlayers);
    }

    public void open(@NotNull Player player)
    {
        if(viewingPlayers.contains(player)) {
            return;
        }

        if (viewingPlayers.size() == 0)
            Bukkit.getPluginManager().registerEvents(this, WestLand.getInstance());

        player.openInventory(inv);

        if (!viewingPlayers.contains(player))
        {
            viewingPlayers.add(player);
            onOpen(player);
        }
    }

    public void close(WLPlayer wlPlayer) {
        this.close(wlPlayer.getPlayer());
    }

    public void close(@NotNull Player player)
    {
        InventoryView pInventory = player.getOpenInventory();
        if (this == pInventory.getTopInventory().getHolder())
            player.closeInventory();
    }

    public void closeAll()
    {
        for (Player player : new ArrayList<>(viewingPlayers))
            close(player);
    }

    protected void removePlayer(@NotNull Player player)
    {
        viewingPlayers.remove(player);

        if (viewingPlayers.size() == 0)
            HandlerList.unregisterAll(this);
    }

    // Must be public
    @EventHandler
    public void onCloseEvent(InventoryCloseEvent event)
    {
        if (event.getInventory().getHolder() != this)
            return;
        HumanEntity entity = event.getPlayer();
        if (!(entity instanceof Player))
            return;
        Player player = (Player) entity;

        removePlayer(player);
        onClose(player);
    }

    // Must be public
    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event)
    {
        removePlayer(event.getPlayer());
    }

    protected void addItem(ItemStack... itemStacks) {
        this.getInventory().addItem(itemStacks);
    }

    protected void setItemsRange(int startIndex, int length, @NotNull Material material) {
        Inventory inv = getInventory();
        for (int i = startIndex; i < startIndex + length && i < getSize(); i++)
            inv.setItem(i, new ItemStack(material, 1));
    }

    protected void setItemsRange(int startIndex, int length, ItemStack itemStack) {
        Inventory inv = getInventory();
        for (int i = startIndex; i < startIndex + length && i < getSize(); i++)
            inv.setItem(i, itemStack);
    }

    protected void setItemsRangeHorizontal(int x, int startIndex, int length, @NotNull Material material) {
        Inventory inv = getInventory();
        for (int i = startIndex; i < startIndex + length; i++)
            inv.setItem(x + (i * getType().columns), new ItemStack(material, 1));
    }

    protected void setItemsRangeHorizontal(int x, int startIndex, int length, ItemStack itemStack) {
        Inventory inv = getInventory();
        for (int i = startIndex; i < startIndex + length; i++)
            inv.setItem(x + (i * getType().columns), itemStack);
    }

    protected void setItem(int x, int y, Material material) {
        setItem(x, y, new ItemStack(material, 1));
    }

    protected void setItem(int x, int y, ItemStack itemStack) {
        inv.setItem(x + (y * getType().columns), itemStack);
    }

    protected void setItem(int pos, ItemStack itemStack) {
        inv.setItem(pos, itemStack);
    }

    protected void setItemWithOffset(int xOff, int yOff, int index, ItemStack itemStack) {
        int x = xOff + (index % (getType().columns - xOff * 2));
        int y = yOff + (index / (getType().rows));
        inv.setItem(x + (y * getType().columns), itemStack);
    }

    protected void setItemWithOffset(int xOff, int yOff, int x, int y, ItemStack itemStack) {
        inv.setItem((x + xOff) + ((y + yOff) * getType().columns), itemStack);
    }

    @NotNull
    public static ItemStack getItemOrDefault(@Nullable ItemStack is, @NotNull ItemStack defaultItem) {
        return (is == null || is.getType() == Material.AIR) ? defaultItem : is;
    }

    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        closeAll();
    }

    protected void setItemCloseInventory() { setItemCloseInventory(4, type.rows - 1); }
    protected void setItemCloseInventory(int y) { setItemCloseInventory(4, y); }
    protected void setItemCloseInventory(int x, int y) { setItem(x, y, CLOSE_INVENTORY_ITEM); }
}
