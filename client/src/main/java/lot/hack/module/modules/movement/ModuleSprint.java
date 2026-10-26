package lot.hack.module.modules.movement;

import lot.hack.module.ClientModule;
import lot.hack.module.ModuleCategory;
import lot.hack.setting.value.values.ValueBoolean;

public class ModuleSprint extends ClientModule {
    public static final ModuleSprint INSTANCE = new ModuleSprint();

    public ValueBoolean auto = booleanValue("Auto", true);
    public ValueBoolean ignoreHunger = booleanValue("IgnoreHunger", false);
    
    private ModuleSprint() {
        super("Sprint", true, ModuleCategory.MOVEMENT);
    }

    @Override
    public boolean shouldListenEvents() {
        return false;
    }
}
