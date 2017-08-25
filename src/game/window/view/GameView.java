package game.window.view;


import game.game.Game;
import game.window.Window;
import game.window.controlls.Controller;
import game.window.controlls.KeyInputListener;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameView extends View implements Controller {

	private Window w;
	private boolean running;

	private KeyInputListener key;

	private Game game;

	@Override
	public void init(Window window) {

		//TODO: TextureHandler load

		this.w = window;
		key = new KeyInputListener(this);
		w.addKeyInputListener(key);
		running = true;

		game = new Game();

		new Thread(() -> {
			while(running) {
				draw();



				try {
					Thread.sleep(1000/30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void draw() {
		BufferedImage buffer = new BufferedImage(w.getPanel().getWidth(), w.getPanel().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) buffer.getGraphics();

		g.setColor(new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255)));
		g.fillRect(0, 0, w.getPanel().getWidth(), w.getPanel().getHeight());



		w.getPanel().getGraphics().drawImage(buffer, 0, 0, null);
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
