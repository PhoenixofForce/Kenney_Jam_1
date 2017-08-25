package game.window.components;

import game.window.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ImageTextButton extends JComponent {

	private ImageText content;
	private boolean entered = false;

	public ImageTextButton(ImageText content, ActionListener actionListener) {
		this.content = content;

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (entered) {
					actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				entered = true;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				entered = false;
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (entered) {
			g.drawImage(content.getImage(), (int) (getWidth() * (1 - GUIConstants.BUTTON_HOVER_SIZE) / 2), (int) (getHeight() * (1 - GUIConstants.BUTTON_HOVER_SIZE) / 2), (int) (getWidth() * GUIConstants.BUTTON_HOVER_SIZE), (int) (getHeight() * GUIConstants.BUTTON_HOVER_SIZE), null);
		} else {
			g.drawImage(content.getImage(), 0, 0, getWidth(), getHeight(), null);
		}

		g.setColor(Color.WHITE);

		String text = content.getText();

		int x = 0, y = 0, width = getWidth(), height = getHeight();
		if (entered) {
			x = (int) (width * (1 - GUIConstants.BUTTON_HOVER_SIZE) / 2);
			y = (int) (height * (1 - GUIConstants.BUTTON_HOVER_SIZE) / 2);
			width = (int) (width * GUIConstants.BUTTON_HOVER_SIZE);
			height = (int) (height * GUIConstants.BUTTON_HOVER_SIZE);
		}

		Font font = GUIConstants.FONT.deriveFont(getHeight() * 0.5f);
		g.setFont(font);

		double fWidth = g.getFontMetrics(font).getStringBounds(text, g).getWidth();
		g.drawString(text, (int) ((width - fWidth) / 2) + x, height * 3 / 4 + y);
	}

	public void setContent(ImageText content) {
		this.content = content;
	}

	/*
		Used to not call setContent()
	 */
	public interface ImageText {
		BufferedImage getImage();

		String getText();
	}
}
