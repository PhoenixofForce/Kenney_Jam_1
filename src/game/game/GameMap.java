package game.game;

import game.handler.FileHandler;

import java.util.Scanner;

public class GameMap {
	private int width, height;
	private boolean[][] walls_V;
	private boolean[][] walls_H;
	private float[] spawn;

	public GameMap() {
		Scanner s = new Scanner(FileHandler.loadFile("map/map.txt"));

		this.width = Integer.valueOf(s.nextLine());
		this.height = Integer.valueOf(s.nextLine());


		walls_V = new boolean[width + 1][height];
		walls_H = new boolean[width][height + 1];

		for (int y = 0; y < height; y++) {
			String line = s.nextLine();
			for (int x = 0; x <= width; x++){
				walls_V[x][y] = x == 0 || x == width || line.charAt(x)=='X';
			}
		}
		s.nextLine();

		for (int y = 0; y <= height; y++) {
			String line = s.nextLine();
			for (int x = 0; x < width; x++) {
				walls_H[x][y] = y == 0 || y == height || line.charAt(x)=='X';
			}
		}

		s.nextLine();
		spawn = new float[4];
		for (int i = 0; i < spawn.length; i++) {
			spawn[i] = Float.valueOf(s.nextLine());
		}
	}

	public float getFirstPlayerSpawnPointX() {
		return spawn[0];
	}

	public float getFirstPlayerSpawnPointY() {
		return spawn[1];
	}

	public float getSecondPlayerSpawnPointX() {
		return spawn[2];
	}

	public float getSecondPlayerSpawnPointY() {
		return spawn[3];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean hasWall(int x, int y, Direction d) {
		if (x + (d == Direction.RIGHT ? 1 : 0) < 0 || x >= width || y + (d == Direction.DOWN ? 1 : 0) < 0 || y >= height) return false;

		if (d == Direction.DOWN || d == Direction.UP) {
			return walls_H[x][y + (d == Direction.DOWN ? 1 : 0)];
		} else {
			return walls_V[x + (d == Direction.RIGHT ? 1 : 0)][y];
		}
	}

	public float getWallWidth() {
		return 0.2f;
	}
}
