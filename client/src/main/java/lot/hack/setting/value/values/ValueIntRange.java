package lot.hack.setting.value.values;

import lot.hack.common.IntRange;
import lot.hack.setting.value.Value;

public class ValueIntRange extends Value<IntRange> {
    private final int min, max;

    public ValueIntRange(String name, IntRange defaultValue, int min, int max) {
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
