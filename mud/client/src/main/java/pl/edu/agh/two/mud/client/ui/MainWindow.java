package pl.edu.agh.two.mud.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

public class MainWindow {

	private JFrame frame;

	private Console mainConsole;

	private PlayerPanel playerPanel;

	/**
	 * Opens application window.
	 */
	public void open() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 858, 381);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new MigLayout("", "[grow][400]", "[0][grow]"));

		mainConsole = new Console();
		frame.getContentPane().add(mainConsole, "cell 0 0 1 2,grow");

		playerPanel = new PlayerPanel();
		frame.getContentPane().add(getPlayerPanel(), "cell 1 0,grow");

		Console chatConsole = new Console();
		frame.getContentPane().add(chatConsole, "cell 1 1,grow");
	}

	public Console getMainConsole() {
		return mainConsole;
	}

	public PlayerPanel getPlayerPanel() {
		return playerPanel;
	}

}
