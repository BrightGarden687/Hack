package lot.hack.guibuilder.element.elements;

import java.util.function.IntSupplier;

import lot.hack.guibuilder.Renderer;
import lot.hack.guibuilder.element.Element;

public class ElementRect extends Element {
    protected final IntSupplier colorProvider;
    protected int color;

    public ElementRect(float x, float y, float w, float h, IntSupplier colorProvider) {
        super(x, y, w, h);
        this.colorProvider = colorProvider;
    }

    @Override
    public void update() {
        super.update();
        color = colorProvider.getAsInt();
    }

    @Override
    public void draw(Renderer renderer, float xOffset, float yOffset) {
        float x = this.x + xOffset;
        float y = this.y + yOffset;

        renderer.rect(x, y, w, h, color);

        super.draw(renderer, x, y);
    }
}
