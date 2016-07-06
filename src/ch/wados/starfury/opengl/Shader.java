package ch.wados.starfury.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

class Shader {

	private final int id;

	Shader(int type, CharSequence source) {
		this.id = GL20.glCreateShader(type);
		GL20.glShaderSource(this.id, source);
		GL20.glCompileShader(id);

		checkStatus();
	}

	private void checkStatus() {
		int status = GL20.glGetShaderi(this.id, GL20.GL_COMPILE_STATUS);
		if (status != GL11.GL_TRUE) {
			throw new RuntimeException(GL20.glGetShaderInfoLog(this.id));
		}
	}

	void delete() {
		GL20.glDeleteShader(this.id);
	}

	int getID() {
		return this.id;
	}

}
