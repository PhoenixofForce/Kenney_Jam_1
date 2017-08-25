package game.window.view;


import game.game.Direction;
import game.game.Game;
import game.game.GameMap;
import game.handler.TextureHandler;
import game.window.Camera;
import game.window.Window;
import game.window.controlls.Controller;
import game.window.controlls.KeyInputListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameView extends View implements Controller {
	public static final int SIZE = 64;
	private BufferedImage buffer;
	private Window w;
	private boolean running;
	private KeyInputListener key;
	private Game game;
	private BufferedImage mapBuffer;
	private int bw = 0, bh = 0;
	private BufferedImage gameBuffer;
	private int gbw, gbh;

	@Override
	public void init(Window window) {
		TextureHandler.loadImagePngSpriteSheet("walls", "game/map/walls");
		TextureHandler.loadImagePng("ground", "game/map/ground");
		TextureHandler.loadImagePngSpriteSheet("tanks", "game/tank/sheet_tanks");

		this.w = window;
		key = new KeyInputListener(this);
		w.addKeyInputListener(key);
		running = true;

		game = new Game();
		redrawMap();

		new Thread(() -> {
			while (running) {
				draw();
			}
		}).start();

		new Thread(() -> {
			long t1 = System.currentTimeMillis();
			while (running) {
				long t2 = System.currentTimeMillis();
				updatePlayerWalking();
				game.update(t2 - t1);
				t1 = t2;
			}
		}).start();
	}

	private void redrawMap() {
		GameMap map = game.getMap();

		mapBuffer = new BufferedImage(SIZE * map.getWidth(), SIZE * map.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) mapBuffer.getGraphics();

		g.setColor(Color.RED);
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				g.drawImage(TextureHandler.getImagePng("ground"), x * SIZE, y * SIZE, SIZE, SIZE, null);

				String wall = "";
				boolean t = map.hasWall(x, y, Direction.UP);
				boolean b = map.hasWall(x, y, Direction.DOWN);
				boolean l = map.hasWall(x, y, Direction.LEFT);
				boolean r = map.hasWall(x, y, Direction.RIGHT);

				g.setStroke(new BasicStroke(3));

				if (t && l) wall = "walls_corner_tl";
				else if (t && r) wall = "walls_corner_tr";
				else if (t) wall = "walls_edge_t";
				else if (b && l) wall = "walls_corner_bl";
				else if (b && r) wall = "walls_corner_br";
				else if (b) wall = "walls_edge_b";
				else if (l) wall = "walls_edge_l";
				else if (r) wall = "walls_edge_r";

				if (!wall.equals("")) {
					g.drawImage(TextureHandler.getImagePng(wall), x * SIZE, y * SIZE, SIZE, SIZE, null);
				}


				if (t) g.drawLine(x * SIZE, y * SIZE, (x + 1) * SIZE, y * SIZE);
				if (b) g.drawLine(x * SIZE, (y + 1) * SIZE, (x + 1) * SIZE, (y + 1) * SIZE);
				if (l) g.drawLine(x * SIZE, y * SIZE, x * SIZE, (y + 1) * SIZE);
				if (r) g.drawLine((x + 1) * SIZE, y * SIZE, (x + 1) * SIZE, (y + 1) * SIZE);
			}
		}
	}

	public void draw() {
		if (w.getPanel().getWidth() != bw || w.getPanel().getHeight() != bh) {
			buffer = new BufferedImage(w.getPanel().getWidth(), w.getPanel().getHeight(), BufferedImage.TYPE_INT_ARGB);
			bw = w.getPanel().getWidth();
			bh = w.getPanel().getHeight();
		}

		Graphics2D g = (Graphics2D) buffer.getGraphics();

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, w.getPanel().getWidth(), w.getPanel().getHeight());

		Camera c = game.getCamera();
		c.update();
		g.translate(c.x, c.y);

		GameMap map = game.getMap();

		if (SIZE * map.getWidth() != gbw || SIZE * map.getHeight() != gbh) {
			gameBuffer = new BufferedImage(SIZE * map.getWidth(), SIZE * map.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			gbw = SIZE * map.getWidth();
			gbh = SIZE * map.getHeight();
		}
		Graphics2D gameGraphics = (Graphics2D) gameBuffer.getGraphics();
		gameGraphics.drawImage(mapBuffer, 0, 0, null);

		gameGraphics.translate((int) (SIZE * (game.getFirstPlayer().getX() + game.getFirstPlayer().getWidth()/2)), (int) (SIZE * (game.getFirstPlayer().getY()+game.getFirstPlayer().getHeight()/2)));
		gameGraphics.rotate(Math.toRadians(game.getFirstPlayer().getRotation()));
		gameGraphics.drawImage(TextureHandler.getImagePng("tanks_tankRed"), -(int) (SIZE * game.getFirstPlayer().getWidth())/2, -(int) (SIZE * game.getFirstPlayer().getHeight())/2, (int) (SIZE * game.getFirstPlayer().getWidth()), (int) (SIZE * game.getFirstPlayer().getHeight()), null);
		gameGraphics.rotate(Math.toRadians(-game.getFirstPlayer().getRotation()));
		gameGraphics.translate(-(int) (SIZE * (game.getFirstPlayer().getX() + game.getFirstPlayer().getWidth()/2)), -(int) (SIZE * (game.getFirstPlayer().getY()+game.getFirstPlayer().getHeight()/2)));

		gameGraphics.translate((int) (SIZE * (game.getSecondPlayer().getX() + game.getSecondPlayer().getWidth()/2)), (int) (SIZE * (game.getSecondPlayer().getY()+game.getSecondPlayer().getHeight()/2)));
		gameGraphics.rotate(Math.toRadians(game.getSecondPlayer().getRotation()));
		gameGraphics.drawImage(TextureHandler.getImagePng("tanks_tankGreen"), -(int) (SIZE * game.getSecondPlayer().getWidth())/2, -(int) (SIZE * game.getSecondPlayer().getHeight())/2, (int) (SIZE * game.getFirstPlayer().getWidth()), (int) (SIZE * game.getFirstPlayer().getHeight()), null);
		gameGraphics.rotate(Math.toRadians(-game.getSecondPlayer().getRotation()));
		gameGraphics.translate(-(int) (SIZE * (game.getSecondPlayer().getX() + game.getSecondPlayer().getWidth()/2)), -(int) (SIZE * (game.getSecondPlayer().getY()+game.getSecondPlayer().getHeight()/2)));


		if (gameBuffer.getWidth() / (1f * gameBuffer.getHeight()) >= buffer.getWidth() / (1f * buffer.getHeight())) {
			float height = (gameBuffer.getHeight() / (1f * gameBuffer.getWidth())) * buffer.getWidth();
			g.drawImage(gameBuffer, 0, (int) ((buffer.getHeight() - height) / 2), buffer.getWidth(), (int) height, null);
		} else {
			float width = (gameBuffer.getWidth() / (1f * gameBuffer.getHeight())) * buffer.getHeight();
			g.drawImage(gameBuffer, (int) ((buffer.getWidth() - width) / 2), 0, (int) width, buffer.getHeight(), null);
		}

		g.translate(-c.x, -c.y);
		w.getPanel().getGraphics().drawImage(buffer, 0, 0, null);
	}

	private void updatePlayerWalking() {
		float mx = 0, my = 0;
		if (key.isPressed(KeyEvent.VK_D)) mx += 1;
		if (key.isPressed(KeyEvent.VK_A)) mx -= 1;
		if (key.isPressed(KeyEvent.VK_W)) my -= 1;
		if (key.isPressed(KeyEvent.VK_S)) my += 1;
		if (mx * mx + my * my != 0) {
			float a = (float) Math.sqrt(mx * mx + my * my);
			mx /= a;
			my /= a;
		}
		game.getFirstPlayer().updateWalkingDirection(mx, my);


		mx = 0;
		my = 0;
		if (key.isPressed(KeyEvent.VK_RIGHT)) mx += 1;
		if (key.isPressed(KeyEvent.VK_LEFT)) mx -= 1;
		if (key.isPressed(KeyEvent.VK_UP)) my -= 1;
		if (key.isPressed(KeyEvent.VK_DOWN)) my += 1;
		if (mx * mx + my * my != 0) {
			float a = (float) Math.sqrt(mx * mx + my * my);
			mx /= a;
			my /= a;
		}
		game.getSecondPlayer().updateWalkingDirection(mx, my);
	}


	@Override
	public void onKeyType(int i) {
		if (i == KeyEvent.VK_E) game.getFirstPlayer().shoot();
		if (i == KeyEvent.VK_MINUS) game.getSecondPlayer().shoot();
	}

	@Override
	public void onMouseWheel(double v) {
	}

	@Override
	public void onMouseDrag(int i, int i1) {
	}

	@Override
	public void onMouseClick(int i, int i1) {
	}

	@Override
	public void stop() {
		running = false;
	}
}
