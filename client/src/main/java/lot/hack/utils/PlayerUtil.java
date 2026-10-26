package lot.hack.utils;

import lot.hack.rotation.Rotation;
import net.minecraft.client.player.LocalPlayer;

public class PlayerUtil implements MCInstances {
    public static void rotate(Rotation rotation) {
        LocalPlayer localPlayer = mc.player;
        if (localPlayer == null) return;
        localPlayer.setXRot(localPlayer.getXRot() + rotation.pitch());
        localPlayer.setYRot(localPlayer.getYRot() + rotation.yaw());
    }

    public static Rotation rotation() {
        LocalPlayer localPlayer = mc.player;
        if (localPlayer == null) throw new NullPointerException();
        return new Rotation(localPlayer.getXRot(), localPlayer.getYRot());
    }
}
