package lot.hack.guibuilder;

import lot.hack.guibuilder.element.Element;

public class GUI {
    private final Renderer renderer;
    private final Element[] elements;

    public GUI(Renderer renderer, Element... elements) {
        this.renderer = renderer;
        this.elements = elements;
    }

    public void onInput(MouseInput mouseInput) {
        for (Element element : elements) {
            if (element.onInput(mouseInput, 0, 0))
                break;
        }
    }

    public void draw() {
        for (Element element : elements) {
            element.update();
            element.draw(renderer, 0, 0);
        }
    }
}
