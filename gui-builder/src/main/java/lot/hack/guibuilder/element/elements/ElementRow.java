package lot.hack.guibuilder.element.elements;

import lot.hack.common.FloatSupplier;
import lot.hack.guibuilder.MouseInput;
import lot.hack.guibuilder.Renderer;
import lot.hack.guibuilder.element.Element;

public class ElementRow extends Element {
    private final FloatSupplier wProvider, hProvider;
    protected final float gaps;

    public ElementRow(float x, float y, float gaps) {
        super(x, y,  0, 0);
        this.gaps = gaps;

        wProvider = () -> {
            float totalW = 0;
            for (Element child : childs) {
                totalW += child.getW() + this.gaps;
            }
            return totalW;
        };

        hProvider = () -> {
            float maxH = 0;
            for (Element child : childs) {
                float childH = child.getH();
                if (childH > maxH) maxH = childH;
            }
            return maxH;
        };
    }

    @Override
    public void update() {
        super.update();
        w = wProvider.getAsFloat();
        h = hProvider.getAsFloat();
    }

    @Override
    public boolean onInput(MouseInput mouseInput, float xOffset, float yOffset) {
        float x = this.x + xOffset;
        float y = this.y + yOffset;

        float offset = 0;
        for (Element child : childs) {
            if (child.onInput(mouseInput, x + offset, y)) return true;
            offset += child.getW() + gaps;
        }

        return false;
    }

    @Override
    public void draw(Renderer renderer, float xOffset, float yOffset) {
        float x = this.x + xOffset;
        float y = this.y + yOffset;

        float offset = 0;
        for (Element child : childs) {
            child.draw(renderer, x + offset, y);
            offset += child.getW() + gaps;
        }
    }
}
