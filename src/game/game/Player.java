package game.game;

public class Player {

	private Game game;

	public static final float acceleration = 0.004f;
	public static final float reibung = 0.9f;
	public static final float stopping = 0.9f;
	public static final long TIME = 5;

	private float x, y;
	private float vx, vy;
	private float mx, my;


	public Player(Game game) {
		x = 0;
		y = 0;
		vx = 0;
		vy = 0;

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

			x += vx;
			y += vy;
		}
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
	}

	public void shoot() {
		game.getCamera().addScreenshake(100.0000001f);
	}

}
