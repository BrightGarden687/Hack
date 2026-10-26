package lot.hack.keybind.keybinds;

import java.util.function.Consumer;

import lot.hack.keybind.Keybind;
import lot.hack.module.ClientModule;

public class ModuleKeybind extends Keybind {
    public final boolean hold;

    public ModuleKeybind(int key, ClientModule module, boolean hold) {
        Consumer<Integer> action;

        if (hold) {
            action = keyAction -> {
                if (keyAction == 2)
                    return;

                module.setToggled(keyAction == 1);
            };
        } else {
            action = keyAction -> {
                if (keyAction == 2)
                    return;

                if (keyAction != 1)
                    module.toggle();
            };
        }

        super(key, action);

        this.hold = hold;
    }
    
}
