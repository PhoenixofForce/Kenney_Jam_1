package game.window.view;

import game.handler.TextureHandler;
import game.window.Window;
import game.window.components.ImageButton;
import game.window.components.ImageTextButton;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MainMenu extends View {

	private JPanel panel;

	//TODO: ImageTextButtons
	private ImageTextButton button_StartGame;		//does something
	private ImageTextButton button_Options;			//Changes position on click
	private ImageTextButton button_CloseGame;		//Starts game

	@Override
	public void init(Window window) {
		this.panel = window.getPanel();

		TextureHandler.loadImagePng("bar_blue", "ui/bar/bar_blue");
		TextureHandler.loadImagePng("bar_red", "ui/bar/bar_red");
		TextureHandler.loadImagePng("bar_yellow", "ui/bar/bar_yellow");

		button_StartGame = new ImageTextButton(new ImageTextButton.ImageText() {
			@Override
			public BufferedImage getImage() {
				return TextureHandler.getImagePng("bar_blue");
			}

			@Override
			public String getText() {
				return "Start Game";
			}
		}, e -> {});
		button_CloseGame = new ImageTextButton(new ImageTextButton.ImageText() {
			@Override
			public BufferedImage getImage() {
				return TextureHandler.getImagePng("bar_red");
			}

			@Override
			public String getText() {
				return "Exit";
			}
		}, e -> {window.updateView(new GameView());});
		button_Options = new ImageTextButton(new ImageTextButton.ImageText() {
			@Override
			public BufferedImage getImage() {
				return TextureHandler.getImagePng("bar_yellow");
			}

			@Override
			public String getText() {
				return "Options";
			}
		}, e -> {button_Options.setBounds(new Random().nextInt(panel.getWidth() - panel.getHeight()*5/16), new Random().nextInt(panel.getHeight() - panel.getHeight()/16),panel.getHeight()*5/8, panel.getHeight()/8);});

		panel.add(button_CloseGame);
		panel.add(button_Options);
		panel.add(button_StartGame);

		changeSize();

		new Thread(() -> {
			while(running) draw();
		}).start();
	}

	@Override
	public void changeSize() {
		if (button_StartGame == null || button_Options == null || button_CloseGame == null) return;

		int width = panel.getWidth();
		int height = panel.getHeight();

		int buttonHeight = height / 8;
		int buttonWidth = buttonHeight * 5;

		button_StartGame.setBounds((width - buttonWidth) / 2, (height - buttonHeight) / 2, buttonWidth, buttonHeight);
		button_Options.setBounds((width - buttonWidth) / 2, (height + 2 * buttonHeight) / 2, buttonWidth, buttonHeight);
		button_CloseGame.setBounds((width - buttonWidth) / 2, (height + 5 * buttonHeight) / 2, buttonWidth, buttonHeight);
	}

	@Override
	public void draw() {
		if (!running) return;

		BufferedImage buffer = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());

		for (Component component : panel.getComponents()) {
			g.translate(component.getX(), component.getY());
			component.update(g);
			g.translate(-component.getX(), -component.getY());
		}

		panel.getGraphics().drawImage(buffer, 0, 0, null);
	}
}