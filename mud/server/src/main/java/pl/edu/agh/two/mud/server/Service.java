package pl.edu.agh.two.mud.server;

import java.io.*;
import java.net.*;

public class Service extends Thread {

	private Socket clientSocket;

	public Service(Socket socket) {
		this.clientSocket = socket;
	}

	@Override
	public void run() {
		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.writeObject("Connected to the server: "
					+ clientSocket.getLocalSocketAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
