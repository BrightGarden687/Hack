package lot.hack.render;

import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.vertex.VertexFormat;

public final class VertexFormats {
    private VertexFormats() { }

    public static final VertexFormat ROUNDED_RECT = VertexFormat.builder(0)
        .addAttribute("Position", GpuFormat.RGB32_FLOAT)
        .addAttribute("Color", GpuFormat.RGBA8_UNORM)
        .addAttribute(VertexElements.ROUNDING_NAME, VertexElements.ROUNDING_FORMAT)
        .addAttribute(VertexElements.DIMENSIONS_NAME, VertexElements.DIMENSIONS_FORMAT)
        .build();

    public static final VertexFormat ROUNDED_IMAGE = VertexFormat.builder(0)
        .addAttribute("Position", GpuFormat.RGB32_FLOAT)
        .addAttribute("UV0", GpuFormat.RG32_FLOAT)
        .addAttribute("Color", GpuFormat.RGBA8_UNORM)
        .addAttribute(VertexElements.ROUNDING_NAME, VertexElements.ROUNDING_FORMAT)
        .addAttribute(VertexElements.DIMENSIONS_NAME, VertexElements.DIMENSIONS_FORMAT)
        .build();
}
