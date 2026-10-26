package lot.hack.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import lot.hack.rotation.CameraRotation;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FirstPersonHandsAndItemsRenderer.class)
public class MixinFirstPersonHandsAndItemsRenderer {
    @ModifyExpressionValue(
        method = "submitHandsWithItems", 
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/level/FirstPersonHandsAndItemsRenderState;viewXRot:F")
    )
    private float modifyRenderX(float original) {
        return CameraRotation.INSTANCE.pitch();
    }

    @ModifyExpressionValue(
        method = "submitHandsWithItems", 
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/level/FirstPersonHandsAndItemsRenderState;viewYRot:F")
    )
    private float modifyRenderY(float original) {
        return CameraRotation.INSTANCE.yaw();
    }

    @ModifyExpressionValue(
        method = "submitHandsWithItems", 
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;xRot:F")
    )
    private float modifyX(float original) {
        return CameraRotation.INSTANCE.pitch();
    }
}
