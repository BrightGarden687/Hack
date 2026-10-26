package lot.hack.utils;

public class FloatRange {
    private float min, max;

    public FloatRange(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public boolean in(float value) {
        return value >= min && value <= max;
    }
}
