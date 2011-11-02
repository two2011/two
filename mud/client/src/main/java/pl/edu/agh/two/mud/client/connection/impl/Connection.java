package pl.edu.agh.two.mud.client.connection.impl;

import java.io.IOException;

public interface Connection {

	void connect(String host, int port) throws IOException;

	Object read() throws ClassNotFoundException, IOException;

	void send(Object objectToSend) throws IOException;

}
