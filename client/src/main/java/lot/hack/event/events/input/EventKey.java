package lot.hack.event.events.input;

import lot.hack.event.Event;

public class EventKey implements Event {
    public static final EventKey INSTANCE = new EventKey();

    private int key;
    private int action;

    private EventKey() {}

    public int getKey() {
        return key;
    }

    public int getAction() {
        return action;
    }

    public void set(int key, int action) {
        this.key = key;
        this.action = action;
    }
}
