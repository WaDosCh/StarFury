package ch.wados.starfury.physics.simple;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dyn4j.dynamics.Body;

import ch.wados.starfury.physics.api.PhysicsManager;

public class SandboxController {

	private final SandboxFrame frame;
	private final PhysicsManager world;
	private long last;
	private volatile boolean stopped = false;

	public SandboxController(final int width, final int height,
			final double render_scale) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.frame = new SandboxFrame(width, height, render_scale, this::stop);
		this.world = new SimplePhysicsManager();
	}

	public final void stop() {
		this.stopped = true;
	}

	public final void start() {
		this.worldInit(world);
		this.frame.setVisible(true);
		this.last = System.nanoTime();
		this.lastSample = this.last;
		Thread thread = new Thread() {
			public void run() {
				while (!stopped) {
					gameStep();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
	}

	private long lastSample;
	private int subsampleIndex = 0;

	private final NumberFormat formatter = new DecimalFormat("#0.0");

	private final void gameStep() {
		List<Body> bodies = this.world.getSpawnedEntities().stream()
				.map(e -> ((SimpleEntity) e).body).collect(Collectors.toList());
		this.frame.render(bodies);
		long time = System.nanoTime();
		long diff = time - this.last;
		long sampleDiff = time - this.lastSample;
		subsampleIndex = (subsampleIndex + 1) % 50;
		if (subsampleIndex == 0) {
			this.frame.setTitle("FPS: "
					+ formatter.format(50.0 / (sampleDiff / 1.0e9)) + " Tick: "
					+ formatter.format((sampleDiff / 1.0e6) / 50) + "ms");
			this.lastSample = time;
		}
		this.last = time;
		double elapsedTime = diff / 1.0e9;
		this.world.stepWorld(elapsedTime);
	}

	public final SandboxFrame getFrame() {
		return this.frame;
	}

	protected void worldInit(final PhysicsManager world) {
		// default no need
	}

}
