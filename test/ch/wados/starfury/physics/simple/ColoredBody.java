package ch.wados.starfury.physics.simple;

import java.awt.Color;

import org.dyn4j.dynamics.Body;

public class ColoredBody extends Body {

	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ColoredBody(Color color) {
		super();
		this.color = color;
	}

	public ColoredBody(Color color, int fixtures) {
		super(fixtures);
		this.color = color;
	}

	public ColoredBody() {
		this.color = new Color((float) Math.random() * 0.5f + 0.5f, (float) Math.random() * 0.5f + 0.5f,
				(float) Math.random() * 0.5f + 0.5f);
	}

	public ColoredBody(int fixtures) {
		super(fixtures);
		this.color = new Color((float) Math.random() * 0.5f + 0.5f, (float) Math.random() * 0.5f + 0.5f,
				(float) Math.random() * 0.5f + 0.5f);
	}

}
