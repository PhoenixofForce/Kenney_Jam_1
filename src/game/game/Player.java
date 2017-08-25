package game.game;

public class Player {

	private Game game;

	public Player(Game game) {
		this.game = game;
	}

	public void shoot() {
		game.getCamera().addScreenshake(100.0000001f);
	}

}
