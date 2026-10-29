#version 330

#include <minecraft:dynamictransforms.glsl>

layout(location = 0) in vec4 vertexColor;
layout(location = 1) in vec4 rounding;
layout(location = 2) in vec4 dimensions;

layout(location = 0) out vec4 fragColor;

float sdRoundBox(in vec2 pos, in vec2 halfDim, in vec4 radius) {
    radius.xy = (pos.x<0.0)?radius.xy : radius.wz;
    radius.x  = (pos.y<0.0)?radius.x  : radius.y;
    vec2 q = abs(pos)-halfDim+radius.x;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - radius.x;
}

void main() {
    vec4 color = vertexColor;

    vec2 hDim = dimensions.zw/2.;
    float d = sdRoundBox(dimensions.xy-hDim, hDim, rounding);
    color.a *= 1.0-clamp(d, 0.0, 1.0);

    if (color.a == 0.0) {
        discard;
    }
    fragColor = color * ColorModulator;
}
