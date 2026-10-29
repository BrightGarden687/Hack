package lot.hack.guibuilder;

public interface Renderer {
    public void rect(float x, float y, float w, float h, int color);
    public void text(String text, float x, float y, float size, int color);
    public float textWidth(String text, float size);
    public float textHeight(float size);
}
