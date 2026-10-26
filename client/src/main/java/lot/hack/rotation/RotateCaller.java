package lot.hack.rotation;

import java.util.ArrayList;
import java.util.List;

import lot.hack.utils.Initializable;
import lot.hack.utils.PlayerUtil;

public class RotateCaller implements Initializable {
    public static final RotateCaller INSTANCE = new RotateCaller();

    private RotateCaller() {}

    private final List<Rotator> rotators = new ArrayList<>();

    public void register(Rotator rotator) {
        rotators.add(rotator);
    }

    public void rotate() {
        CameraRotation camera = CameraRotation.INSTANCE;

        for (Rotator rotator : rotators) {
            if (!rotator.shouldRotate())
                continue;

            rotator.rotate();
            camera.setUnlocked(true);
            return;
        }

        if (!camera.isUnlocked())
            return;

        Rotation delta = PlayerUtil.rotation().substact(camera).minus();

        if (delta.length() == 0) {
            camera.setUnlocked(false);
            return;
        }

        PlayerUtil.rotate(delta);
    }
}
