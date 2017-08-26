package game.game;

public class Projectile {
	public static final long TIME = 5;
	public static final float TIME_PER_DEGREE = 2;

	private float x, y;
	private float vx, vy;
	private float rotation = 0;
	private float targetRotation = 0;

	private Game game;
	private Player shooter;

	public Projectile(Player shooter, Game game) {
		x = shooter.getX();
		y = shooter.getY();
		vx = 0;
		vy = 0;

		this.shooter = shooter;
		this.game = game;
	}

	long lastTime = 0;
	int collides = 0;
	public boolean update(long time) {
		lastTime += time;
		while (lastTime > TIME) {
			lastTime -= TIME;

			Player t1 = collidePlayer(x+vx, y);
			if (t1 != null) {
				return true;

			} else if (collideWall(x+vx, y)){
				updateFlyingDirection(-vx, vy);
				collides++;
			} else {
				x += vx;
			}

			t1 = collidePlayer(x, y+vy);
			if (t1 != null) {
				return true;

			} else if (collideWall(x, y+vy)){
				updateFlyingDirection(vx, -vy);
				collides++;
			} else {
				y += vy;
			}

			if (collides >= 5) {
				return true;
			}
		}

		rotation = targetRotation;
		return false;
	}

	private Player collidePlayer(float x, float y) {
		if (game.getFirstPlayer() != shooter && CollisionUtil.collides(game.getFirstPlayer(), x, y, this.getWidth(), this.getHeight())) return game.getFirstPlayer();
		if (game.getSecondPlayer() != shooter && CollisionUtil.collides(game.getSecondPlayer(), x, y, this.getWidth(), this.getHeight())) return game.getSecondPlayer();

		return null;
	}

	private boolean collideWall(float x, float y) {
		float ww = game.getMap().getWallWidth();
		for (int wx = (int)x - 1; wx <= (int)x + 1; wx++) {
			for (int wy = (int)y - 1; wy <= (int)y + 1; wy++) {
				if (game.getMap().hasWall(wx, wy, Direction.DOWN)) {
					if (CollisionUtil.collides(x, y, this.getWidth(), this.getHeight(), wx, wy+1-ww, 1, 2*ww)) return true;
				}
				if (game.getMap().hasWall(wx, wy, Direction.RIGHT)) {
					if (CollisionUtil.collides(x, y, this.getWidth(), this.getHeight(), wx+1-ww, wy, 2*ww, 1)) return true;
				}
			}
		}

		return false;
	}

	public float getRotation() {
		return rotation;
	}

	public float getWidth() {
		return 0.2f;
	}

	public float getHeight() {
		return 0.5f;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getVX() {
		return vx;
	}

	public float getVY() {
		return vy;
	}

	public void updateFlyingDirection(float vx, float vy) {
		this.vx = vx;
		this.vy = vy;

		if (vx * vx + vy *vy > 0) {
			targetRotation = (float) Math.toDegrees(Math.acos(vy/(Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)))));
			if(vx < 0) targetRotation = 360 - targetRotation;

			targetRotation = 360 - targetRotation;
		}
	}

	public Player getShooter() {
		return shooter;
	}
}
