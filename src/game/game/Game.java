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

	private boolean swap = false;

	public Game() {
		map = new GameMap();
		cam = new Camera();
		cam.setZoom(1);
		cam.setPosition(0, 0);

		projectiles = new ArrayList<>();

		first = new Player(this, map.getFirstPlayerSpawnPointX(), map.getFirstPlayerSpawnPointY());
		second = new Player(this, map.getSecondPlayerSpawnPointX(), map.getSecondPlayerSpawnPointY());
	}

	public GameMap getMap() {
		return map;
	}

	public Camera getCamera() {
		return cam;
	}

	public Player getFirstPlayer() {
		if(!swap) return first;
		else return second;
	}

	public Player getSecondPlayer() {
		if(!swap) return second;
		else return first;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	long lastTime = 0;
	public void update(long time) {
		first.update(time);
		second.update(time);

		lastTime += time;
		if(lastTime >= 5000L && Math.random() < 0.000000015) {
			lastTime = 0L;
			swap = !swap;
		}

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
		Projectile p = new Projectile(s, this, Math.random() > 0.034);
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
		if((p.equals(first) && !swap) || (p.equals(second) && swap)) fScore++;
		else sScore++;
		System.out.println(fScore + " : " + sScore);
	}
}
