package lot.hack.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import lot.hack.module.modules.movement.ModuleSprint;
import lot.hack.rotation.CameraRotation;
import net.minecraft.client.player.LocalPlayer;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer {
    @ModifyExpressionValue(
        method = "aiStep", 
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Input;sprint()Z"
        )
    )
    private boolean autoSprint(boolean original) {
        return original || (ModuleSprint.INSTANCE.isToggled() && ModuleSprint.INSTANCE.auto.get()); 
    }

    @ModifyExpressionValue(
        method = "isSprintingPossible(Z)Z",
        at = @At(
            value = "INVOKE", 
            target = "Lnet/minecraft/client/player/LocalPlayer;hasEnoughFoodToDoExhaustiveManoeuvres()Z"
        )
    )
    private boolean modifyFoodCheckResult(boolean originalValue) {
        LocalPlayer player = (LocalPlayer) (Object) this;

        return player.getFoodData().hasEnoughFood() || 
            player.getAbilities().mayfly || 
            (ModuleSprint.INSTANCE.isToggled() && ModuleSprint.INSTANCE.ignoreHunger.get());
    }

    @ModifyExpressionValue(method = "applyInput", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getXRot()F"))
    private float modifyRenderX(float original) {
        return CameraRotation.INSTANCE.pitch();
    }

    @ModifyExpressionValue(method = "applyInput", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getYRot()F"))
    private float modifyRenderY(float original) {
        return CameraRotation.INSTANCE.yaw();
    }
}
