package ch.wados.starfury.controller;

import java.util.HashMap;

import ch.wados.starfury.view.Window;

public class SceneManager {

	private HashMap<String, Scene> scenes;
	private Scene currentScene;
	private Window window;

	public SceneManager(Window window) {
		this.window = window;

		this.scenes = new HashMap<>();
		this.currentScene = null;
	}

	public Window getWindow() {
		return this.window;
	}

	public void addScene(Scene scene, String name) {
		this.scenes.put(name, scene);
	}

	public void changeToScene(String name) {
		if (!this.scenes.containsKey(name))
			throw new IllegalArgumentException("Scene with the name " + name
					+ " does not exist");
		if (this.currentScene != null)
			this.currentScene.exit();
		this.currentScene = this.scenes.get(name);
		this.currentScene.enter(this);
	}
}
