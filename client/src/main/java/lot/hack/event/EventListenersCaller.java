package lot.hack.event;

import java.util.ArrayList;
import java.util.List;

import lot.hack.utils.Initializable;

public class EventListenersCaller implements Initializable {
    public static final EventListenersCaller INSTANCE = new EventListenersCaller();

    private List<EventListener> eventListeners = new ArrayList<>();

    private EventListenersCaller() {}

    public void register(EventListener eventListener) {
        eventListeners.add(eventListener);
    }

    public void call(Event event) {
        for (EventListener eventListener : eventListeners) {
            if (eventListener.shouldListenEvents()) {
                eventListener.onEvent(event);
            }
        }
    }
}
