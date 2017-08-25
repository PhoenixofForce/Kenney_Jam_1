package game.game;

public class GameMap {
	private int width, height;
	private boolean[][] walls_V;
	private boolean[][] walls_H;

	public GameMap(int width, int height) {
		this.width = width;
		this.height = height;

		walls_V = new boolean[width + 1][height];
		walls_H = new boolean[width][height + 1];

		for (int x = 0; x <= width; x++) {
			for (int y = 0; y <= height; y++) {
				if (y < height) walls_V[x][y] = x == 0 || x == width || Math.random() < 0.1;
				if (x < width) walls_H[x][y] = y == 0 || y == height|| Math.random() < 0.1;
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean hasWall(int x, int y, Direction d) {
		if (x < 0 || x >= width || y < 0 || y >= height) return false;

		if (d == Direction.DOWN || d == Direction.UP) {
			return walls_H[x][y + (d == Direction.DOWN ? 1 : 0)];
		} else {
			return walls_V[x + (d == Direction.RIGHT ? 1 : 0)][y];
		}
	}
}
