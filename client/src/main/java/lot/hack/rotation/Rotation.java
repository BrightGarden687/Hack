package lot.hack.rotation;

public class Rotation {
    public float pitch = 0;
    private float yaw = 0;

    public Rotation(float pitch, float yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Rotation() {}

    public float pitch() {
        return pitch;
    }

    public float yaw() {
        return yaw;
    }

    public void pitch(float pitch) {
        this.pitch = pitch;
    }

    public void yaw(float yaw) {
        this.yaw = yaw;
    }

    public Rotation add(Rotation rotation) {
        pitch += rotation.pitch();
        yaw += rotation.yaw();
        return this;
    }

    public Rotation add(float pitch, float yaw) {
        this.pitch += pitch;
        this.yaw += yaw;
        return this;
    }

    public Rotation added(Rotation rotation) {
        return new Rotation(pitch + rotation.pitch(), yaw + rotation.yaw());
    }

    public Rotation substact(Rotation rotation) {
        pitch -= rotation.pitch();
        yaw -= rotation.yaw();
        return this;
    }

    public Rotation substacted(Rotation rotation) {
        return new Rotation(pitch - rotation.pitch(), yaw - rotation.yaw());
    }

    public Rotation multiply(Rotation rotation) {
        pitch *= rotation.pitch();
        yaw *= rotation.yaw();
        return this;
    }

    public Rotation multiplid(Rotation rotation) {
        return new Rotation(pitch * rotation.pitch(), yaw * rotation.yaw());
    }

    public Rotation multiply(float factor) {
        pitch *= factor;
        yaw *= factor;
        return this;
    }

    public Rotation multiplid(float factor) {
        return new Rotation(pitch * factor, yaw * factor);
    }

    public Rotation divide(Rotation rotation) {
        pitch /= rotation.pitch();
        yaw /= rotation.yaw();
        return this;
    }

    public Rotation divided(Rotation rotation) {
        return new Rotation(pitch / rotation.pitch(), yaw / rotation.yaw());
    }

    public Rotation divide(float factor) {
        pitch /= factor;
        yaw /= factor;
        return this;
    }

    public Rotation divided(float factor) {
        return new Rotation(pitch / factor, yaw / factor);
    }

    public Rotation minus() {
        pitch = -pitch;
        yaw = -yaw;
        return this;
    }

    public double length() {
        return Math.hypot(pitch, yaw);
    }

    public float floatLength() {
        return (float) Math.hypot(pitch, yaw);
    }

    public Rotation direction() {
        if (length() == 0) return this;
        return divided(floatLength());
    }

    public Rotation clampPitch(float min, float max) {
        pitch = pitch < min ? min : (pitch > max ? max : pitch);
        return this;
    }
}