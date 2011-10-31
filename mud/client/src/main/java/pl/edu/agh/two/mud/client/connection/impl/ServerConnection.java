package pl.edu.agh.two.mud.client.connection.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection implements Connection {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public void connect(String host, int port) throws IOException {
		socket = new Socket(host, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	public Object read() throws ClassNotFoundException, IOException {
		return in.readObject();
	}

	public void send(Object objectToSend) throws IOException {
		out.writeObject(objectToSend);
	}

}
