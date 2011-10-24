package pl.edu.agh.two.mud.client;

import java.io.*;

import pl.edu.agh.two.mud.client.configuration.*;

public class Client {

	private Connection connection;

	private Gui gui;

	public void start(String host, int port) {
		gui.show();
		try {
			connection.connect(host, port);
			gui.setLabel(connection.read().toString());
		} catch (Exception e) {
			gui.setLabel(e.toString());
		}

	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		Client client = (Client) ApplicationContext.getBean("client");
		client.start(args.length == 0 ? "127.0.0.1" : args[0], 3306);
	}

}
