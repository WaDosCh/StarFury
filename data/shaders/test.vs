#version 330 core

layout(location = 0) in vec3 vertex_position;
layout(location = 1) in vec3 vertex_color;

out vec3 fragment_color;

void main() {
	gl_Position.xyz = vertex_position;
	gl_Position.w = 1.0;
	fragment_color = vertex_color;
}