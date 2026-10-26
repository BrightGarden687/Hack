package lot.hack.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lot.hack.rotation.CameraRotation;
import lot.hack.rotation.RotateUtil;

@Mixin(Entity.class)
public class MixinEntity {

    @Shadow
    private float xRot;

    @Shadow
    private float yRot;

    @Inject(at = @At("HEAD"), method = "turn", cancellable = true)
    private void unlockCamera(double xo, double yo, CallbackInfo ci) {
        if ((Object) this != Minecraft.getInstance().player)
            return;

        float xDelta = (float) yo * 0.15F;
        float yDelta = (float) xo * 0.15F;

        CameraRotation camera = CameraRotation.INSTANCE;

        if (camera.isUnlocked()) {
            camera.add(xDelta, yDelta).clampPitch(-90, 90);
            ci.cancel();
        }
    }

    @Inject(at = @At("TAIL"), method = "turn")
    private void setCameraRotation(double xo, double yo, CallbackInfo ci) {
        CameraRotation camera = CameraRotation.INSTANCE;

        if (!camera.isUnlocked()) {
            camera.pitch(xRot);
            camera.yaw(camera.yaw() + RotateUtil.wrap(yRot - camera.yaw()));
        }
    }
}
