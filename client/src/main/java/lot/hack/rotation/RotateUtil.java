package lot.hack.rotation;

import net.minecraft.world.phys.Vec3;

public class RotateUtil {
    public static float wrap(float angle) {
        angle = (angle + 180) % 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle - 180;
    }

    public static float pitchTo(Vec3 point) {
        return (float) -Math.toDegrees(Math.atan2(point.y, point.horizontalDistance()));
    }

    public static float yawTo(Vec3 point) {
        return wrap((float) Math.toDegrees(Math.atan2(point.z, point.x)) - 90f);
    }

    public static Rotation rotationTo(Vec3 point) {
        return new Rotation(pitchTo(point), yawTo(point));
    }
}
