package lot.hack.common;

public class IntRange {
    private int start, end;

    public IntRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean in(int value) {
        return value >= start && value <= end;
    }
}
