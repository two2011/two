package pl.edu.agh.two.mud.client;

import java.io.*;

import pl.edu.agh.two.mud.client.configuration.*;

public class Client {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		Connection connection = (Connection) ApplicationContext
				.getBean("connection");
		Gui gui = new Gui();
		gui.show();
		connection.startConnection(args[0], 3306);
		gui.setLabel(connection.readObject().toString());
	}

}
