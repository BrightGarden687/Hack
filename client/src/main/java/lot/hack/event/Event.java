package lot.hack.event;

public interface Event {
    default void call() {
        EventListenersCaller.INSTANCE.call(this);
    }
}