package lot.hack.guibuilder.element;

import lot.hack.guibuilder.MouseInput;
import lot.hack.guibuilder.Renderer;

public class Element {
    protected float x, y, w, h;

    public Element(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(Renderer renderer) {}

    public boolean onInput(MouseInput mouseInput) { return false; }

    public boolean isInside(float px, float py) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }
}
