package lot.hack;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lot.hack.event.EventListenersCaller;
import lot.hack.gui.GUIs;
import lot.hack.keybind.KeybindHandler;
import lot.hack.module.Modules;
import lot.hack.rotation.RotateCaller;

public class Hack implements ModInitializer {
	public static final String MOD_ID = "hack";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		EventListenersCaller.INSTANCE.initialize();
		KeybindHandler.INSTANCE.initialize();
		Modules.INSTANCE.initialize();
		RotateCaller.INSTANCE.initialize();
		GUIs.INSTANCE.initialize();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
