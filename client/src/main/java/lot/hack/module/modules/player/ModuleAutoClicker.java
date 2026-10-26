package lot.hack.module.modules.player;

import lot.hack.event.Event;
import lot.hack.event.events.game.EventGameLoop;
import lot.hack.event.events.world.EventTick;
import lot.hack.module.ClientModule;
import lot.hack.module.ModuleCategory;
import lot.hack.setting.value.values.ValueBoolean;
import lot.hack.setting.value.values.ValueFloatRange;
import lot.hack.utils.RandomUtil;

public class ModuleAutoClicker extends ClientModule {
    public static final ModuleAutoClicker INSTANCE = new ModuleAutoClicker();

    private final ValueFloatRange CPS = floatRangeValue("CPS", 20, 20, 0.1f, 40, 0.1f);
    private final ValueBoolean onlyWhileLeftDown = booleanValue("OnlyWhileLeftDown", true);

    private long lastClickTime = 0;
    private int clicks = 0;

    private ModuleAutoClicker() {
        super("AutoClicker", true, ModuleCategory.PLAYER);
    }

    @Override
    public void onEvent(Event event) {
        if (onlyWhileLeftDown.get() && !mc.options.keyAttack.isDown())
            return;

        if (event == EventGameLoop.INSTANCE) {
            float delay = 1000 / RandomUtil.random(CPS.get());

            if (System.currentTimeMillis() - lastClickTime >= delay) {
                clicks++;
                lastClickTime = System.currentTimeMillis();
            }
        }

        if (event == EventTick.INSTANCE) {
            while (clicks > 0) {
                mc.startAttack();
                clicks--;
            }
        }
    }
}
