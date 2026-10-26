package lot.hack.setting.value.values;

import lot.hack.setting.value.Value;

public class ValueBoolean extends Value<Boolean> {
    public ValueBoolean(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    public void toggle() {
        value = !value;
    }
}
