package lot.hack.event;

public class CancellableEvent implements Event {
    private boolean cancelled = false;

    public void cancel() {
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void call() {
        cancelled = false;
        Event.super.call();
    }
}