package lot.hack.setting.value.values;

import lot.hack.setting.value.Toggleable;

public class ToggleableConfigurable extends Configurable implements Toggleable {
    private boolean toggled;

    public ToggleableConfigurable(String name, boolean defaultToggled) {
        super(name);
        setToggled(defaultToggled);
    }

    @Override
    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        Toggleable.super.setToggled(toggled);
        this.toggled = toggled;
    }
}
