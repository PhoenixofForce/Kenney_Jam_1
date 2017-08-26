package game.game;

import game.window.Camera;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private GameMap map;
	private Player first, second;
	private int fScore = 0, sScore = 0;

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
			if(p != null) {
				boolean b = p.update(time);
				if (b) {
					projectiles.remove(p);
					i--;
				}
			}
		}
	}

	public void summonProjectile(Player s, float rot) {
		Projectile p = new Projectile(s, this);
		p.updateFlyingDirection(-(float)Math.sin(Math.toRadians(rot))/9, (float)Math.cos(Math.toRadians(rot))/9);
		projectiles.add(p);
	}

	public int getFirstPlayerScore() {
		return fScore;
	}

	public int getSecondPlayerScore() {
		return sScore;
	}

	public void score(Player p) {
		if(p.equals(first)) fScore++;
		else sScore++;
		System.out.println(fScore + " : " + sScore);
	}
}
