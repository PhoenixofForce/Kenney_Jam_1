import game.window.Window;
import game.window.view.MainMenu;

public class Main {

	public static void main(String[] args) {

		Window w = new Window();
		w.updateView(new MainMenu());

	}
}
