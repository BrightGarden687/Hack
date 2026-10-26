package lot.hack.utils;

import java.util.ArrayList;
import java.util.List;

import lot.hack.setting.value.values.Configurable;
import lot.hack.setting.value.values.ToggleableConfigurable;
import lot.hack.setting.value.values.ValueBoolean;
import lot.hack.setting.value.values.ValueFloat;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class TargetFinder extends Configurable implements MCInstances {
    private final List<LivingEntity> candidate = new ArrayList<>();
    private LivingEntity target = null;

    public final ToggleableConfigurable targetLock = toggleableGroup("TargetLock", false);
    public final ValueFloat lockRange = targetLock.floatValue("Range", 6, 0, 20, 0.1f);
    public final ValueFloat searchRange = floatValue("SearchRange", 6, 0, 20, 0.1f);
    public final ValueBoolean targetOnDying = booleanValue("TargetOnDying", false);

    public TargetFinder() {
        super("TargetFinder");
    }

    public LivingEntity getTarget() {
        return target;
    }

    public void resetTarget() {
        target = null;
    }

    public void updateTarget() {
        if (target != null && targetLock.isToggled()) {
            if (localPlayer().distanceTo(target) <= lockRange.get())
                return;
        }

        candidate.clear();
        target = null;

        for (Entity entity : clientLevel().entitiesForRendering()) {
            if (
                entity == null ||
                entity == localPlayer() || 
                localPlayer().distanceTo(entity) > searchRange.get()
            ) continue;

            if (entity instanceof LivingEntity livingEntity && (!livingEntity.isDeadOrDying() || targetOnDying.get())) {
                candidate.add(livingEntity);
            }
        }

        if (candidate.isEmpty()) return;
        target = candidate.getFirst();
    }
}
