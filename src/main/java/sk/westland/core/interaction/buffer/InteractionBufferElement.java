package sk.westland.core.interaction.buffer;

import sk.westland.core.event.player.WLPlayerInteractWithNPCEvent;

import java.util.function.Consumer;

public class InteractionBufferElement {

    private String name;
    private String tooltip;
    private Consumer<WLPlayerInteractWithNPCEvent> eventConsumer;
    private WLPlayerInteractWithNPCEvent event;

    public InteractionBufferElement(String name, String tooltip, WLPlayerInteractWithNPCEvent event, Consumer<WLPlayerInteractWithNPCEvent> eventConsumer) {
        this.name = name;
        this.tooltip = tooltip;
        this.event = event;
        this.eventConsumer = eventConsumer;
    }

    public String getName() {
        return name;
    }

    public String getTooltip() {
        return tooltip;
    }

    public Object getEvent() {
        return event;
    }

    public Consumer<WLPlayerInteractWithNPCEvent> getEventConsumer() {
        return eventConsumer;
    }

    public void fire() {
        eventConsumer.accept(event);
    }
}
