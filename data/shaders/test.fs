#version 330 core

in vec2 fragment_tex;

uniform sampler2D text;

out vec4 color;

void main() {
	color = texture(text, fragment_tex);
}