package lot.hack.gui;

import lot.hack.guibuilder.Renderer;
import lot.hack.utils.MCInstances;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class DefaultRenderer implements Renderer, MCInstances {
    public static final DefaultRenderer INSTANCE = new DefaultRenderer();

    private GuiGraphicsExtractor graphicsExtractor = null;

    private DefaultRenderer() {}

    public void setGraphicsExtractor(GuiGraphicsExtractor graphicsExtractor) {
        this.graphicsExtractor = graphicsExtractor;
    }

    @Override
    public void rect(float x, float y, float w, float h, int color) {
        if (graphicsExtractor == null) return;
        graphicsExtractor.fill((int) x, (int) y, (int) (x + w), (int) (y + h), color);
    }
    
    @Override
    public void text(String text, float x, float y, float size, int color) {
        graphicsExtractor.text(mc.font, text, (int) x, (int) y, color);
    }

    @Override
    public float textWidth(String text, float size) {
        return mc.font.width(text);
    }

    @Override
    public float textHeight(float size) {
        return mc.font.lineHeight;
    }
}
