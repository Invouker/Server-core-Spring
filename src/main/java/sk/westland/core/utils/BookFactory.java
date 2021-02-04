package sk.westland.core.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import sk.westland.core.entity.player.WLPlayer;
import xyz.upperlevel.spigot.book.BookUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BookFactory {

    private ItemStack itemStack;
    private BookMeta bookMeta;

    public BookFactory() {
        this.itemStack = new ItemStack(Material.WRITTEN_BOOK);
        this.bookMeta = (BookMeta) this.itemStack.getItemMeta();
    }

    public BookFactory title(@Nullable String title) {
        this.bookMeta.setTitle(title);
        return this;
    }

    public BookFactory author(@Nullable String author) {
        this.bookMeta.setAuthor(author);
        return this;
    }

    @Deprecated
    public BookFactory addPage(@NotNull BookPageFactory pageFactory) {
        this.bookMeta.spigot().addPage(pageFactory.build());
        return this;
    }

    public BookFactory addPage(@NotNull BookPageFactory pageFactory, @NotNull Player context) {
        this.bookMeta.spigot().addPage(pageFactory.build(context));
        return this;
    }

    public BookFactory addPage(@NotNull BookPageFactory pageFactory, @NotNull WLPlayer context) {
        return addPage(pageFactory, context.getPlayer());
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.bookMeta);
        return this.itemStack;
    }

    public void open(Player player) {
        BookUtil.openPlayer(player, build());
    }

    public void open(WLPlayer player) {
        BookUtil.openPlayer(player.getPlayer(), build());
    }

}
