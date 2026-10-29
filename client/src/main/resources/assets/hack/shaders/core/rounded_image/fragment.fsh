#version 330

#include <minecraft:dynamictransforms.glsl>

uniform sampler2D Sampler0;

in vec2 texCoord0;
in vec4 rounding;
in vec4 dimensions;
in vec4 vertexColor;

out vec4 fragColor;

float sdRoundBox(in vec2 pos, in vec2 halfDim, in vec4 radius) {
    radius.xy = (pos.x<0.0)?radius.xy : radius.wz; // было zw
    radius.x  = (pos.y<0.0)?radius.x  : radius.y;
    vec2 q = abs(pos)-halfDim+radius.x;
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - radius.x;
}

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor;

    vec2 hDim = dimensions.zw/2.;
    float d = sdRoundBox(dimensions.xy-hDim, hDim, rounding);
    color.a *= 1.0-clamp(d, 0.0, 1.0);

    if (color.a == 0.0) {
        discard;
    }
    fragColor = color * ColorModulator;
}