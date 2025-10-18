#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
varying float v_texture_index;
uniform sampler2D u_textures[MAX_TEXTURE_UNITS];
uniform float u_time;
<GET_TEXTURE_FROM_ARRAY_PLACEHOLDER>

void main() {
    vec4 original = getTextureFromArray(v_texCoords);
    if (original.a < 0.01) discard;
    float wave = sin(v_texCoords.x * 60.0 + u_time * 6.2) * 0.016;
    float verticalMovement = sin(u_time * 1.5) * 0.03;
    float topMask = smoothstep(0.2, 0.8, v_texCoords.y);
    vec2 finalCoords = v_texCoords;
    finalCoords.y -= (wave + verticalMovement) * topMask;
    gl_FragColor = v_color * getTextureFromArray(finalCoords);
}