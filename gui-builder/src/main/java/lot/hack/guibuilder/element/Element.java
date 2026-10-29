package lot.hack.guibuilder.element;

import java.util.ArrayList;
import java.util.List;

import lot.hack.guibuilder.MouseInput;
import lot.hack.guibuilder.Renderer;

public class Element {
    protected final float x, y;
    protected float w, h;

    protected final List<Element> childs = new ArrayList<>();

    public Element(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void update() {
        for (int i = childs.size() - 1; i >= 0; i--) {
            Element child = childs.get(i);
            child.update();
        }
    }

    public boolean onInput(MouseInput mouseInput, float xOffset, float yOffset) {
        for (int i = childs.size() - 1; i >= 0; i--) {
            Element child = childs.get(i);
            if (child.onInput(mouseInput, xOffset, yOffset)) return true;
        }

        return false;
    }

    public void draw(Renderer renderer, float xOffset, float yOffset) {
        for (Element child : childs) {
            child.draw(renderer, xOffset, yOffset);
        }
    }

    public boolean isInside(float xOffset, float yOffset, float px, float py) {
        return px >= x + xOffset && px <= x + xOffset + w && py >= y + yOffset && py <= y + yOffset + h;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }
}
