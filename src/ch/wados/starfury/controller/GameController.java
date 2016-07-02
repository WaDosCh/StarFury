package ch.wados.starfury.controller;

import java.io.IOException;

import org.dyn4j.geometry.Vector2;

import ch.wados.starfury.physics.simple.SimplePhysicsManager;

public class GameController {

	private GuiController guiController;
	private SimplePhysicsManager physicsManager;
	private GameRenderingManager renderingManager;

	public GameController(GuiController guiController) {
		this.guiController = guiController;

		this.physicsManager = new SimplePhysicsManager();
		this.renderingManager = new GameRenderingManager();
	}

	public void startGame() {
		this.guiController.setRenderingManager(this.renderingManager);
		this.physicsManager.initialiseWorld(new Vector2(0, 0));

		this.guiController.startLoop();
	}

	public void load() {
		try {
			this.renderingManager.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
