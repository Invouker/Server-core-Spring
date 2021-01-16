package sk.westland.core.services;

import dev.alangomes.springspigot.context.Context;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine;
import sk.westland.core.ComponentBuilder;
import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;
import sk.westland.core.interaction.buffer.InteractionBuffer;
import sk.westland.core.interaction.buffer.InteractionBufferElement;
import sk.westland.core.player.WLPlayer;
import sk.westland.core.utils.BookPageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@CommandLine.Command(name = "select_interaction")
@Service
public class InteractionService implements Listener, Runnable {

    @Autowired
    private PlayerService playerService;

    private InteractionBuffer interactionBuffer = new InteractionBuffer();

    private Map<Player, HashMap<UUID, InteractionBufferElement>> playerInstanceHashMapMap = new HashMap<>();

    public <T> boolean requestInteractionHandler(String npcName, String name, String tooltip, Class<T> baseType, T event, Consumer<T> onEventReceived) {
        if(npcName == null || !(event instanceof WLPlayerInteractWithNPCEvent)) {
            onEventReceived.accept(event);
            return true;
        }

        WLPlayerInteractWithNPCEvent npcEvent = (WLPlayerInteractWithNPCEvent) event;

        if(!npcEvent.getNPCName().endsWith(ChatColor.stripColor(npcName)))
            return false;

        InteractionBufferElement interactionBufferElement = new InteractionBufferElement(name, tooltip, npcEvent, (Consumer<WLPlayerInteractWithNPCEvent>)onEventReceived);
        interactionBufferElement.fire();

        interactionBuffer.putToList(event, interactionBufferElement);

        return false;
    }

    @SuppressWarnings("unchecked")
    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerInteractWithEntity(WLPlayerInteractWithNPCEvent event) {
        List<InteractionBufferElement> interactionBuffers = interactionBuffer.getList(event);

        if(interactionBuffers == null) {
            //Bukkit.getLogger().warning("InteractionBufferElement not found for " + event.getNPCName());
            return;
        }

        if(interactionBuffers.size() == 1) {
            interactionBuffers.get(0).fire();
            interactionBuffer.removeList(event);
            return;
        }

        BookPageFactory pageFactory = BookPageFactory.create();
        pageFactory.appendText("Vyber akci:");
        pageFactory.newLine();

        HashMap<UUID, InteractionBufferElement> bufferElementMap = new HashMap<>();

        for(InteractionBufferElement element : interactionBuffers) {
            UUID uuid = UUID.randomUUID();
            bufferElementMap.put(uuid, element);
            pageFactory.newLineAction(ComponentBuilder.text(ChatColor.DARK_GREEN + "[" + element.getName() + "]").build(), ClickEvent.Action.RUN_COMMAND, "/select_interaction " + uuid, ComponentBuilder.text(ChatColor.GOLD + element.getTooltip()).build());
        }

        playerInstanceHashMapMap.put(event.getWLPlayer().getPlayer(), bufferElementMap);

        ItemStack bookStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) bookStack.getItemMeta();
        meta.setTitle("Interaction");
        meta.setAuthor("MineBreak");
        meta.spigot().addPage(pageFactory.build());
        bookStack.setItemMeta(meta);

        event.getPlayer().openBook(bookStack);

        interactionBuffer.removeList(event);
    }


    ////////////////////////////////////

    @Autowired
    private Context context;

    @CommandLine.Parameters(index = "0")
    private String interaction_uuid;

    @Override
    public void run() {
        if(!(context.getSender() instanceof Player)) {
            return;
        }

        WLPlayer wlPlayer = playerService.getWLPlayer(context.getPlayer());

        if(wlPlayer == null) {
            return;
        }

        HashMap<UUID, InteractionBufferElement> bufferElementMap = playerInstanceHashMapMap.getOrDefault(wlPlayer.getPlayer(), null);

        if(bufferElementMap == null) {
            return;
        }

        UUID uuid = UUID.fromString(interaction_uuid);

        InteractionBufferElement bufferElement = bufferElementMap.getOrDefault(uuid, null);

        if(bufferElement == null) {
            return;
        }

        playerInstanceHashMapMap.remove(wlPlayer.getPlayer());

        bufferElement.fire();
    }
}

