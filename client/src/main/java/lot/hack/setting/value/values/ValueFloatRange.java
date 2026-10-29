package lot.hack.setting.value.values;

import lot.hack.common.FloatRange;
import lot.hack.setting.value.Value;

public class ValueFloatRange extends Value<FloatRange> {
    private final float min, max, step;

    public ValueFloatRange(String name, FloatRange defaultValue, float min, float max, float step) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public float getStep() {
        return step;
    }
}
