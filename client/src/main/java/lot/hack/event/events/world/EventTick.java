package lot.hack.event.events.world;

import lot.hack.event.CancellableEvent;

public class EventTick extends CancellableEvent {
    public static final EventTick INSTANCE = new EventTick();

    private EventTick() {}
}
