package game.game;

import game.window.Camera;

public class Game {

	private GameMap map;
	private Player first, second;

	private Camera cam;

	public Game() {
		map = new GameMap(64, 34);
		cam = new Camera();
		cam.setZoom(1);
		cam.setPosition(0, 0);
	}

	public GameMap getMap() {
		return map;
	}

	public Camera getCamera() {
		return cam;
	}

	public Player getFirstPlayer() {
		return first;
	}

	public Player getSecondPlayer() {
		return second;
	}
}
