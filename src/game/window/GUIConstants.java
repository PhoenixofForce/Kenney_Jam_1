package game.window;

import java.awt.*;
import java.io.IOException;

public class GUIConstants {
	public static final int MAXIMUM_DRAG_DISTANCE_FOR_CLICK = 10;
	public static final double ZOOM = 1.1;

	public static final long CAMERA_TIME = 500;
	public static final long ZOOM_TIME = 200;

	public static Font FONT = null;

	static {
		try {

			FONT = Font.createFont(Font.PLAIN, GUIConstants.class.getResourceAsStream(("res/font/kenvector_future_thin.ttf")));				//Dont works
		} catch (FontFormatException e) {
			FONT = new Font("Arial", Font.BOLD, 12);
			e.printStackTrace();
		} catch (IOException e) {
			FONT = new Font("Arial", Font.BOLD, 12);
			e.printStackTrace();
		}
	}

	public static final double BUTTON_HOVER_SIZE = 0.9;
}
