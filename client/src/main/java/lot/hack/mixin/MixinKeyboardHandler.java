package lot.hack.mixin;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import lot.hack.event.events.input.EventKey;

@Mixin(KeyboardHandler.class)
public class MixinKeyboardHandler {
    @Inject(method = "keyPress", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/FramerateLimitTracker;onInputReceived()V", shift = At.Shift.AFTER))
    private void callKeyEvent(long handle, int action, KeyEvent event, CallbackInfo ci) {
        if (Minecraft.getInstance().gui.screen() != null)
            return;

        EventKey keyEvent = EventKey.INSTANCE;
        keyEvent.set(event.key(), action);
        keyEvent.call();
    }
}
