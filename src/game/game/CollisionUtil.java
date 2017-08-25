package game.game;

public class CollisionUtil {
	public static boolean collides(float tx, float ty, float tw, float th, float rx, float ry, float rw, float rh) {
		return ((tx + tw) > rx
			&&  (rx + rw) > tx
			&&  (ty + th) > ry
			&&  (ry + rh) > ty);
	}

	public static boolean collides(Player p, float x2, float y2, float width2, float heigth2) {
		return collides(p.getX(), p.getY(), p.getWidth(), p.getHeight(), x2, y2, width2, heigth2);
	}
}
