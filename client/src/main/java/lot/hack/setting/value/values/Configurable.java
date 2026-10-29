package lot.hack.setting.value.values;

import java.util.ArrayList;
import java.util.List;

import lot.hack.common.FloatRange;
import lot.hack.common.IntRange;
import lot.hack.setting.value.Value;

public class Configurable extends Value<List<Value<?>>> {
    public Configurable(String name) {
        super(name, new ArrayList<>());
    }

    public ValueBoolean booleanValue(String name, boolean defaultValue) {
        ValueBoolean value = new ValueBoolean(name, defaultValue);
        this.value.add(value);
        return value;
    }

    public ValueInt intValue(String name, Integer defaultValue, Integer min, Integer max) {
        ValueInt value = new ValueInt(name, defaultValue, min, max);
        this.value.add(value);
        return value;
    }

    public ValueIntRange intRangeValue(String name, int defaultStart, int defaultEnd, int min, int max) {
        ValueIntRange value = new ValueIntRange(name, new IntRange(defaultStart, defaultEnd), min, max);
        this.value.add(value);
        return value;
    }

    public ValueFloat floatValue(String name, float defaultValue, float min, float max, float step) {
        ValueFloat value = new ValueFloat(name, defaultValue, min, max, step);
        this.value.add(value);
        return value;
    }

    public ValueFloatRange floatRangeValue(String name, float defaultStart, float defaultEnd, float min, float max, float step) {
        ValueFloatRange value = new ValueFloatRange(name, new FloatRange(defaultStart, defaultEnd), min, max, step);
        this.value.add(value);
        return value;
    }

    public ValueMode modeValue(String name) {
        ValueMode value = new ValueMode(name);
        this.value.add(value);
        return value;
    }

    public Configurable group(String name) {
        Configurable value = new Configurable(name);
        this.value.add(value);
        return value;
    }

    public ToggleableConfigurable toggleableGroup(String name, boolean defaultToggled) {
        ToggleableConfigurable value = new ToggleableConfigurable(name, defaultToggled);
        this.value.add(value);
        return value;
    }

    public <E extends Configurable> E tree(E configurable) {
        value.add(configurable);
        return configurable;
    }
}
