#version 330 core

layout(location = 0) in vec3 vertex_position;
layout(location = 1) in vec2 vertex_tex;

uniform mat4 MVP;

out vec2 fragment_tex;

void main() {
	gl_Position = MVP * vec4(vertex_position, 1);
	fragment_tex = vertex_tex;
}