package lot.hack.module.modules.player;

import lot.hack.event.Event;
import lot.hack.module.ClientModule;
import lot.hack.module.ModuleCategory;
import lot.hack.module.modules.player.autoclicker.Clicker;
import net.minecraft.world.item.BlockItem;

public class ModuleAutoClicker extends ClientModule {
    public static final ModuleAutoClicker INSTANCE = new ModuleAutoClicker();

    private final Clicker attack = tree(new Clicker("Attack", true, () -> mc.startAttack()))
        .addCondition("OnlyWhileButtonDown", true, () -> mc.options.keyAttack.isDown());

    private final Clicker use = tree(new Clicker("Use", true, () -> mc.startUseItem()))
        .addCondition("OnlyWhileButtonDown", true, () -> mc.options.keyUse.isDown())
        .addCondition("OnlyWhileInHandBlock", true, () -> localPlayer().getActiveItem().getItem() instanceof BlockItem);

    private ModuleAutoClicker() {
        super("AutoClicker", false, ModuleCategory.PLAYER);
    }

    @Override
    public void onEvent(Event event) {
        attack.onEvent(event);
        use.onEvent(event);
    }
}
