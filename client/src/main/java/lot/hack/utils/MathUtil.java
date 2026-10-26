package lot.hack.utils;

public class MathUtil {
    public static float lerp(float value, float min, float max) {
        return (max - min) * value + min;
    }
}
