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
uniform vec4 u_atlas_coords;

<GET_TEXTURE_FROM_ARRAY_PLACEHOLDER>

void main() {
    float waveSpeed = 1.8;
    float waveFrequency = 640.0;
    float offsetY = abs(sin(v_texCoords.x * waveFrequency + u_time * waveSpeed)) * 0.0012;
    vec2 offset = vec2(0, -offsetY);
    vec2 distortedCoords = v_texCoords + offset;
    vec4 color = getTextureFromArray(distortedCoords);
    if (distortedCoords.y < u_atlas_coords.y) {
        color.a = 0.0;
    }
    gl_FragColor = v_color * color;
}