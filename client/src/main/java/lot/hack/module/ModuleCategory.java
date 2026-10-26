package lot.hack.module;

import java.util.ArrayList;
import java.util.List;

public enum ModuleCategory {
    COMBAT("Combat"),
    MISC("Misc"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    VISUAL("Visual"),
    WORLD("World");

    public final String name;
    private final List<ClientModule> modules = new ArrayList<>();

    ModuleCategory(String name) {
        this.name = name;
    }

    public void register(ClientModule clientModule) {
        modules.add(clientModule);
    }
}
