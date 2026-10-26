package lot.hack.keybind.keybinds;


import lot.hack.keybind.Keybind;
import lot.hack.utils.MCInstances;
import net.minecraft.client.gui.screens.Screen;

public class GUIKeybind extends Keybind implements MCInstances {
    public GUIKeybind(int key, Screen screen) {
        super(key, action -> {
            if (action == 2)
                return;
            
            if (action != 1)
                mc.setScreenAndShow(screen);
        });
    }
}
