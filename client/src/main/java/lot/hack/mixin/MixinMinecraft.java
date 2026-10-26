package lot.hack.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lot.hack.event.events.game.EventGameLoop;
import lot.hack.event.events.world.EventTick;
import lot.hack.rotation.RotateCaller;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void callTickEvent(CallbackInfo ci) {
		EventTick.INSTANCE.call();
		RotateCaller.INSTANCE.rotate();

		if (EventTick.INSTANCE.isCancelled()) 
			ci.cancel();
	}

	@Inject(method = "runTick", at = @At("HEAD"))
	private void callGameLoopEvent(CallbackInfo ci) {
		EventGameLoop.INSTANCE.call();
	}
}