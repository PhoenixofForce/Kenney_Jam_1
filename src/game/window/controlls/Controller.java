package game.window.controlls;

public interface Controller {

	void onKeyType(int code);
	void onMouseWheel(double percentage);
	void onMouseDrag(int xOff, int yOff);
	void onMouseClick(int x, int y);
}
