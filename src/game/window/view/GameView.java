package game.window.view;


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

	private Window w;
	private boolean running;

	private KeyInputListener key;

	private Game game;

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
			while(running) {
				draw();
			}
		}).start();

		new Thread(() -> {
			long t1 = System.currentTimeMillis();
			while(running) {
				long t2 = System.currentTimeMillis();
				updatePlayerWalking();
				game.update(t2 - t1);
				t1 = t2;
			}
		}).start();
	}

	private BufferedImage mapBuffer;
	private void redrawMap() {
		GameMap map = game.getMap();

		mapBuffer = new BufferedImage(SIZE * map.getWidth(), SIZE * map.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) mapBuffer.getGraphics();

		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				g.drawImage(TextureHandler.getImagePng("ground"), x*SIZE, y*SIZE, SIZE, SIZE, null);
			}
		}
	}

	public void draw() {
		BufferedImage buffer = new BufferedImage(w.getPanel().getWidth(), w.getPanel().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) buffer.getGraphics();

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, w.getPanel().getWidth(), w.getPanel().getHeight());

		Camera c = game.getCamera();
		c.update();
		g.translate(c.x, c.y);

		GameMap map = game.getMap();

		BufferedImage gameBuffer = new BufferedImage(SIZE * map.getWidth(), SIZE * map.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D gameGraphics = (Graphics2D) gameBuffer.getGraphics();
		gameGraphics.drawImage(mapBuffer, 0, 0, null);

		gameGraphics.drawImage(TextureHandler.getImagePng("tanks_tankRed"), (int) (SIZE*game.getFirstPlayer().getX()), (int) (SIZE*game.getFirstPlayer().getY()), (int) (SIZE*game.getFirstPlayer().getWidth()),(int) (SIZE*game.getFirstPlayer().getHeight()), null);
		gameGraphics.drawImage(TextureHandler.getImagePng("tanks_tankGreen"), (int) (SIZE*game.getSecondPlayer().getX()), (int) (SIZE*game.getSecondPlayer().getY()), (int) (SIZE*game.getSecondPlayer().getWidth()),(int) (SIZE*game.getSecondPlayer().getHeight()), null);


		if (gameBuffer.getWidth() / (1f*gameBuffer.getHeight()) >= buffer.getWidth() / (1f*buffer.getHeight())) {
			float height = (gameBuffer.getHeight() / (1f*gameBuffer.getWidth())) * buffer.getWidth();
			g.drawImage(gameBuffer, 0, (int) ((buffer.getHeight() - height)/2), buffer.getWidth(), (int) height, null);
		} else {
			float width = (gameBuffer.getWidth() / (1f*gameBuffer.getHeight())) * buffer.getHeight();
			g.drawImage(gameBuffer, (int) ((buffer.getWidth() - width)/2), 0, (int) width, buffer.getHeight(), null);
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
		if (mx*mx + my*my != 0) {
			float a = (float) Math.sqrt(mx*mx + my*my);
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
		if (mx*mx + my*my != 0) {
			float a = (float) Math.sqrt(mx*mx + my*my);
			mx /= a;
			my /= a;
		}
		game.getSecondPlayer().updateWalkingDirection(mx, my);
	}


	@Override
	public void onKeyType(int i) {
	}

	@Override
	public void onMouseWheel(double v) {}

	@Override
	public void onMouseDrag(int i, int i1) {}

	@Override
	public void onMouseClick(int i, int i1) {}

	@Override
	public void stop() {
		running = false;
	}
}
