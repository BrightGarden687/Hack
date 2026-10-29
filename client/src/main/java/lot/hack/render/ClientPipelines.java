package lot.hack.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.pipeline.BlendFunction;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.renderpearl.api.pipeline.RenderPipeline.Builder;

import lot.hack.Hack;
import lot.hack.common.VibeCode;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;

public class ClientPipelines {
    public static final RenderPipeline RECT = RenderPipelines.register(
        begin("rect")
            .withVertexShader(Hack.id("core/position_color/vertex"))
            .withFragmentShader(Hack.id("core/position_color/fragment"))
            .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .build()
    );

    public static final RenderPipeline ROUNDED_RECT = RenderPipelines.register(
        begin("rounded_rect")
            .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
            .withVertexShader(Hack.id("core/rounded_rect/vertex"))
            .withFragmentShader(Hack.id("core/rounded_rect/fragment"))
            .withVertexBinding(0, VertexFormats.ROUNDED_RECT)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .build()
    );

    public static final RenderPipeline ROUNDED_IMAGE = RenderPipelines.register(
        begin("rounded_image")
            .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
            .withVertexShader(Hack.id("core/rounded_image/vertex"))
            .withFragmentShader(Hack.id("core/rounded_image/fragment"))
            .withVertexBinding(0, VertexFormats.ROUNDED_IMAGE)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .build()
    );

    @VibeCode(model = "GLM-5.3-Flash")
    private static Builder begin(String name) {
        return RenderPipeline.builder(RenderPipelines.GLOBALS_SNIPPET)
            .withLocation(Hack.id("pipeline/" + name))
            .withBindGroupLayout(BindGroupLayouts.PROJECTION)
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT));
    }
}
