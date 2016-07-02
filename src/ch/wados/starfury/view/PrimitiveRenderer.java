package ch.wados.starfury.view;

import silvertiger.tutorial.lwjgl.math.Matrix4f;

public interface PrimitiveRenderer {
	
	void render(Matrix4f model, Matrix4f camera);

}
