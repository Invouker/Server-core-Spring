package sk.westland.core.interaction.buffer;


import sk.westland.core.utils.HashListMap;

import java.util.LinkedList;

public class InteractionBuffer extends HashListMap<Object, InteractionBufferElement, LinkedList<InteractionBufferElement>> {

    public InteractionBuffer() {
    }
}
