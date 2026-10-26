package lot.hack.keybind;

import java.util.function.Consumer;

public class Keybind {
    public final int key;
    public final Consumer<Integer> action;

    public Keybind(int key, Consumer<Integer> action) {
        this.key = key;
        this.action = action;
    }
}
