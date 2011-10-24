package pl.edu.agh.two.mud.client;

import java.io.*;
import java.net.*;

public class Connection {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public Connection() {
	}

	public void startConnection(String host, int port) throws IOException {
		socket = new Socket(host, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	public Object readObject() throws ClassNotFoundException, IOException {
		return in.readObject();
	}

	public void writeObject(Object objectToSend) throws IOException {
		out.writeObject(objectToSend);
	}

}
