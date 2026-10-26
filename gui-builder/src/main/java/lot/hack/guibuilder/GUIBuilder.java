package lot.hack.guibuilder;

import java.util.ArrayList;
import java.util.List;

import lot.hack.guibuilder.element.Element;

public class GUIBuilder {
    private final Renderer renderer;
    private final List<Element> elements = new ArrayList<>();

    private GUIBuilder(Renderer renderer) {
        this.renderer = renderer;
    }

    public static GUIBuilder begin(Renderer renderer) {
        return new GUIBuilder(renderer);
    }

    public GUIBuilder addElement(Element element) {
        elements.add(element);
        return this;
    } 

    public GUI build() {
        return new GUI(renderer, elements.toArray(new Element[0]));
    }
}
