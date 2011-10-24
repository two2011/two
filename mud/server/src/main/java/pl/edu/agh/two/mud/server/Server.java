package pl.edu.agh.two.mud.server;

import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(3306);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {
			while (true) {
				try {
					new Service(serverSocket.accept()).start();
				} catch (Exception e) {
				}
			}
		} finally {

			serverSocket.close();
		}

	}
}
