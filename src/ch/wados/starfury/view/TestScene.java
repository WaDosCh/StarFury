package ch.wados.starfury.view;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import ch.wados.starfury.controller.Scene;
import ch.wados.starfury.controller.SceneManager;

public class TestScene implements Scene {

	Window window;

	@Override
	public void enter(SceneManager manager) {
		window = manager.getWindow();

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LESS);

		ShaderProgram shader = ShaderFactory.createShader("test.vs", "test.fs");

		float vertices[] = { -1, -1, 0, 0, -1, 0, 0, 1, 0, -1, -1, 0, 0, 1, 0,
				-1, 1, 0 };
		float colors[] = { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1,
				1 };

		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		int vertex_buffer = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_buffer);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

		int color_buffer = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, color_buffer);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colors, GL15.GL_STATIC_DRAW);

		while (!window.isClosing()) {

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			GL20.glUseProgram(shader.getId());

			GL30.glBindVertexArray(vao);
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertex_buffer);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, color_buffer);
			GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);

			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);

			window.update();
		}
	}

	@Override
	public void exit() {
	}

}
