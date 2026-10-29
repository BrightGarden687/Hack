package lot.hack.render;

import java.util.function.Consumer;

import org.joml.Matrix3x2fc;
import org.jspecify.annotations.Nullable;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;

import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;

public class Element implements GuiElementRenderState {
    private final RenderPipeline pipeline;
    private final Consumer<VertexConsumer> verticesBuilder;
    private final ScreenRectangle scissorArea;
    private final ScreenRectangle bounds;
    private final TextureSetup textureSetup;

    public Element(Matrix3x2fc pose, RenderPipeline pipeline, Consumer<VertexConsumer> verticesBuilder, ScreenRectangle rect, ScreenRectangle scissorArea) {
        this(pose, pipeline, TextureSetup.noTexture(), verticesBuilder, rect, scissorArea);
    }

    public Element(Matrix3x2fc pose, RenderPipeline pipeline, TextureSetup textureSetup, Consumer<VertexConsumer> verticesBuilder, ScreenRectangle rect, ScreenRectangle scissorArea) {
        this.pipeline = pipeline;
        this.verticesBuilder = verticesBuilder;
        this.textureSetup = textureSetup;
        this.scissorArea = scissorArea;

        ScreenRectangle bounds = rect.transformMaxBounds(pose);
        if (scissorArea == null) {
            this.bounds = bounds;
        } else {
            this.bounds = scissorArea.intersection(bounds);
        }
    }

    @Override
    public @Nullable ScreenRectangle bounds() {
        return bounds;
    }

    @Override
    public void buildVertices(VertexConsumer vertexConsumer) {
        verticesBuilder.accept(vertexConsumer);
    }

    @Override
    public RenderPipeline pipeline() {
        return pipeline;
    }

    @Override
    public @Nullable ScreenRectangle scissorArea() {
        return scissorArea;
    }

    @Override
    public TextureSetup textureSetup() {
        return textureSetup;
    }
}
