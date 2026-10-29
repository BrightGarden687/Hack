package lot.hack.common;

public enum Align {
    TOP_LEFT     (-1, -1),
    TOP_CENTER   ( 0, -1),
    TOP_RIGHT    ( 1, -1),
    CENTER_RIGHT ( 1,  0),
    BOTTOM_RIGHT ( 1,  1),
    BOTTOM_CENTER( 0,  1),
    BOTTOM_LEFT  (-1,  1),
    CENTER_LEFT  (-1,  0),
    CENTER       ( 0,  0);

    public final int x, y;

    Align(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float xOffset(float outsideW, float insideW) {
        return MathUtil.map(x, -1, 1, 0, outsideW - insideW);
    }

    public float yOffset(float outsideH, float insideH) {
        return MathUtil.map(y, -1, 1, 0, outsideH - insideH);
    }
}
