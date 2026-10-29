package lot.hack.module;

import lot.hack.common.Initializable;
import lot.hack.event.EventListener;
import lot.hack.event.EventListenersCaller;
import lot.hack.setting.value.values.ToggleableConfigurable;
import lot.hack.utils.MCInstances;

public abstract class ClientModule extends ToggleableConfigurable implements Initializable, MCInstances, EventListener {
    protected ClientModule(String name, boolean toggled, ModuleCategory category) {
        super(name, toggled);
        EventListenersCaller.INSTANCE.register(this);
        Modules.INSTANCE.add(this);
        category.register(this);
    }

    @Override
    public boolean shouldListenEvents() {
        return isInGame() && isToggled();
    }
}
