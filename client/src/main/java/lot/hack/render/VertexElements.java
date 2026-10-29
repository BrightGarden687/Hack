package lot.hack.render;

import com.mojang.renderpearl.api.GpuFormat;

public final class VertexElements {
    private VertexElements() {}

    public static final String ROUNDING_NAME = "Rounding";
    public static final GpuFormat ROUNDING_FORMAT = GpuFormat.RGBA32_FLOAT;

    public static final String DIMENSIONS_NAME = "Dimensions";
    public static final GpuFormat DIMENSIONS_FORMAT = GpuFormat.RGBA32_FLOAT;
}
