#version 330
#extension GL_ARB_separate_shader_objects : require

// @VibeCode(model = "GLM-5.3-Flash")

#include <minecraft:dynamictransforms.glsl>

layout(location = 0) in vec4 vertexColor;

layout(location = 0) out vec4 fragColor;

void main() {
    fragColor = vertexColor;
}
