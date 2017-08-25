package game.game;

public enum Direction {
	UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(0, 1);

	int vx, vy;

	Direction(int vx, int vy) {
		this.vx = vx;
		this.vy = vy;
	}

	public int getVx() {
		return vx;
	}

	public int getVy() {
		return vy;
	}
}
