package lot.hack.common;

public class FloatRange {
    private float start, end;

    public FloatRange(float start, float end) {
        this.start = start;
        this.end = end;
    }

    public float getStart() {
        return start;
    }

    public float getEnd() {
        return end;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public boolean in(float value) {
        return value >= start && value <= end;
    }
}
