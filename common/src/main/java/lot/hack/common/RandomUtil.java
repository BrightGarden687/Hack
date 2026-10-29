package lot.hack.common;

import java.util.Random;

public class RandomUtil {
    private static final Random RANDOM = new Random();

    public static int random(int start, int end) {
        return RANDOM.nextInt(start, end);
    }

    public static int random(IntRange range) {
        return random(range.getStart(), range.getEnd());
    }

    public static float random(float start, float end) {
        return RANDOM.nextFloat(start, end);
    }

    public static float random(FloatRange range) {
        return random(range.getStart(), range.getEnd());
    }
}
