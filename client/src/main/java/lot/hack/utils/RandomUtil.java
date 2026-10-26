package lot.hack.utils;

public class RandomUtil {
    public static float random(FloatRange range) {
        return MathUtil.lerp((float) Math.random(), range.getMin(), range.getMax());
    }
}
