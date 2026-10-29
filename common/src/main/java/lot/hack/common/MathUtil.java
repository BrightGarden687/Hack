package lot.hack.common;

public class MathUtil {
    public static float lerp(float value, float min, float max) {
        return (max - min) * value + min;
    }

    public static float map(float value, float valueMin, float valueMax, float outMin, float outMax) {
        return lerp((value - valueMin) / (valueMax - valueMin), outMin, outMax);
    }
}
