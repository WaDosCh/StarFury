#version 150 core

in vec3 vertexColor;
in vec2 textureCoord;

out vec4 fragColor;

uniform sampler2D textureImage;

void main() {
	//vec4 textureColor = texture(textureImage, textureCoord);
	//fragColor = vec4(vertexColor, 1.0) * textureColor;
	fragColor = vec4(1.0, 1.0, 1.0, 0.5);
}