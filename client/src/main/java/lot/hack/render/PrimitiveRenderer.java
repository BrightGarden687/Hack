package lot.hack.render;

import org.joml.Matrix3x2fc;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;

public class PrimitiveRenderer {
    public static void rect(
        GuiGraphicsExtractor graphicsExtractor,
        Matrix3x2fc pose,
        int x, int y, int w, int h,
        int c0, int c1, int c2, int c3,
        float r0, float r1, float r2, float r3
    ) {
        graphicsExtractor.guiRenderState.addGuiElement(
            new Element(
                pose, ClientPipelines.ROUNDED_RECT, TextureSetup.noTexture(),
                vertexConsumer -> {
                    vertexConsumer.addVertexWith2DPose(pose, x, y).setColor(c0);
                    VertexConsumerExtensions.setRounding(vertexConsumer, r0, r1, r2, r3);
                    VertexConsumerExtensions.setDimensions(vertexConsumer, 0, 0, w, h);
                    vertexConsumer.addVertexWith2DPose(pose, x, y + h).setColor(c1);
                    VertexConsumerExtensions.setRounding(vertexConsumer, r0, r1, r2, r3);
                    VertexConsumerExtensions.setDimensions(vertexConsumer, 0, h, w, h);
                    vertexConsumer.addVertexWith2DPose(pose, x + w, y + h).setColor(c2);
                    VertexConsumerExtensions.setRounding(vertexConsumer, r0, r1, r2, r3);
                    VertexConsumerExtensions.setDimensions(vertexConsumer, w, h, w, h);
                    vertexConsumer.addVertexWith2DPose(pose, x + w, y).setColor(c3);
                    VertexConsumerExtensions.setRounding(vertexConsumer, r0, r1, r2, r3);
                    VertexConsumerExtensions.setDimensions(vertexConsumer, w, 0, w, h);
                },
                new ScreenRectangle(x, y, w, h), graphicsExtractor.scissorStack.peek()
            )
        );
    }

    public static void rect(
        GuiGraphicsExtractor graphicsExtractor,
        int x, int y, int w, int h,
        int c0, int c1, int c2, int c3,
        float r0, float r1, float r2, float r3
    ) {
        rect(
            graphicsExtractor, graphicsExtractor.pose(),
            x, y, w, h,
            c0, c1, c2, c3,
            r0, r1, r2, r3
        );
    }

    public static void roundedImage(
        GuiGraphicsExtractor graphicsExtractor,
        Matrix3x2fc pose, TextureSetup textureSetup,
        int x, int y, int w, int h,
        int c0, int c1, int c2, int c3,
        float r0, float r1, float r2, float r3
    ) {
        graphicsExtractor.guiRenderState.addGuiElement(
            new Element(
                pose, ClientPipelines.ROUNDED_RECT, textureSetup,
                vertexConsumer -> {
                    vertexConsumer.addVertexWith2DPose(pose, x, y).setUv(0, 0).setColor(c0);
                    VertexConsumerExtensions.setRounding(vertexConsumer, r0, r1, r2, r3);
                    VertexConsumerExtensions.setDimensions(vertexConsumer, 0, 0, w, h);
                    vertexConsumer.addVertexWith2DPose(pose, x, y + h).setUv(0, 1).setColor(c1);
                    VertexConsumerExtensions.setRounding(vertexConsumer, r0, r1, r2, r3);
                    VertexConsumerExtensions.setDimensions(vertexConsumer, 0, h, w, h);
                    vertexConsumer.addVertexWith2DPose(pose, x + w, y + h).setUv(1, 1).setColor(c2);
                    VertexConsumerExtensions.setRounding(vertexConsumer, r0, r1, r2, r3);
                    VertexConsumerExtensions.setDimensions(vertexConsumer, w, h, w, h);
                    vertexConsumer.addVertexWith2DPose(pose, x + w, y).setUv(1, 0).setColor(c3);
                    VertexConsumerExtensions.setRounding(vertexConsumer, r0, r1, r2, r3);
                    VertexConsumerExtensions.setDimensions(vertexConsumer, w, 0, w, h);
                },
                new ScreenRectangle(x, y, w, h), graphicsExtractor.scissorStack.peek()
            )
        );
    }

    public static void roundedImage(
        GuiGraphicsExtractor graphicsExtractor,
        TextureSetup textureSetup,
        int x, int y, int w, int h,
        int c0, int c1, int c2, int c3,
        float r0, float r1, float r2, float r3
    ) {
        roundedImage(
            graphicsExtractor, graphicsExtractor.pose(), 
            textureSetup, x, y, w, h,
            c0, c1, c2, c3,
            r0, r1, r2, r3
        );
    }
}
