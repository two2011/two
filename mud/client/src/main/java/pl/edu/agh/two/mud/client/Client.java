package pl.edu.agh.two.mud.client;

import java.io.*;

import pl.edu.agh.two.mud.client.configuration.*;

public class Client {

	private static final String DEFAULT_IP = "127.0.0.1";

	private Connection connection;

	private Gui gui;

	public void start(String host, int port) {
		try {
			gui.show();
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

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Client client = (Client) ApplicationContext.getBean("client");
		String host = getHostFromArgsOrDefault(args);
		client.start(host, 3306);
	}

	private static String getHostFromArgsOrDefault(String[] args) {
		return args.length == 0 ? DEFAULT_IP : args[0];
	}

}
