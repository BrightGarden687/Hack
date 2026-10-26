package lot.hack.gui;

import lot.hack.guibuilder.Renderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class DefaultRenderer implements Renderer {
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
}
