
package ch.wados.starfury.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import ch.wados.vecmath.Matrix4f;
import ch.wados.vecmath.Vector4f;

public class Program {

	private final int id;

	public int getId() {
		return id;
	}

	public Program() {
		id = GL20.glCreateProgram();
	}

	void attachShader(ch.wados.starfury.opengl.Shader shader) {
		GL20.glAttachShader(id, shader.getID());
	}

	void bindFragmentDataLocation(int number, CharSequence name) {
		GL30.glBindFragDataLocation(id, number, name);
	}

	void link() {
		GL20.glLinkProgram(id);
		checkStatus();
	}

	public int getAttributeLocation(CharSequence name) {
		return GL20.glGetAttribLocation(id, name);
	}

	public void enableVertexAttribute(int location) {
		GL20.glEnableVertexAttribArray(location);
	}

	public void disableVertexAttribute(int location) {
		GL20.glDisableVertexAttribArray(location);
	}

	/**
	 * Sets the vertex attribute pointer.
	 *
	 * @param location
	 *            Location of the vertex attribute
	 * @param size
	 *            Number of values per vertex
	 * @param stride
	 *            Offset between consecutive generic vertex attributes in bytes
	 * @param offset
	 *            Offset of the first component of the first generic vertex
	 *            attribute in bytes
	 */
	public void pointVertexAttribute(int location, int size, int stride,
			int offset) {
		GL20.glVertexAttribPointer(location, size, GL11.GL_FLOAT, false, stride,
				offset);
	}

	public int getUniformLocation(String name) {
		return GL20.glGetUniformLocation(this.id, name);
	}

	public void setUniform(String name, int value) {
		GL20.glUniform1i(getUniformLocation(name), value);
	}

	public void setUniform(String name, Vector4f vector) {
		GL20.glUniform4fv(getUniformLocation(name), vector.comps);
	}

	public void setUniform(String name, Matrix4f matrix) {
		GL20.glUniformMatrix4fv(getUniformLocation(name), false,
				matrix.getGLArray());
	}

	public void use() {
		GL20.glUseProgram(id);
	}

	public void checkStatus() {
		int status = GL20.glGetProgrami(id, GL20.GL_LINK_STATUS);
		if (status != GL11.GL_TRUE) {
			throw new RuntimeException(GL20.glGetProgramInfoLog(id));
		}
	}

	public void delete() {
		GL20.glDeleteProgram(id);
	}

	public void bindTexture(Texture texture, String name, int textureUnit) {
		assert textureUnit >= 0;
		assert textureUnit < 32;
		this.setUniform(name, textureUnit);
		GL13.glActiveTexture(textureUnit + GL13.GL_TEXTURE0);
		texture.bind();
	}

}
