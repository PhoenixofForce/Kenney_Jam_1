package game.window.controlls;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseInputListener extends MouseAdapter {

	private static final int MAXIMUM_DRAG_DISTANCE_FOR_CLICK = 10;

	private Controller game;

	private boolean mousePressedInGame = false;
	private int lastX = 0, lastY = 0;
	private int mouseX = 0, mouseY = 0;
	private int totalDistanceDragged = 0;

	public MouseInputListener(Controller game) {
		this.game = game;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		game.onMouseWheel(e.getScrollAmount() * e.getPreciseWheelRotation());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//Update dragging
		if (mousePressedInGame) {
			totalDistanceDragged += Math.abs(e.getX()-lastX) + Math.abs(e.getY()-lastY);
			game.onMouseDrag(e.getX() - lastX, e.getY() - lastY);
			lastX = e.getX();
			lastY = e.getY();
		}

		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		game.onMouseClick(x, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressedInGame = true;
		lastX = e.getX();
		lastY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		//When the cam was moved less then "MAXIMUM_DRAG_DISTANCE_FOR_CLICK" then it should still count as a click
		if (mousePressedInGame && totalDistanceDragged > 0) {
			if (totalDistanceDragged <= MAXIMUM_DRAG_DISTANCE_FOR_CLICK) {
				int x = e.getX();
				int y = e.getY();
				game.onMouseClick(x, y);
			}

			totalDistanceDragged = 0;
			mousePressedInGame = false;
		}
	}

	/**
	 * @return x value of the position from the mouse
	 */
	public int getMouseX() {
		return mouseX;
	}

	/**
	 * @return y value of the position from the mouse
	 */
	public int getMouseY() {
		return mouseY;
	}
}