#version 330 core

in vec2 fragment_uv;

uniform sampler2D myTexture;

out vec4 color;

void main() {
	color = texture(myTexture, fragment_uv);
}