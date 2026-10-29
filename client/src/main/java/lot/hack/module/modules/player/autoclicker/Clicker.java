package lot.hack.module.modules.player.autoclicker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

import lot.hack.common.RandomUtil;
import lot.hack.common.Timer;
import lot.hack.event.Event;
import lot.hack.event.events.game.EventGameLoop;
import lot.hack.event.events.world.EventTick;
import lot.hack.setting.value.values.ToggleableConfigurable;
import lot.hack.setting.value.values.ValueBoolean;
import lot.hack.setting.value.values.ValueFloatRange;
import lot.hack.setting.value.values.ValueIntRange;
import lot.hack.setting.value.values.ValueMode;

public class Clicker extends ToggleableConfigurable {
    private final ValueMode delayType = modeValue("DelayType");
    private final ValueMode.Mode delayTypeCPS = delayType.create("CPS").select();
    private final ValueMode.Mode delayTypeMilliseconds = delayType.create("Milliseconds");

    private final ValueFloatRange CPS = floatRangeValue("CPS", 20, 20, 0.1f, 40, 0.1f).visible(() -> delayTypeCPS.selected());
    private final ValueIntRange delay = intRangeValue("Delay", 50, 50, 0, 10000).visible(() -> delayTypeMilliseconds.selected());

    private Timer clickTimer = new Timer();
    private double clicks = 0;
    private final Runnable click;
    private final List<BooleanSupplier> conditions = new ArrayList<>();

    public Clicker(String name, boolean defaultToggled, Runnable click) {
        super(name, defaultToggled);
        this.click = click;
    }

    public Clicker addCondition(String name, boolean defaultToggled, BooleanSupplier condition) {
        ValueBoolean value = booleanValue(name, defaultToggled);
        conditions.add(() -> value.get() && !condition.getAsBoolean());
        return this;
    }

    public void onEvent(Event event) {
        for (BooleanSupplier condition : conditions) {
            if (condition.getAsBoolean())
                return;
        } 

        if (event == EventGameLoop.INSTANCE) {
            int delay = 0;

            if (delayTypeCPS.selected()) {
                delay = (int) (1000 / RandomUtil.random(CPS.get()));
            } else if (delayTypeMilliseconds.selected()) {
                delay = RandomUtil.random(this.delay.get());
            }

            if (clickTimer.reachedMS() >= delay) {
                clicks++;
                clickTimer.reset();
            }
        }

        if (event == EventTick.INSTANCE) {
            while (clicks > 0) {
                click.run();
                clicks--;
            }
        }
    }
}
