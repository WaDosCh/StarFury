package ch.wados.starfury.view;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import ch.wados.starfury.opengl.Factory;
import ch.wados.starfury.opengl.Program;
import ch.wados.starfury.opengl.Texture;
import ch.wados.starfury.opengl.VertexArray;
import ch.wados.vecmath.Matrix4f;

public class SpriteRenderer implements PrimitiveRenderer {

	private final Program shader;
	private final VertexArray vao;
	private final Texture texture;
	private final Matrix4f scaling;

	public SpriteRenderer(float width, float height, Texture texture) {
		this.scaling = Matrix4f.scale(width, height, 1);
		this.texture = texture;

		this.shader = Factory.loadProgram("basic_sprite");

		float vertices[] = { -1, -1, 0, 1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0,
				-1, 1, 0 };
		float text[] = { 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1 };
		this.vao = new VertexArray(GL15.GL_STATIC_DRAW, GL11.GL_TRIANGLES, 6,
				vertices, text);

	}

	@Override
	public void enable() {
		this.shader.use();
		shader.setUniform("scale", this.scaling);
		shader.bindTexture(this.texture, "myTexture", 0);
		vao.bindAndEnable();
	}

	@Override
	public void render(Matrix4f model, Matrix4f camera) {
		shader.setUniform("MP", camera.multiply(model));
		vao.draw();
	}

	@Override
	public void disable() {
		vao.disable();
	}

	@Override
	public void destroy() {
		this.shader.delete();
	}
}
