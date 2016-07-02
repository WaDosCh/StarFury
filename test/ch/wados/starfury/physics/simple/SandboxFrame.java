package ch.wados.starfury.physics.simple;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.JFrame;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

public class SandboxFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final Canvas canvas;
	private final int width, height;
	private final double scale;

	public SandboxFrame(final int width, final int height, final double scale, Runnable stop) {
		super("Graphics2D Example");
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Dimension size = new Dimension(800, 600);
		this.canvas = new Canvas();
		this.canvas.setPreferredSize(size);
		this.canvas.setMinimumSize(size);
		this.canvas.setMaximumSize(size);
		this.add(this.canvas);
		this.setResizable(false);
		this.pack();
		this.canvas.setIgnoreRepaint(true);
		this.canvas.createBufferStrategy(2);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop.run();
				super.windowClosing(e);
			}
		});
	}

	public void render(List<Body> bodies) {
		Graphics2D g = (Graphics2D) this.canvas.getBufferStrategy().getDrawGraphics();
		AffineTransform yFlip = AffineTransform.getScaleInstance(1, -1);
		AffineTransform move = AffineTransform.getTranslateInstance(400, -300);
		g.transform(yFlip);
		g.transform(move);
		g.setColor(Color.WHITE);
		g.fillRect(-width / 2, -height / 2, width, height);
		bodies.forEach(b -> render(b, g));
		g.dispose();
		BufferStrategy strategy = this.canvas.getBufferStrategy();
		if (!strategy.contentsLost()) {
			strategy.show();
		}
		Toolkit.getDefaultToolkit().sync();
	}

	private void render(Body b, Graphics2D g) {
		AffineTransform ot = g.getTransform();
		AffineTransform lt = new AffineTransform();
		Transform t = b.getTransform();
		lt.translate(t.getTranslationX() * this.scale, t.getTranslationY() * this.scale);
		lt.rotate(t.getRotation());
		g.transform(lt);

		Color color = (b instanceof ColoredBody) ? ((ColoredBody) b).getColor() : null;
		for (BodyFixture fixture : b.getFixtures()) {
			Convex convex = fixture.getShape();
			Graphics2DRenderer.render(g, convex, this.scale, color);
		}

		Stroke str = g.getStroke();
		g.setStroke(new BasicStroke(3f));

		// Render Force
		g.setColor(Color.BLACK);
		Vector2 f = b.getTransform().getInverseTransformedR(b.getForce());
		g.drawLine(0, 0, (int) (1 * f.x), (int) (1 * f.y));
		// Render Velocity
		g.setColor(Color.BLUE);
		Vector2 v = b.getTransform().getInverseTransformedR(b.getLinearVelocity());
		g.drawLine(0, 0, (int) (10 * v.x), (int) (10 * v.y));

		g.setStroke(str);
		g.setTransform(ot);
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

}
