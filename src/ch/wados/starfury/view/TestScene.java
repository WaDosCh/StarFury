package ch.wados.starfury.view;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import ch.wados.starfury.controller.Scene;
import ch.wados.starfury.controller.SceneManager;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class TestScene implements Scene {

	Window window;

	@Override
	public void enter(SceneManager manager) {
		window = manager.getWindow();

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LESS);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		Texture texture = Texture.loadTexture("test.png");

		ShaderProgram shader = ShaderFactory.createShader("test.vs", "test.fs");

		float vertices[] = { -1, -1, 0, 1, -1, 0, 1, 1, 0, -1, -1, 0, 1, 1, 0,
				-1, 1, 0 };
		float text[] = { 0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1 };

		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		int vertex_buffer = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_buffer);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

		int text_buffer = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, text_buffer);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, text, GL15.GL_STATIC_DRAW);

		float ANGLE_STEP = .5f;
		float angle = 0;
		float aspectRatio = 1920f / 1080f;

		while (!window.isClosing()) {

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			GL20.glUseProgram(shader.getId());

			GL30.glBindVertexArray(vao);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_buffer);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, text_buffer);
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);

			Matrix4f mat = Matrix4f.scale(.1f / aspectRatio, .1f, 1)
					.multiply(Matrix4f.rotate(angle, 0, 0, 1))
					.multiply(Matrix4f.translate(5, 0, 0))
					.multiply(Matrix4f.scale(2, 2, 1));

			int mvp_id = GL20.glGetUniformLocation(shader.getId(), "MVP");
			GL20.glUniformMatrix4fv(mvp_id, false, mat.getBuffer());
			
			texture.bind();

			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);

			window.update();

			angle += ANGLE_STEP;
		}
	}

	@Override
	public void exit() {
	}

}
