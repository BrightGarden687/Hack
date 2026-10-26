package lot.hack.event.events.game;

import lot.hack.event.Event;

public class EventGameLoop implements Event {
    public static final EventGameLoop INSTANCE = new EventGameLoop();

    private EventGameLoop() {}
}
