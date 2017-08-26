package game.game;

public class Projectile {

	public static final float acceleration = 0.01f;
	public static final float reibung = 0.9f;
	public static final float stopping = 0.9f;
	public static final long TIME = 5;
	public static final float TIME_PER_DEGREE = 2;

	private float x, y;
	private float vx, vy;
	private float mx, my;
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
	public void update(long time) {
		lastTime += time;
		while (lastTime > TIME) {
			vx += mx * acceleration;
			vy += my * acceleration;
			vx *= reibung;
			vy *= reibung;
			if (mx == 0 && my == 0) {
				vx *= stopping;
				vy *= stopping;
			}

			lastTime -= TIME;

			if(allowed(x+vx, y)) x += vx;
			else {
				//TODO: Kill and Respawn
				vx = 0;
			}
			if(allowed(x, y+vy)) y += vy;
			else {
				//TODO: Kill and Respawn
				vy = 0;
			}
		}

		rotation = targetRotation;
	}

	private boolean allowed(float x, float y) {
		if (game.getFirstPlayer() != shooter && CollisionUtil.collides(game.getFirstPlayer(), x, y, this.getWidth(), this.getHeight())) return false;
		if (game.getSecondPlayer() != shooter && CollisionUtil.collides(game.getSecondPlayer(), x, y, this.getWidth(), this.getHeight())) return false;

		float ww = game.getMap().getWallWidth();
		for (int wx = (int)x - 1; wx <= (int)x + 1; wx++) {
			for (int wy = (int)y - 1; wy <= (int)y + 1; wy++) {
				if (game.getMap().hasWall(wx, wy, Direction.DOWN)) {
					if (CollisionUtil.collides(x, y, this.getWidth(), this.getHeight(), wx, wy+1-ww, 1, 2*ww)) return false;
				}
				if (game.getMap().hasWall(wx, wy, Direction.RIGHT)) {
					if (CollisionUtil.collides(x, y, this.getWidth(), this.getHeight(), wx+1-ww, wy, 2*ww, 1)) return false;
				}
			}
		}

		return true;
	}

	public float getRotation() {
		return rotation;
	}

	public float getWidth() {
		return 1f;
	}

	public float getHeight() {
		return 1f;
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

	public void updateWalkingDirection(float mx, float my) {
		this.mx = mx;
		this.my = my;

		if (mx * mx + my *my > 0) {
			targetRotation = (float) Math.toDegrees(Math.acos(my/(Math.sqrt(Math.pow(mx, 2) + Math.pow(my, 2)))));
			if(mx < 0) targetRotation = 360 - targetRotation;

			targetRotation = 360 - targetRotation;
		}
	}

	public Player getShooter() {
		return shooter;
	}
}
