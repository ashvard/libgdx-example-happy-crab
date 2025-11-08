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

uniform float u_wave_frequency;
uniform float wave_speed;
uniform float u_water_level_amplitude;
uniform float u_water_level_frequency;

<GET_TEXTURE_FROM_ARRAY_PLACEHOLDER>

void main() {
    float waveSpeed = wave_speed;
    float waveFrequency = u_wave_frequency;
    float waterLevelSpeed = 0.0;
    float waterLevelAmplitude = u_water_level_amplitude;
    float waterLevelFrequency = u_water_level_frequency;
    float offsetY = sin(v_texCoords.x * waveFrequency + u_time * waveSpeed) * 0.0012;

    float waterLevelOffset = -abs(sin(u_time * waterLevelSpeed + v_texCoords.x * waterLevelFrequency)) * waterLevelAmplitude;
    vec2 offset = vec2(0, -offsetY + waterLevelOffset);
    vec2 distortedCoords = v_texCoords + offset;

    vec4 color = getTextureFromArray(distortedCoords);
    if (distortedCoords.y < u_atlas_coords.y) {
        color.a = 0.0;
    }
    gl_FragColor = v_color * color;
}