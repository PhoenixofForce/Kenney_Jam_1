package game.window;

import game.window.controlls.KeyInputListener;
import game.window.controlls.MouseInputListener;
import game.window.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Window extends JFrame {

	private JPanel panel;
	private View view;

	private boolean running;

	public Window() {

		this.setMinimumSize(new Dimension(800, 600));

		panel = new JPanel();
		panel.setIgnoreRepaint(true);
		panel.setFocusable(true);
		this.setContentPane(panel);
		this.setTitle("7102 MAJ YENNEK");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framed();

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (view != null) view.changeSize();
			}
		});
	}

	public JPanel getPanel() {
		return panel;
	}

	public boolean isCurrentView(View view) {
		return this.view == view;
	}

	public void updateView(View newView) {
		running = false;
		if (this.view != null) this.view.stop();
		this.view = newView;
		panel.removeAll();

		newView.init(this);
	}

	public void addMouseInputListener(MouseInputListener m) {
		getPanel().addMouseListener(m);
		getPanel().addMouseMotionListener(m);
		getPanel().addMouseWheelListener(m);
	}

	public void addKeyInputListener(KeyInputListener k) {
		getPanel().addKeyListener(k);
	}

	public void fullscreen() {
		this.dispose();
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}

	public void framed() {
		this.dispose();
		this.setExtendedState(JFrame.NORMAL);
		this.setUndecorated(false);
		this.setSize(800, 600);
		this.setVisible(true);
	}
}