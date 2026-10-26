package lot.hack.event;

public interface EventListener {
    public boolean shouldListenEvents();
    default void onEvent(Event event) {}
}