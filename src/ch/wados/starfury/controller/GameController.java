package ch.wados.starfury.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.dyn4j.geometry.Vector2;
import org.lwjgl.opengl.GL11;

import ch.wados.starfury.model.Timer;
import ch.wados.starfury.opengl.Factory;
import ch.wados.starfury.opengl.Program;
import ch.wados.starfury.physics.simple.SimplePhysicsManager;
import silvertiger.tutorial.lwjgl.core.Game;

public class GameController implements Scene {

	private static final int TARGET_FPS_UPS = 60;

	private SimplePhysicsManager physicsManager;
	private SceneManager manager;
	private Timer timer;

	public GameController() {
		this.physicsManager = new SimplePhysicsManager();
		this.timer = new Timer();
	}

	@Override
	public void exit() {
		this.shader.delete();
		// TODO Auto-generated method stub

	}

	private Program shader;

	@Override
	public void enter(SceneManager manager) {
		this.manager = manager;
		this.physicsManager.initialiseWorld(new Vector2(0, 0));

		this.shader = Factory.loadProgram("texture");

		this.loop();
	}

	private void loop() {
		float delta;
		boolean running = true;

		while (running) {
			/* Check if game should close */
			if (this.manager.getWindow().isClosing()) {
				running = false;
			}

			/* Get delta time and update the accumulator */
			delta = this.timer.getDelta();

			/* Handle input */
			// input();

			/* Update game and timer UPS if enough time has passed */
			update(delta);
			timer.updateUPS();

			/* Render game and update timer FPS */
			render();
			timer.updateFPS();

			/* Update timer */
			timer.update();

			/* Draw FPS, UPS and Context version */
			// int height = renderer.getDebugTextHeight("Context");
			// renderer.drawDebugText("FPS: " + timer.getFPS() + " | UPS: "
			// + timer.getUPS(), 5, 5 + height);
			// renderer.drawDebugText("Context: "
			// + (renderer.hasDefaultContext() ? "3.2 core" : "2.1"), 5, 5);

			/* Update window to show the new screen */
			this.manager.getWindow().update();

			/* Synchronize if v-sync is disabled */
			if (!this.manager.getWindow().isVSyncEnabled()) {
				sync(TARGET_FPS_UPS);
			}
		}
	}

	private void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

		this.manager.getWindow().update();

		// TODO Auto-generated method stub

	}

	private void update(float delta) {
		this.physicsManager.stepWorld(delta);
	}

	/**
	 * Synchronizes the game at specified frames per second.
	 *
	 * @param fps
	 *            Frames per second
	 */
	private void sync(int fps) {
		double lastLoopTime = timer.getLastLoopTime();
		double now = timer.getTime();
		float targetTime = 1f / fps;

		while (now - lastLoopTime < targetTime) {
			Thread.yield();

			/*
			 * This is optional if you want your game to stop consuming too much
			 * CPU but you will loose some accuracy because Thread.sleep(1)
			 * could sleep longer than 1 millisecond
			 */
			try {
				Thread.sleep(1);
			} catch (InterruptedException ex) {
				Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null,
						ex);
			}

			now = timer.getTime();
		}
	}
}
