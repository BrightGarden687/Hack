package lot.hack.guibuilder.element.elements;

import java.util.function.BiConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import lot.hack.common.Align;
import lot.hack.guibuilder.MouseInput;
import lot.hack.guibuilder.Renderer;
import lot.hack.guibuilder.element.Element;

public class ElementButton extends Element {
    protected final IntSupplier colorProvider;
    protected final Supplier<String> textProvider;
    protected final Align align;
    protected final float textSize;
    protected final IntSupplier textColorProvider;
    protected final BiConsumer<Integer, Integer> action;

    protected int color;
    protected String text;
    protected int textColor;

    public ElementButton(
        float x, float y, float w, float h,
        IntSupplier colorProvider, 
        Supplier<String> textProvider, Align align,
        float textSize, IntSupplier textColorProvider,
        BiConsumer<Integer, Integer> action
    ) {
        super(x, y, w, h);
        this.colorProvider = colorProvider;
        this.textProvider = textProvider;
        this.align = align;
        this.textSize = textSize;
        this.textColorProvider = textColorProvider;
        this.action = action;
    }

    @Override
    public void update() {
        super.update();
        color = colorProvider.getAsInt();
        text = textProvider.get();
        textColor = textColorProvider.getAsInt();
    }

    @Override
    public boolean onInput(MouseInput mouseInput, float xOffset, float yOffset) {
        float x = this.x + xOffset;
        float y = this.y + yOffset;

        if (super.onInput(mouseInput, x, y)) {
            return true;
        }

        if (isInside(xOffset, yOffset, mouseInput.x(), mouseInput.y())) {
            action.accept(mouseInput.button(), mouseInput.action());
            return true;
        }

        return false;
    }

    @Override
    public void draw(Renderer renderer, float xOffset, float yOffset) {
        float x = this.x + xOffset;
        float y = this.y + yOffset;

        renderer.rect(x, y, w, h, color);
        float textWidth = renderer.textWidth(text, textSize);
        float textHeight = renderer.textHeight(textSize);
        renderer.text(text, x + align.xOffset(w, textWidth), y + align.yOffset(h, textHeight), textSize, textColor);

        super.draw(renderer, x, y);
    }
}
