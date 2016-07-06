package ch.wados.starfury.view;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import ch.wados.starfury.controller.Scene;
import ch.wados.starfury.controller.SceneManager;
import ch.wados.starfury.math.Matrix4f;
import ch.wados.starfury.opengl.Factory;
import ch.wados.starfury.opengl.Program;
import ch.wados.starfury.opengl.Texture;
import ch.wados.starfury.opengl.VertexArray;

public class TestScene implements Scene {

	Window window;
	float aspectRatio = 1920f / 1080f;

	@Override
	public void enter(SceneManager manager) {
		window = manager.getWindow();

		float vertices[] = { -1, -1, 0, 1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0,
				-1, 1, 0 };
		float text[] = { 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1 };

		Texture texture = Factory.loadTexture("test.png");
		Program shader = Factory.loadProgram("test");
		VertexArray vao = new VertexArray(GL15.GL_STATIC_DRAW,
				GL11.GL_TRIANGLES, 6, vertices, text);

		float ANGLE_STEP = .5f;
		float angle = 0;

		while (!window.isClosing()) {

			window.clear();

			Matrix4f matrix = getMatrix(angle);

			shader.use();
			shader.setUniform("MVP", matrix);
			shader.bindTexture(texture, "myTexture", 0);

			vao.bindAndEnable();
			vao.draw();
			vao.disable();

			window.update();

			angle += ANGLE_STEP;
		}
	}

	private Matrix4f getMatrix(float angle) {
		return Matrix4f.scale(.1f / aspectRatio, .1f, 1)
				.multiply(Matrix4f.rotate(angle, 0, 0, 1))
				.multiply(Matrix4f.translate(5, 0, 0))
				.multiply(Matrix4f.scale(2, 2, 1));
	}

	@Override
	public void exit() {
	}

}
