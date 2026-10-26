package lot.hack.module;

import java.util.ArrayList;

import lot.hack.module.modules.combat.ModuleAttackAura;
import lot.hack.module.modules.movement.ModuleSprint;
import lot.hack.module.modules.player.ModuleAutoClicker;
import lot.hack.utils.Initializable;

public class Modules extends ArrayList<ClientModule> implements Initializable {
    public static final Modules INSTANCE = new Modules();

    private Modules() {}

    @Override
    public void initialize() {
        ModuleSprint.INSTANCE.initialize();
        ModuleAttackAura.INSTANCE.initialize();
        ModuleAutoClicker.INSTANCE.initialize();
    }
}
