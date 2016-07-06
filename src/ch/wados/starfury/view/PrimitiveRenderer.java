package ch.wados.starfury.view;

import ch.wados.starfury.math.Matrix4f;

public interface PrimitiveRenderer {

	void enable();

	void render(Matrix4f model, Matrix4f camera);

	void disable();
	
	void destroy();

}
