package ch.wados.starfury;

import ch.wados.starfury.controller.GameController;
import ch.wados.starfury.controller.GuiController;

public class GameLauncher {

	private GuiController guiController;
	private GameController gameController;

	public static void main(String[] args) {
		new GameLauncher();
	}

	public GameLauncher() {
		buildUpComponents();
		start();
	}

	private void start() {
		this.guiController.openWindow();
		this.gameController.load();
		this.gameController.startGame();
	}

	private void buildUpComponents() {
		this.guiController = new GuiController();

		this.gameController = new GameController(this.guiController);
	}

}
