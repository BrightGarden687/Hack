package lot.hack.gui;

import lot.hack.common.Initializable;

public class GUIs implements Initializable {
    public static final GUIs INSTANCE = new GUIs();

    public static final ScreenModuleSettings MODULE_SETTINGS = ScreenModuleSettings.INSTANCE;

    private GUIs() {}
}
