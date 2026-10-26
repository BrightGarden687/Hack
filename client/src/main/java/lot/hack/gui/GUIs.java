package lot.hack.gui;

import lot.hack.gui.clientsettings.ScreenClientSettings;
import lot.hack.utils.Initializable;

public class GUIs implements Initializable {
    public static final GUIs INSTANCE = new GUIs();

    public static final ScreenClientSettings CLIENT_SETTINGS = ScreenClientSettings.INSTANCE;

    private GUIs() {}
}
