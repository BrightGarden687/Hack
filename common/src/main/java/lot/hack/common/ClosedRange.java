package lot.hack.common;

public class ClosedRange<T extends Comparable<T>> {
    private T min, max;

    public ClosedRange(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public boolean in(T value) {
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }
}

