package lot.hack.setting.value.values;

import lot.hack.setting.value.Value;

public class ValueInt extends Value<Integer> {
    private final int min, max;

    public ValueInt(String name, Integer defaultValue, Integer min, Integer max) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
