package game.game;

import game.window.Camera;

public class Game {

	private GameMap map;
	private Player first, second;

	private Camera cam;

	public Game() {

		map = new GameMap(20, 20);
		cam = new Camera();



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
