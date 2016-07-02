package ch.wados.starfury.view;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class SpriteRenderer implements PrimitiveRenderer {

	private final Texture texture;
	private final ShaderProgram shader;
	private final int vao_id;
	private final int vbo_id;
	private final int ebo_id;

	public SpriteRenderer(Texture texture, ShaderProgram shader, float width,
			float height) {
		this.texture = texture;
		this.shader = shader;

		this.vao_id = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(this.vao_id);

		FloatBuffer vertices = BufferUtils.createFloatBuffer(4 * 7);
		vertices.put(-width / 2).put(-height / 2).put(1f).put(1f).put(1f)
				.put(0f).put(0f);
		vertices.put(width / 2).put(-width / 2).put(1f).put(1f).put(1f).put(1f)
				.put(0f);
		vertices.put(width / 2).put(width / 2).put(1f).put(1f).put(1f).put(1f)
				.put(1f);
		vertices.put(-width / 2).put(width / 2).put(1f).put(1f).put(1f).put(0f)
				.put(1f);
		vertices.flip();

		this.vbo_id = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vbo_id);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

		IntBuffer elements = BufferUtils.createIntBuffer(2 * 3);
		elements.put(0).put(1).put(2);
		elements.put(2).put(3).put(0);
		elements.flip();

		this.ebo_id = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.ebo_id);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, vertices,
				GL15.GL_STATIC_DRAW);

		specifyVertexAttributes();

	}

	private void specifyVertexAttributes() {
		/* Specify Vertex Pointer */
		int posAttrib = shader.getAttributeLocation("position");
		shader.enableVertexAttribute(posAttrib);
		shader.pointVertexAttribute(posAttrib, 2, 7 * Float.BYTES, 0);

		/* Specify Color Pointer */
		int colAttrib = shader.getAttributeLocation("color");
		shader.enableVertexAttribute(colAttrib);
		shader.pointVertexAttribute(colAttrib, 3, 7 * Float.BYTES,
				2 * Float.BYTES);

		/* Specify Texture Pointer */
		int texAttrib = shader.getAttributeLocation("texcoord");
		shader.enableVertexAttribute(texAttrib);
		shader.pointVertexAttribute(texAttrib, 2, 7 * Float.BYTES,
				5 * Float.BYTES);
	}

	@Override
	public void render(Matrix4f model, Matrix4f camera) {
		shader.use();
		this.texture.bind();

		int texID = GL20.glGetUniformLocation(this.shader.getId(), "textureImage");
		GL20.glUniform1i(texID, 0);

		/* Set model matrix to identity matrix */
		int uniModel = shader.getUniformLocation("model");
		shader.setUniform(uniModel, model);

		/* Set view matrix to identity matrix */
		int uniCamera = shader.getUniformLocation("camera");
		shader.setUniform(uniCamera, camera);

		GL30.glBindVertexArray(this.vao_id);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
	}

}
