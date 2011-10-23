package pl.edu.agh.two.mud.client;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class Gui {

	private JFrame frmAghMud;

	public void show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmAghMud.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAghMud = new JFrame();
		frmAghMud.setTitle("AGH MUD");
		frmAghMud.setBounds(100, 100, 450, 300);
		frmAghMud.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new CompoundBorder(new EmptyBorder(4, 4, 4, 4),
				new LineBorder(new Color(192, 192, 192), 1, true)));
		frmAghMud.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		panel_1.add(panel);
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblAghMud = new JLabel("Stay tuned...");
		lblAghMud.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(lblAghMud, BorderLayout.NORTH);
	}

}
