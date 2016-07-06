package ch.wados.starfury.view;

import ch.wados.starfury.controller.Scene;
import ch.wados.starfury.controller.SceneManager;
import ch.wados.starfury.math.Matrix4f;
import ch.wados.starfury.opengl.Factory;
import ch.wados.starfury.opengl.Texture;

public class TestScene implements Scene {

	Window window;
	float aspectRatio = 1920f / 1080f;

	@Override
	public void enter(SceneManager manager) {
		window = manager.getWindow();

		Texture texture = Factory.loadTexture("test.png");
		SpriteRenderer sprite = new SpriteRenderer(1, 1, texture);
		Matrix4f camera = Matrix4f.scale(.1f / aspectRatio, .1f, 1);

		float ANGLE_STEP = .5f;
		float angle = 0;

		while (!window.isClosing()) {

			window.clear();

			Matrix4f matrix1 = getMatrix(angle);
			Matrix4f matrix2 = Matrix4f.rotate(120, 0, 0, 1).multiply(matrix1);
			Matrix4f matrix3 = Matrix4f.rotate(240, 0, 0, 1).multiply(matrix1);

			sprite.enable();

			sprite.render(matrix1, camera);
			sprite.render(matrix2, camera);
			sprite.render(matrix3, camera);

			sprite.disable();

			window.update();

			angle += ANGLE_STEP;
		}
	}

	private Matrix4f getMatrix(float angle) {
		return Matrix4f.rotate(angle, 0, 0, 1)
				.multiply(Matrix4f.translate(5, 0, 0))
				.multiply(Matrix4f.scale(2, 2, 1));
	}

	@Override
	public void exit() {
	}

}
