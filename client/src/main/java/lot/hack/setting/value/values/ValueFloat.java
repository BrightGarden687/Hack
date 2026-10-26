package lot.hack.setting.value.values;

import lot.hack.setting.value.Value;

public class ValueFloat extends Value<Float> {
    private final float min, max, step;

    public ValueFloat(String name, float defaultValue, float min, float max, float step) {
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
