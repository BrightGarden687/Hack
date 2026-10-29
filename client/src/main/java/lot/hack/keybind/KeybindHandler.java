package lot.hack.keybind;

import java.util.ArrayList;
import java.util.List;

import lot.hack.common.Initializable;
import lot.hack.event.Event;
import lot.hack.event.EventListener;
import lot.hack.event.EventListenersCaller;
import lot.hack.event.events.input.EventKey;
import lot.hack.utils.MCInstances;

public class KeybindHandler implements Initializable, MCInstances, EventListener {
    public static final KeybindHandler INSTANCE = new KeybindHandler();

    private final List<Keybind> keybinds = new ArrayList<>();

    private KeybindHandler() {
        EventListenersCaller.INSTANCE.register(this);
    }

    public void register(Keybind keybind) {
        keybinds.add(keybind);
    }

    @Override
    public void onEvent(Event event) {
        if (event != EventKey.INSTANCE)
            return;

        for (Keybind keybind : keybinds) {
            if (keybind.key == EventKey.INSTANCE.getKey())
                keybind.action.accept(EventKey.INSTANCE.getAction());
        }
    }

    @Override
    public boolean shouldListenEvents() {
        return isInGame();
    }
}
