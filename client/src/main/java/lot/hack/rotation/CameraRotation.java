package lot.hack.rotation;

public class CameraRotation extends Rotation {
    public static final CameraRotation INSTANCE = new CameraRotation();

    private boolean unlocked = false;

    private CameraRotation() {
        super();
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
