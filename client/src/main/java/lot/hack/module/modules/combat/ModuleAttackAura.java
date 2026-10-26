package lot.hack.module.modules.combat;

import com.mojang.blaze3d.platform.InputConstants;

import lot.hack.event.Event;
import lot.hack.event.events.world.EventTick;
import lot.hack.keybind.KeybindHandler;
import lot.hack.keybind.keybinds.ModuleKeybind;
import lot.hack.module.ClientModule;
import lot.hack.module.ModuleCategory;
import lot.hack.rotation.RotateUtil;
import lot.hack.rotation.Rotation;
import lot.hack.rotation.Rotator;
import lot.hack.utils.PlayerUtil;
import lot.hack.utils.TargetFinder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import lot.hack.rotation.RotateCaller;

public class ModuleAttackAura extends ClientModule implements Rotator {
    public static final ModuleAttackAura INSTANCE = new ModuleAttackAura();

    private final TargetFinder targetFinder = tree(new TargetFinder());

    private ModuleAttackAura() {
        super("AttackAura", false, ModuleCategory.COMBAT);
        KeybindHandler.INSTANCE.register(new ModuleKeybind(InputConstants.KEY_E, this, false));
        RotateCaller.INSTANCE.register(this);
    }

    @Override
    public void onDisable() {
        targetFinder.resetTarget();
    }

    @Override
    public void onEvent(Event event) {
        if (event == EventTick.INSTANCE) {
            targetFinder.updateTarget();
        }
    }

    @Override
    public boolean shouldRotate() {
        return shouldListenEvents() && target() != null;
    }
 
    @Override
    public void rotate() {
        Vec3 point = target().getEyePosition().subtract(localPlayer().getEyePosition());
        Rotation delta = RotateUtil.rotationTo(point).substact(PlayerUtil.rotation());
        PlayerUtil.rotate(delta);
    }

    public LivingEntity target() {
        return targetFinder.getTarget();
    }
}
