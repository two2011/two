package pl.edu.agh.two.mud.server;

import java.io.*;
import java.net.*;

import org.apache.log4j.*;

public class Server {
	private static Logger logger = Logger.getLogger(Server.class);

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		int port = 3306;

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			logger.error("Cannot listen on port " + port);
			System.exit(1);
		}
		logger.info("Server started, listening for clients...");
		try {

			while (true) {
				try {
					new Service(serverSocket.accept()).start();
				} catch (IOException e) {
					logger.error("Server socket error:" + e.getStackTrace());
				}
			}
		} finally {
			logger.info("Shutting down the server");
			serverSocket.close();
		}

	}
}
