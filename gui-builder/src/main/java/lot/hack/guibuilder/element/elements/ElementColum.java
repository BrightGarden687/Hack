package lot.hack.guibuilder.element.elements;

import lot.hack.common.FloatSupplier;
import lot.hack.guibuilder.MouseInput;
import lot.hack.guibuilder.Renderer;
import lot.hack.guibuilder.element.Element;

public class ElementColum extends Element {
    private final FloatSupplier wProvider, hProvider;
    protected final float gaps;

    public ElementColum(float x, float y, float gaps) {
        super(x, y,  0, 0);
        this.gaps = gaps;

        wProvider = () -> {
            float maxW = 0;
            for (Element child : childs) {
                float childW = child.getW();
                if (childW > maxW) maxW = childW;
            }
            return maxW;
        };

        hProvider = () -> {
            float totalH = 0;
            for (Element child : childs) {
                totalH += child.getH() + this.gaps;
            }
            return totalH;
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
            if (child.onInput(mouseInput, x, y + offset)) return true;
            offset += child.getH() + gaps;
        }

        return false;
    }

    @Override
    public void draw(Renderer renderer, float xOffset, float yOffset) {
        float x = this.x + xOffset;
        float y = this.y + yOffset;

        float offset = 0;
        for (Element child : childs) {
            child.draw(renderer, x, y + offset);
            offset += child.getH() + gaps;
        }
    }
}
