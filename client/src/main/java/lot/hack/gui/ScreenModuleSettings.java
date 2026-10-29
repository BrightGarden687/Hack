package lot.hack.gui;

import com.mojang.blaze3d.platform.InputConstants;

import lot.hack.common.Align;
import lot.hack.guibuilder.GUI;
import lot.hack.guibuilder.GUIBuilder;
import lot.hack.guibuilder.MouseInput;
import lot.hack.guibuilder.element.elements.ElementButton;
import lot.hack.keybind.KeybindHandler;
import lot.hack.keybind.keybinds.GUIKeybind;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class ScreenModuleSettings extends Screen {
    public static final ScreenModuleSettings INSTANCE = new ScreenModuleSettings();

    private final GUI content = GUIBuilder.begin(DefaultRenderer.INSTANCE)
        .addElement(
            new ElementButton(
                0f, 0f, 100f, 20f, 
                () -> -1, () -> "Enter text",
                Align.CENTER, 12f,
                () -> (int) 0xFF000000, 
                (button, action) -> System.out.println("UwU" + "   " + button + "   " + action)
            )
        )
        .build();

    private ScreenModuleSettings() {
        super(Component.empty());
        KeybindHandler.INSTANCE.register(new GUIKeybind(InputConstants.KEY_RSHIFT, this));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        DefaultRenderer.INSTANCE.setGraphicsExtractor(graphics);
        content.draw();
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        content.onInput(new MouseInput((float) event.x(), (float) event.y(), event.button(), 0));
        return false;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        content.onInput(new MouseInput((float) event.x(), (float) event.y(), event.button(), 1));
        return false;
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        content.onInput(new MouseInput((float) event.x(), (float) event.y(), event.button(), 2));
        return false;
    }
}
