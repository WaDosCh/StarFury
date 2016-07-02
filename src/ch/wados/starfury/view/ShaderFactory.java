package ch.wados.starfury.view;

import org.lwjgl.opengl.GL20;

public class ShaderFactory {

	public static ShaderProgram createShader(String vertex, String fragment) {
		Shader vs = Shader.loadShader(GL20.GL_VERTEX_SHADER, vertex);
		Shader fs = Shader.loadShader(GL20.GL_FRAGMENT_SHADER, fragment);
		ShaderProgram p = new ShaderProgram();
		p.attachShader(vs);
		p.attachShader(fs);
		p.bindFragmentDataLocation(0, "fragColor");
		p.link();
		return p;
	}

}
