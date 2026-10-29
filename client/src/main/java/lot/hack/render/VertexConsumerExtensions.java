package lot.hack.render;

import org.lwjgl.system.MemoryUtil;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.vertex.VertexFormatElement;

import lot.hack.common.VibeCode;

public final class VertexConsumerExtensions {
    private VertexConsumerExtensions() {}

    public static VertexConsumer setRounding(VertexConsumer consumer, float topLeft, float bottomLeft, float bottomRight, float topRight) {
        if (consumer instanceof BufferBuilder builder) {
            long pointer = attributePointer(builder, VertexElements.ROUNDING_NAME);
            if (pointer != -1L) {
                MemoryUtil.memPutFloat(pointer, topLeft);
                MemoryUtil.memPutFloat(pointer + 4, bottomLeft);
                MemoryUtil.memPutFloat(pointer + 8, bottomRight);
                MemoryUtil.memPutFloat(pointer + 12, topRight);
            }
        }
        return consumer;
    }

    public static VertexConsumer setRounding(VertexConsumer consumer, float radius) {
        return setRounding(consumer, radius, radius, radius, radius);
    }

    public static VertexConsumer setDimensions(VertexConsumer consumer, float x, float y, float w, float h) {
        if (consumer instanceof BufferBuilder builder) {
            long pointer = attributePointer(builder, VertexElements.DIMENSIONS_NAME);
            if (pointer != -1L) {
                MemoryUtil.memPutFloat(pointer, x);
                MemoryUtil.memPutFloat(pointer + 4, y);
                MemoryUtil.memPutFloat(pointer + 8, w);
                MemoryUtil.memPutFloat(pointer + 12, h);
            }
        }
        return consumer;
    }

    @VibeCode(model = "GLM-5.3-Flash")
    private static long attributePointer(BufferBuilder builder, String attributeName) {
        if (!builder.building) return -1L;
        VertexFormatElement element = builder.format.getElement(attributeName);
        if (element == null) return -1L;
        return builder.vertexPointer + element.offset();
    }
}
