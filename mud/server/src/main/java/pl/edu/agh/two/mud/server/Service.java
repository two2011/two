package pl.edu.agh.two.mud.server;

import java.io.*;
import java.net.*;

import org.apache.log4j.*;

import pl.edu.agh.two.mud.common.command.*;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;

public class Service extends Thread {

	private Socket clientSocket;
	private SocketAddress clientAddress;
	private Logger logger = Logger.getLogger(Service.class);
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Dispatcher dispatcher;


	
	public void bindSocket(Socket socket) throws IOException {
		clientSocket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		clientAddress = clientSocket.getRemoteSocketAddress();
		logger.info("New client connected: " + clientAddress);
	}

	@Override
	public void run() {

		try {
			// TODO: First send to client CommandDefinitions,
			// you can use getAvailableCommands method from CommandProvider
			while (true) {
				Object userCommand = in.readObject();
				dispatcher.dispatch((IParsedCommand) userCommand);
			}
		} catch (Exception e) {
			logger.error(clientAddress + " - " + e.getMessage());
		}

		try {
			logger.info(clientAddress + " - shutting down client connection");
			clientSocket.close();
		} catch (IOException e) {
			logger.error(clientAddress + " - closing client socket error: " + e.getMessage());
		}
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}
