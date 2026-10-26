package lot.hack.guibuilder.element.elements;

import java.util.function.BiConsumer;

import lot.hack.guibuilder.MouseInput;
import lot.hack.guibuilder.Renderer;
import lot.hack.guibuilder.element.Element;

public class ElementButton extends Element {
    protected int color;
    protected BiConsumer<Integer, Integer> action;

    public ElementButton(float x, float y, float w, float h, int color, BiConsumer<Integer, Integer> action) {
        super(x, y, w, h);
        this.color = color;
        this.action = action;
    }

    @Override
    public void draw(Renderer renderer) {
        renderer.rect(x, y, w, h, color);

        super.draw(renderer);
    }

    @Override
    public boolean onInput(MouseInput mouseInput) {
        if (super.onInput(mouseInput)) {
            return true;
        }

        if (isInside(mouseInput.x(), mouseInput.y())) {
            action.accept(mouseInput.button(), mouseInput.action());
            return true;
        }

        return false;
    }
}
