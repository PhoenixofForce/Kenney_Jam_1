package game.game;

import game.window.Camera;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private GameMap map;
	private Player first, second;

	private List<Projectile> projectiles;

	private Camera cam;

	public Game() {
		map = new GameMap(20, 15);
		cam = new Camera();
		cam.setZoom(1);
		cam.setPosition(0, 0);

		projectiles = new ArrayList<>();

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

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void update(long time) {
		first.update(time);
		second.update(time);
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			if(p != null) p.update(time);
		}
	}

	public void summonProjectile(Player s, float rot) {
		Projectile p = new Projectile(s, this);
		p.updateWalkingDirection(rot == 90 || rot == 45 || rot == 135? 1: rot == 0 || rot == 180? 0: -1, rot == 225 || rot == 180 || rot == 135? 1: rot == 90 || rot == 270? 0: -1);
		projectiles.add(p);
	}
}
