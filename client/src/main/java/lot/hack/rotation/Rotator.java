package lot.hack.rotation;

public interface Rotator {
    public boolean shouldRotate();
    default void rotate() {}
}
