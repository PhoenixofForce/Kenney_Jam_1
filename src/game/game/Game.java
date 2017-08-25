package game.game;

import game.window.Camera;

public class Game {

	private GameMap map;
	private Player first, second;

	private Camera cam;

	public Game() {
		map = new GameMap(20, 15);
		cam = new Camera();
		cam.setZoom(1);
		cam.setPosition(0, 0);

		first = new Player(this);
		second = new Player(this);
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

	public void update(long time) {
		first.update(time);
		second.update(time);
	}
}
