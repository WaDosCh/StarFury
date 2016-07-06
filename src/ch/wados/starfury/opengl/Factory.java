package ch.wados.starfury.opengl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.stb.STBImage;

public final class Factory {

	public static Texture loadTexture(String path) {
		/* Prepare image buffers */
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);

		/* Load image */
		STBImage.stbi_set_flip_vertically_on_load(1);
		ByteBuffer image = STBImage.stbi_load("data/" + path, w, h, comp, 4);
		if (image == null) {
			throw new RuntimeException("Failed to load a texture file!"
					+ System.lineSeparator() + STBImage.stbi_failure_reason());
		}

		/* Get width and height of image */
		int width = w.get();
		int height = h.get();

		return new Texture(width, height, image);
	}

	private static Shader loadShader(int type, String path) {
		StringBuilder builder = new StringBuilder();

		try (InputStream in = new FileInputStream("data/shaders/" + path);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in))) {
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
		} catch (IOException ex) {
			throw new RuntimeException("Failed to load a shader file!"
					+ System.lineSeparator() + ex.getMessage());
		}

		CharSequence source = builder.toString();
		return new Shader(type, source);
	}

	public static Program loadProgram(String name) {
		Shader vs = loadShader(GL20.GL_VERTEX_SHADER, name + ".vs");
		Shader fs = loadShader(GL20.GL_FRAGMENT_SHADER, name + ".fs");
		Program p = new Program();
		p.attachShader(vs);
		p.attachShader(fs);
		p.bindFragmentDataLocation(0, "color");
		p.link();
		return p;
	}

}
