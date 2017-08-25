package game.window.view;

import game.window.Window;
import game.window.controlls.Controller;

public abstract class View {

	protected boolean running;

	public View() {
		running = true;
	}

	public abstract void init(Window w);

	public void stop() {
		running = false;
	}
	public void changeSize() {

	}

	public void draw() {}
}