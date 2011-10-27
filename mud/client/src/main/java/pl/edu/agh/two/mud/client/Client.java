package pl.edu.agh.two.mud.client;

import java.awt.EventQueue;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;
import pl.edu.agh.two.mud.client.ui.Console;
import pl.edu.agh.two.mud.client.ui.HeroPanel;

public class Client {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
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

		Console mainConsole = new Console();
		frame.getContentPane().add(mainConsole, "cell 0 0 1 2,grow");

		HeroPanel heroConsole = new HeroPanel();
		frame.getContentPane().add(heroConsole, "cell 1 0,grow");

		Console chatConsole = new Console();
		frame.getContentPane().add(chatConsole, "cell 1 1,grow");
	}

}
