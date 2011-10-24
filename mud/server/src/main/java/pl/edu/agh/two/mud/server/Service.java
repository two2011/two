package pl.edu.agh.two.mud.server;

import java.io.*;
import java.net.*;

import org.apache.log4j.*;

public class Service extends Thread {

	private Socket clientSocket;
	private Logger logger = Logger.getLogger(Service.class);

	public Service(Socket socket) {
		this.clientSocket = socket;
		logger.info("New client connected: " + socket.getLocalAddress());
	}

	@Override
	public void run() {
		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.writeObject("Connected to the server: " + clientSocket.getLocalSocketAddress());

		} catch (IOException e) {
			logger.error("Socket error: " + e.getMessage());
		}
	}
}
