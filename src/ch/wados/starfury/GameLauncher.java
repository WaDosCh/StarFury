package ch.wados.starfury;

import ch.wados.starfury.controller.GameController;
import ch.wados.starfury.controller.SceneManager;
import ch.wados.starfury.view.Window;

public class GameLauncher {

	private Window window;
	private GameController gameController;
	private SceneManager sceneManager;

	public static void main(String[] args) {
		new GameLauncher();
	}

	public GameLauncher() {
		buildUpComponents();
		start();
	}

	private void start() {
		this.window.openWindow();

		this.sceneManager.changeToScene("Game");
	}

	private void buildUpComponents() {
		this.window = new Window();
		this.sceneManager = new SceneManager(this.window);

		this.gameController = new GameController();

		this.sceneManager.addScene(this.gameController, "Game");
	}

}
