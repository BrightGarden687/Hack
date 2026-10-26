package lot.hack.guibuilder.element.elements;

import lot.hack.guibuilder.Renderer;
import lot.hack.guibuilder.element.Element;

public class ElementRect extends Element {
    protected int color;

    public ElementRect(float x, float y, float w, float h, int color) {
        super(x, y, w, h);
        this.color = color;
    }

    @Override
    public void draw(Renderer renderer) {
        renderer.rect(x, y, w, h, color);

        super.draw(renderer);
    }
}
