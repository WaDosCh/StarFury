#version 150 core

in vec2 position;
in vec3 color;
in vec2 texcoord;

out vec3 vertexColor;
out vec2 textureCoord;

uniform mat4 model;
uniform mat4 camera;

void main () {
	vertexColor = color;
	textureCoord = texcoord;
	mat4 mc = camera * model;
	gl_Position = mc * vec4(position, 1.0, 1.0);
}