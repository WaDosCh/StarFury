package ch.wados.starfury.opengl;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public final class Texture {

	private final int id;

	private final int width;
	private final int height;

	Texture(int width, int height, ByteBuffer data) {
		this.width = width;
		this.height = height;

		this.id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
				GL13.GL_CLAMP_TO_BORDER);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
				GL13.GL_CLAMP_TO_BORDER);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_NEAREST);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height,
				0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
	}

	public void bind(int textureUnit) {
		GL13.glActiveTexture(textureUnit);
		this.bind();
	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
	}

	public void delete() {
		GL11.glDeleteTextures(this.id);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

}
